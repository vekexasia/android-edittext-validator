package com.andreabaccega.formedittextvalidator;

import android.text.TextUtils;
import android.widget.EditText;

/**
 * A simple validator that validates the field only if the field is not empty.
 * @author Andrea Baccega <me@andreabaccega.com>
 *
 */
public class EmptyValidator extends Validator {
	public EmptyValidator(String message) {
		super(message);
	}
	public boolean isValid(EditText et) {
		return TextUtils.getTrimmedLength(et.getText()) > 0;
	}
}
