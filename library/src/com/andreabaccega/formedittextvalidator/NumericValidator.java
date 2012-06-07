package com.andreabaccega.formedittextvalidator;

import android.text.TextUtils;
import android.widget.EditText;

/**
 * A validator that returns true only if the input field contains only numbers.
 * @author Andrea Baccega <me@andreabaccega.com>
 *
 */
public class NumericValidator extends Validator{
	public NumericValidator(String _customErrorMessage) {
		super(_customErrorMessage);
	}

	public boolean isValid(EditText et) {
		return TextUtils.isDigitsOnly(et.getText());
	}
}
