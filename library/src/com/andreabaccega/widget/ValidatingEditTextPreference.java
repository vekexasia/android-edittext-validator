package com.andreabaccega.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

/**
 * A validating {@link EditTextPreference} validation is performed when the OK
 * or AlertDialog.BUTTON_POSITIVE button is clicked. When invalid an error
 * message is displayed and the EditTextPreference is not dismissed
 */
public class ValidatingEditTextPreference extends EditTextPreference {
	public ValidatingEditTextPreference(Context context) {
		super(context);
		// FIXME how should this constructor be handled
		throw new RuntimeException("Not supported");
	}

	public ValidatingEditTextPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
		editTextValidator = new DefaultEditTextValidator(getEditText(), attrs,
				context);
	}

	public ValidatingEditTextPreference(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		editTextValidator = new DefaultEditTextValidator(getEditText(), attrs,
				context);
	}

	public EditTextValidator getEditTextValidator() {
		return editTextValidator;
	}

	public void setEditTextValidator(EditTextValidator editTextValidator) {
		this.editTextValidator = editTextValidator;
	}

	@Override
	protected void showDialog(Bundle state) {
		super.showDialog(state);

		// If the dialog isn't an instance of alert dialog this code is useless
		if (super.getDialog() instanceof AlertDialog) {
			final AlertDialog theDialog = (AlertDialog) super.getDialog();

			// get originalBottomPadding to know when adjust the underlying
			// layouts bottom padding (ie has room already
			// been created for an error message)
			int padding = Integer.MIN_VALUE;
			try {
				padding = ((LinearLayout) getEditText().getParent())
						.getPaddingBottom();
			} catch (Exception e) {
				// some exception thrown. Unable to do increase space for error
				// message
			}

			final int originalBottomPadding = padding;

			Button b = theDialog.getButton(AlertDialog.BUTTON_POSITIVE);

			// attach our validating on click listener
			ValidatingOnClickListener l = new ValidatingOnClickListener(
					originalBottomPadding, theDialog);
			b.setOnClickListener(l);

			// add an editor action listener for the 'done/next' buttons on a
			// soft keyboard
			getEditText().setOnEditorActionListener(l);
		}
	}

	private final class ValidatingOnClickListener implements
			View.OnClickListener, OnEditorActionListener {
		private ValidatingOnClickListener(int originalBottomPadding,
				AlertDialog theDialog) {
			this.originalBottomPadding = originalBottomPadding;
			this.theDialog = theDialog;
		}

		public void onClick(View view) {
			performValidation();
		}

		public void performValidation() {
			getEditText().setError(null);
			if (editTextValidator.testValidity()) {
				// Dismiss once everything is OK.
				theDialog.dismiss();
				ValidatingEditTextPreference.this.onClick(theDialog,
						AlertDialog.BUTTON_POSITIVE);

				// reset padding - for when dialog is used again
				if (originalBottomPadding != Integer.MIN_VALUE) {
					LinearLayout parentLayout = (LinearLayout) getEditText()
							.getParent();

					if (originalBottomPadding == parentLayout
							.getPaddingBottom()) {
						parentLayout.setPadding(parentLayout.getPaddingLeft(),
								parentLayout.getPaddingTop(),
								parentLayout.getPaddingRight(),
								originalBottomPadding);
					}
				}
			} else {

				// increase padding so error message doesn't cover buttons
				if (originalBottomPadding != Integer.MIN_VALUE) {
					LinearLayout parentLayout = (LinearLayout) getEditText()
							.getParent();

					if (originalBottomPadding == parentLayout
							.getPaddingBottom()) {
						parentLayout
								.setPadding(
										parentLayout.getPaddingLeft(),
										parentLayout.getPaddingTop(),
										parentLayout.getPaddingRight(),
										(int) (parentLayout.getPaddingBottom() + getEditText()
												.getHeight() * 1.05));
					}
				}

				// don't dismiss the dialog
			}
		}

		private final int originalBottomPadding;

		private final AlertDialog theDialog;

		@Override
		public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
			performValidation();
			return true;
		}

	}

	private EditTextValidator editTextValidator;

}
