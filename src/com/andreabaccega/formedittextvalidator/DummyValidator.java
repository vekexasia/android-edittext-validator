package com.andreabaccega.formedittextvalidator;

import java.util.regex.Pattern;

import android.widget.EditText;

public class DummyValidator extends Validator {
	public DummyValidator() {
		super(null);
	}
	public boolean isValid(EditText et) {
		return true;
	}
}
