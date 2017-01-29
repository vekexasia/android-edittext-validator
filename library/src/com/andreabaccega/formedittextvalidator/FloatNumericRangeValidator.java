package com.andreabaccega.formedittextvalidator;

import android.widget.EditText;

/**
 * A validator that returns true only if the input field contains only numbers
 * and the number is within the given range.
 *
 * @author Said Tahsin Dane <tasomaniac@gmail.com>
 */
public class FloatNumericRangeValidator extends Validator {

    private float floatmin, floatmax;

    public FloatNumericRangeValidator(String _customErrorMessage, float floatmin, float floatmax) {
        super(_customErrorMessage);
        this.floatmin = floatmin;
        this.floatmax = floatmax;
    }

    public boolean isValid(EditText et) {
        try {
            float value = Float.parseFloat(et.getText().toString());
            return value >= floatmin && value <= floatmax;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
