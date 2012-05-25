package com.andreabaccega.formedittextvalidator;

import android.widget.EditText;

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
