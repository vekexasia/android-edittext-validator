package com.andreabaccega.formedittextvalidator;

import android.widget.EditText;

/**
 * A validator that returns true only if the input field contains only numbers
 * and the number is within the given range.
 * 
 * @author Said Tahsin Dane <tasomaniac@gmail.com>
 *
 */
public class NumericRangeValidator extends Validator{
	
	private int min, max;
	
	public NumericRangeValidator(String _customErrorMessage, int min, int max) {
		super(_customErrorMessage);
		this.min = min;
		this.max = max;
	}

	public boolean isValid(EditText et) {
		try {
			int value = Integer.parseInt(et.getText().toString());
			return value >= min && value <= max;
		} catch(NumberFormatException e) {
			return false;
		}
	}
}
