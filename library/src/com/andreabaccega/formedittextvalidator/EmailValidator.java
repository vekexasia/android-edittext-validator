package com.andreabaccega.formedittextvalidator;

import java.util.regex.Pattern;

import android.os.Build;
import android.util.Patterns;

public class EmailValidator extends PatternValidator{
	public EmailValidator(String _customErrorMessage) {
		super(_customErrorMessage, Build.VERSION.SDK_INT>=8?Patterns.EMAIL_ADDRESS:Pattern.compile(
	            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
	            "\\@" +
	            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
	            "(" +
	                "\\." +
	                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
	            ")+"
	        ));
	}
}
