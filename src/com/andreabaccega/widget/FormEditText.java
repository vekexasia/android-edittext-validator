package com.andreabaccega.widget;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.andreabaccega.formedittext.R;
import com.andreabaccega.formedittextvalidator.AlphaNumericValidator;
import com.andreabaccega.formedittextvalidator.AlphaValidator;
import com.andreabaccega.formedittextvalidator.CreditCardValidator;
import com.andreabaccega.formedittextvalidator.DomainValidator;
import com.andreabaccega.formedittextvalidator.DummyValidator;
import com.andreabaccega.formedittextvalidator.EmailValidator;
import com.andreabaccega.formedittextvalidator.EmptyValidator;
import com.andreabaccega.formedittextvalidator.IpAddressValidator;
import com.andreabaccega.formedittextvalidator.NumericValidator;
import com.andreabaccega.formedittextvalidator.PhoneValidator;
import com.andreabaccega.formedittextvalidator.RegexpValidator;
import com.andreabaccega.formedittextvalidator.Validator;
import com.andreabaccega.formedittextvalidator.WebUrlValidator;

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
	private static final int TEST_EMAIL			= 4;
	private static final int TEST_CREDITCARD	= 5;
	private static final int TEST_PHONE			= 6;
	private static final int TEST_DOMAINNAME	= 7;
	private static final int TEST_IPADDRESS		= 8;
	private static final int TEST_WEBURL		= 9;
	
	private static final int TEST_NOCHECK		= 10;
	/**
	 * The custom validators setted using
	 */
	private List<Validator> mValidators = new ArrayList<Validator>();
	private String errorStringFromXML;
	
	/**
	 * This should be used with {@link #addTextChangedListener(TextWatcher)}. It fixes the non-hiding error popup behaviour.
	 */
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
	
	public String getCustomErrorString() {
		return errorStringFromXML;
	}
	
	public FormEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FormEditText);
		boolean emptyAllowed = typedArray.getBoolean(R.styleable.FormEditText_emptyAllowed, false);
		
		if (! emptyAllowed ) { // If the xml tells us that this is a required field, we will add the EmptyValidator.
			String tmp = typedArray.getString(R.styleable.FormEditText_emptyErrorString);
			String emptyErrorString = null;
			if (! TextUtils.isEmpty(tmp)) {
				emptyErrorString = tmp;
			} else {
				emptyErrorString = context.getString(R.string.error_field_must_not_be_empty);
			}
			addValidator(new EmptyValidator(emptyErrorString));
		}
		
		int testType = typedArray.getInt(R.styleable.FormEditText_test, TEST_NOCHECK);
		
		errorStringFromXML = typedArray.getString(R.styleable.FormEditText_testErrorString);
		
		
		switch (testType) {
		default:
		case TEST_NOCHECK:
			addValidator(new DummyValidator());
			break;
			
		case TEST_ALPHA:
			addValidator(new AlphaValidator(TextUtils.isEmpty(errorStringFromXML)?context.getString(R.string.error_only_standard_letters_are_allowed):errorStringFromXML));
			break;
		case TEST_ALPHANUMERIC:
			addValidator(new AlphaNumericValidator(TextUtils.isEmpty(errorStringFromXML)?context.getString(R.string.error_this_field_cannot_contain_special_character):errorStringFromXML));
			break;
		
		case TEST_NUMERIC:
			addValidator(new NumericValidator(TextUtils.isEmpty(errorStringFromXML)?context.getString(R.string.error_only_numeric_digits_allowed):errorStringFromXML));
			break;
		case TEST_REGEXP:
			String customRegexp = typedArray.getString(R.styleable.FormEditText_customRegexp);
			addValidator(new RegexpValidator(errorStringFromXML, customRegexp));
			break;
		case TEST_CREDITCARD:
			addValidator(new CreditCardValidator(TextUtils.isEmpty(errorStringFromXML)?context.getString(R.string.error_creditcard_number_not_valid):errorStringFromXML));
			break;
		case TEST_EMAIL:
			addValidator(new EmailValidator(TextUtils.isEmpty(errorStringFromXML)?context.getString(R.string.error_email_address_not_valid):errorStringFromXML));
			break;
		case TEST_PHONE:
			addValidator(new PhoneValidator(TextUtils.isEmpty(errorStringFromXML)?context.getString(R.string.error_phone_not_valid):errorStringFromXML));
			break;
		case TEST_DOMAINNAME:
			addValidator(new DomainValidator(TextUtils.isEmpty(errorStringFromXML)?context.getString(R.string.error_domain_not_valid):errorStringFromXML));
			break;
		case TEST_IPADDRESS:
			addValidator(new IpAddressValidator(TextUtils.isEmpty(errorStringFromXML)?context.getString(R.string.error_ip_not_valid):errorStringFromXML));
			break;
		case TEST_WEBURL:
			addValidator(new WebUrlValidator(TextUtils.isEmpty(errorStringFromXML)?context.getString(R.string.error_url_not_valid):errorStringFromXML));
			break;
		}
		
		
		typedArray.recycle();
		
		addTextChangedListener(errorPopupRemoverTextWatcher);
	}
	
	/**
	 * Add a validator to this FormEditText.
	 * The validator will be added in the queue of the current validators.
	 * @param theValidator
	 * @throws IllegalArgumentException if the validator is null
	 */
	public void addValidator(Validator theValidator) throws IllegalArgumentException {
		if (theValidator == null) throw new IllegalArgumentException("theValidator argument should not be null");
		mValidators.add(theValidator);
	}
	
	/**
	 * Calling *testValidity()* will cause the EditText to go through customValidators and call {@link #Validator.isValid(EditText)}
	 * @return true if the validity passes false otherwise.
	 */
	public boolean testValidity() {
		boolean isValid = true;
		for (Validator v: mValidators) {
			isValid = v.isValid(this) && isValid;
			if ( ! isValid ) {
				if (v.hasErrorMessage(this)) {
					setError(v.getErrorMessage(this));
				}
				break;	
			}
		}
		return isValid;
	}
	
	
	
	
	

}
