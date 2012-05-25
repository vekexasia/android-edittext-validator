package com.andreabaccega.formedittextvalidator;

import java.util.regex.Pattern;

import android.widget.EditText;

public class RegexpValidator extends Validator {
	private String regexp;
	public RegexpValidator( String message, String _regexp) {
		super(message);
		regexp = _regexp;
	}
	public boolean isValid(EditText et) {
		return Pattern.matches(regexp, et.getText());
	}
}
