package com.andreabaccega.formedittextvalidator;

import android.widget.EditText;

/**
 * The or validator checks if one of passed validators is returning true.<br/>
 * Note: the message that will be shown is the one passed to the Constructor
 * @author Andrea B.
 *
 */
public class OrValidator extends MultiValidator {
  
  public OrValidator( String message, Validator ... validators) {
    super(message, validators);
  }
  
  public boolean isValid(EditText et) {
    //TODO: What if we've no validators ?
    for (Validator v: validators) {
      if (v.isValid(et)) {
        return true; // Remember :) We're acting like an || operator.
      }
    }
    return false;
  }
  
}

