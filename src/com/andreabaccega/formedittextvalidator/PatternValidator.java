package com.andreabaccega.formedittextvalidator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.widget.EditText;

public class PatternValidator extends Validator{
	private Pattern pattern;
	public PatternValidator(String _customErrorMessage, Pattern _pattern) {
		super(_customErrorMessage);
		if (_pattern == null) throw new IllegalArgumentException("matcher must not be null");
		pattern = _pattern;
	}

	public boolean isValid(EditText et) {
		return pattern.matcher(et.getText()).matches();
	}

}
