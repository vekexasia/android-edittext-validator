package com.andreabaccega.formedittextvalidator;

import java.util.regex.Pattern;

import android.os.Build;
import android.util.Patterns;

public class PhoneValidator extends PatternValidator{
	public PhoneValidator(String _customErrorMessage) {

		super(_customErrorMessage, Build.VERSION.SDK_INT>=8?Patterns.PHONE:Pattern.compile(                                  // sdd = space, dot, or dash
				"(\\+[0-9]+[\\- \\.]*)?"                    // +<digits><sdd>*
				+ "(\\([0-9]+\\)[\\- \\.]*)?"               // (<digits>)<sdd>*
				+ "([0-9][0-9\\- \\.][0-9\\- \\.]+[0-9])"));
	}
}
