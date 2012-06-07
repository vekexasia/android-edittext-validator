package com.andreabaccega.formedittextvalidator;

import android.widget.EditText;

/**
 * It's a validator that applies the "NOT" logical operator to the validator passed in the constructor.
 * @author Andrea Baccega <me@andreabaccega.com>
 *
 */
public class NotValidator extends Validator{
  private Validator v;
  public NotValidator(String errorMessage, Validator _v) {
    super(errorMessage);
    v = _v;
  }

  public boolean isValid(EditText et) {
    return ! v.isValid(et);
  }

}
