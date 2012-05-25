package com.andreabaccega.formedittextvalidator;

import java.util.regex.Pattern;

import android.os.Build;
import android.util.Patterns;

public class DomainValidator extends PatternValidator{
	public DomainValidator(String _customErrorMessage) {
		//TODO: Fix the pattern for api level < 8
		super(_customErrorMessage, Build.VERSION.SDK_INT>=8?Patterns.DOMAIN_NAME:Pattern.compile(".*"));
	}
}
