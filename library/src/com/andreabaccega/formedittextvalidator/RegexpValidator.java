package com.andreabaccega.formedittextvalidator;

import java.util.regex.Pattern;

/**
 * Used for validating the user input using a regexp.
 * @author Andrea Baccega <me@andreabaccega.com>
 *
 */
public class RegexpValidator extends PatternValidator {
	public RegexpValidator( String message, String _regexp) {
		super(message, Pattern.compile(_regexp));
	}
}
