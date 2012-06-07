package com.andreabaccega.formedittextvalidator;

import java.util.regex.Pattern;

import android.os.Build;
import android.util.Patterns;

/**
 * Validates the ipaddress. The regexp was taken from the android source code.
 * @author Andrea Baccega <me@andreabaccega.com>
 *
 */
public class IpAddressValidator extends PatternValidator{
	public IpAddressValidator(String _customErrorMessage) {
		super(_customErrorMessage, Build.VERSION.SDK_INT>=8?Patterns.IP_ADDRESS:Pattern.compile(
	            "((25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9])\\.(25[0-5]|2[0-4]"
	            + "[0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]"
	            + "[0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}"
	            + "|[1-9][0-9]|[0-9]))"));
	}
}
