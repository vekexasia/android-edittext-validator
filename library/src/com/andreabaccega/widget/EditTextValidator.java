package com.andreabaccega.widget;

import android.content.Context;
import android.text.TextWatcher;
import android.widget.EditText;

import com.andreabaccega.formedittextvalidator.Validator;

/**
 * Interface for encapsulating validation of an EditText control
 */
public interface EditTextValidator {
    /**
     * Add a validator to this FormEditText. The validator will be added in the
     * queue of the current validators.
     *
     * @param theValidator
     * @throws IllegalArgumentException if the validator is null
     */
    void addValidator(Validator theValidator)
            throws IllegalArgumentException;

    /**
     * This should be used with {@link #addTextChangedListener(TextWatcher)}. It
     * fixes the non-hiding error popup behaviour.
     */
    TextWatcher getTextWatcher();

    boolean isEmptyAllowed();

    /**
     * Resets the {@link Validator}s
     */
    void resetValidators(Context context);

    /**
     * Calling *testValidity()* will cause the EditText to go through
     * customValidators and call {@link #Validator.isValid(EditText)}
     * Same as {@link #testValidity(boolean)} with first parameter true
     *
     * @return true if the validity passes false otherwise.
     */
    boolean testValidity();

    /**
     * Calling *testValidity()* will cause the EditText to go through
     * customValidators and call {@link #Validator.isValid(EditText)}
     *
     * @param showUIError determines if this call should show the UI error.
     * @return true if the validity passes false otherwise.
     */
    boolean testValidity(boolean showUIError);

    void showUIError();

    int TEST_REGEXP = 0;

    int TEST_NUMERIC = 1;

    int TEST_ALPHA = 2;

    int TEST_ALPHANUMERIC = 3;

    int TEST_EMAIL = 4;

    int TEST_CREDITCARD = 5;

    int TEST_PHONE = 6;

    int TEST_DOMAINNAME = 7;

    int TEST_IPADDRESS = 8;

    int TEST_WEBURL = 9;

    int TEST_NOCHECK = 10;

    int TEST_CUSTOM = 11;

    int TEST_PERSONNAME = 12;

    int TEST_PERSONFULLNAME = 13;

    int TEST_DATE = 14;

    int TEST_NUMERIC_RANGE = 15;

    int TEST_FLOAT_NUMERIC_RANGE = 16;

}
