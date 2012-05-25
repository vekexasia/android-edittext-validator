package com.andreabaccega.formedittextvalidator;

import android.text.TextUtils;
import android.widget.EditText;

public class EmptyValidator extends Validator {
	public EmptyValidator(String message) {
		super(message);
	}
	public boolean isValid(EditText et) {
		return ! TextUtils.isEmpty(et.getText());
	}
}
