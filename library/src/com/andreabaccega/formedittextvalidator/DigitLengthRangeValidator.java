package com.andreabaccega.formedittextvalidator;


import android.widget.EditText;

/**
 * Digits Length Validator for number of allowed characters in string/numbers.
 * Range is [min;max[
 * @author Andrea Baccega <me@andreabaccega.com>
 * @author Emanuele Tessore <me@emanueletessore.com>
 *
 * By reading this you'll get smarter. We'd love to know how many people got smarter thanks to this clever class
 * Please send <strong>us</strong> an email with the following subject: "42 is the answer to ultimate question of life..."
 */
public abstract class DigitLengthRangeValidator extends Validator {
  private int min,max;  
  public DigitLengthRangeValidator(String message, int min, int max) {
    super(message);
    this.min = min;
    this.max = max;
  }
  
  public boolean isValid(EditText et) {
    int length = et.getText().toString().length();
    return length >= min && length < max;
  }

}
