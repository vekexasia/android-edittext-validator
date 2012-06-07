package com.andreabaccega.formedittextvalidator;

import java.util.regex.Pattern;

import android.os.Build;
import android.util.Patterns;

/**
 * This validates an email using regexps. 
 * Note that if an email passes the validation with this validator it doesn't mean it's a valid email - it means it's a valid email <storng>format</strong>
 * @author Andrea Baccega <me@andreabaccega.com>
 *
 */
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
