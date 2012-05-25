package com.andreabaccega.formedittextvalidator;

import android.text.TextUtils;
import android.widget.EditText;

public class NumericValidator extends Validator{
	public NumericValidator(String _customErrorMessage) {
		super(_customErrorMessage);
	}

	public boolean isValid(EditText et) {
		return TextUtils.isDigitsOnly(et.getText());
	}
}
