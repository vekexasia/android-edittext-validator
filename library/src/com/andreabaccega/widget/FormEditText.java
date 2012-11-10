package com.andreabaccega.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

import com.andreabaccega.formedittextvalidator.Validator;

/**
 * EditText Extension to be used in order to create forms in android.
 * 
 * @author Andrea Baccega <me@andreabaccega.com>
 */
public class FormEditText extends EditText {
	public FormEditText(Context context) {
		super(context);
		// FIXME how should this constructor be handled
		throw new RuntimeException("Not supported");
	}

	public FormEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		editTextValidator = new DefaultEditTextValidator(this, attrs, context);
	}

	public FormEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		editTextValidator = new DefaultEditTextValidator(this, attrs, context);

	}

	/**
	 * Add a validator to this FormEditText. The validator will be added in the
	 * queue of the current validators.
	 * 
	 * @param theValidator
	 * @throws IllegalArgumentException
	 *             if the validator is null
	 */
	public void addValidator(Validator theValidator) throws IllegalArgumentException {
		editTextValidator.addValidator(theValidator);
	} 

	public EditTextValidator getEditTextValidator() {
		return editTextValidator;
	}

	public void setEditTextValidator(EditTextValidator editTextValidator) {
		this.editTextValidator = editTextValidator;
	}

	/**
	 * Calling *testValidity()* will cause the EditText to go through
	 * customValidators and call {@link #Validator.isValid(EditText)}
	 * 
	 * @return true if the validity passes false otherwise.
	 */
	public boolean testValidity() {
		return editTextValidator.testValidity();
	}

	private EditTextValidator editTextValidator;

}
