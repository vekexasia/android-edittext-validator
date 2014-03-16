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
	 * @throws IllegalArgumentException
	 *             if the validator is null
	 */
	public void addValidator(Validator theValidator)
			throws IllegalArgumentException;

	/**
	 * This should be used with {@link #addTextChangedListener(TextWatcher)}. It
	 * fixes the non-hiding error popup behaviour.
	 */
	public TextWatcher getTextWatcher();

	public boolean isEmptyAllowed();

	/**
	 * Resets the {@link Validator}s
	 */
	public void resetValidators(Context context);

	/**
	 * Calling *testValidity()* will cause the EditText to go through
	 * customValidators and call {@link #Validator.isValid(EditText)}
	 * Same as {@link #testValidity(boolean)} with first parameter true
	 * @return true if the validity passes false otherwise.
	 */
	public boolean testValidity();

    /**
     * Calling *testValidity()* will cause the EditText to go through
     * customValidators and call {@link #Validator.isValid(EditText)}
     * @param showUIError determines if this call should show the UI error.
     * @return true if the validity passes false otherwise.
     */
    public boolean testValidity(boolean showUIError);

    public void showUIError();

	final int TEST_REGEXP = 0;

	final int TEST_NUMERIC = 1;

	final int TEST_ALPHA = 2;

	final int TEST_ALPHANUMERIC = 3;

	final int TEST_EMAIL = 4;

	final int TEST_CREDITCARD = 5;

	final int TEST_PHONE = 6;

	final int TEST_DOMAINNAME = 7;

	final int TEST_IPADDRESS = 8;

	final int TEST_WEBURL = 9;

	final int TEST_NOCHECK = 10;

	final int TEST_CUSTOM = 11;

	final int TEST_PERSONNAME = 12;

	final int TEST_PERSONFULLNAME = 13;

	final int TEST_DATE = 14;
	
	final int TEST_NUMERIC_RANGE = 15;

}
