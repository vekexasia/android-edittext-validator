package com.andreabaccega.widget;
import java.util.regex.Pattern;

import com.andreabaccega.formedittext.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * EditText Extension to be used in order to create forms in android.
 * 
 * @author Andrea Baccega <me@andreabaccega.com>
 */
public class FormEditText extends EditText {
	private static final int TEST_REGEXP		= 0;
	private static final int TEST_NUMERIC		= 1;
	private static final int TEST_ALPHA			= 2;
	private static final int TEST_ALPHANUMERIC	= 3;
	private static final int TEST_NOCHECK		= 4;
	
	
	private int testType;
	private String emptyErrorString,testErrorString;
	private boolean emptyAllowed;
	private String customRegexp;
	
	private TextWatcher errorPopupRemoverTextWatcher = new TextWatcher() {
		
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			if(s != null && s.length() > 0 && getError() != null) {
	            setError(null);
	        }
		}
		
		public void beforeTextChanged(CharSequence s, int start, int count,	int after) {}
		
		public void afterTextChanged(Editable s) {}
	};
	
	public FormEditText(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	public FormEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FormEditText);
		testType = typedArray.getInt(R.styleable.FormEditText_test, TEST_NOCHECK);
		
		String defaultTestErrorString = null;
		switch (testType) {
		case TEST_ALPHA:
			defaultTestErrorString = context.getString(R.string.error_only_standard_letters_are_allowed);
			break;
		case TEST_ALPHANUMERIC:
			defaultTestErrorString = context.getString(R.string.error_this_field_cannot_contain_special_character);
			break;
		default:
		case TEST_NOCHECK:
			defaultTestErrorString = "";
			break;
		case TEST_NUMERIC:
			defaultTestErrorString = context.getString(R.string.error_only_numeric_digits_allowed);
			break;
		case TEST_REGEXP:
			defaultTestErrorString = "";
			break;
		}
		
		String tmp = typedArray.getString(R.styleable.FormEditText_testErrorString);
		if (! TextUtils.isEmpty(tmp)) {
			testErrorString = tmp;
		} else {
			testErrorString = defaultTestErrorString;
		}
		
		tmp = typedArray.getString(R.styleable.FormEditText_emptyErrorString);
		if (! TextUtils.isEmpty(tmp)) {
			emptyErrorString = tmp;
		} else {
			emptyErrorString = context.getString(R.string.error_field_must_not_be_empty);
		}
		
		emptyAllowed = typedArray.getBoolean(R.styleable.FormEditText_emptyAllowed, false);
		
		customRegexp = typedArray.getString(R.styleable.FormEditText_customRegexp);
		
		typedArray.recycle();
		
		addTextChangedListener(errorPopupRemoverTextWatcher);
	}
	
	public boolean testValidity() {
		if (!emptyAllowed) {
			if (TextUtils.isEmpty(this.getText())) {
				setError(emptyErrorString);
				return false;
			}
		}
		boolean isValid;
		switch (testType) {
		case TEST_REGEXP:
			isValid = testValidityRegexp();
			break;
		case TEST_NUMERIC:
			isValid = testValidityNumeric();
			break;
		case TEST_ALPHA:
			isValid = testValidityAlpha();
			break;
		case TEST_ALPHANUMERIC:
			isValid = testValidityAlphaNumeric();
			break;
		case TEST_NOCHECK:
			isValid = testValidityNocheck();
			break;
		default:
			throw new UnsupportedOperationException("the type of checking is unknown: "+testType);
		}
		
		if (!isValid) {
			setError(testErrorString);
		}
		return isValid;
	}
	private boolean testValidityNocheck() {
		return true;
	}
	private boolean testValidityAlphaNumeric() {
		return Pattern.matches("[a-zA-Z0-9 \\./-]*", getText());
	}
	private boolean testValidityAlpha() {
		return Pattern.matches("[a-zA-Z \\./-]*", getText());
	}
	private boolean testValidityNumeric() {
		return TextUtils.isDigitsOnly(this.getText());
	}

	private boolean testValidityRegexp() {
		if (customRegexp == null) throw new IllegalArgumentException("Regexp type given but no regexp provided");
		return Pattern.matches(customRegexp, getText());
	}
	

}
