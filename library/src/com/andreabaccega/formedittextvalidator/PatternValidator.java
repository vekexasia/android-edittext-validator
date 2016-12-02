package com.andreabaccega.formedittextvalidator;

import android.widget.EditText;

import java.util.regex.Pattern;

/**
 * Base class for regexp based validators.
 *
 * @author Andrea Baccega <me@andreabaccega.com>
 * @see DomainValidator
 * @see EmailValidator
 * @see IpAddressValidator
 * @see PhoneValidator
 * @see WebUrlValidator
 * @see RegexpValidator
 */
public class PatternValidator extends Validator {
    private Pattern pattern;

    public PatternValidator(String _customErrorMessage, Pattern _pattern) {
        super(_customErrorMessage);
        if (_pattern == null) throw new IllegalArgumentException("_pattern must not be null");
        pattern = _pattern;
    }

    public boolean isValid(EditText et) {
        return pattern.matcher(et.getText()).matches();
    }

}
