package com.andreabaccega.edittextformexample;

import android.text.TextUtils;
import android.widget.EditText;

import com.andreabaccega.formedittextvalidator.Validator;

public class CiaoValidator
    extends Validator
{

	public CiaoValidator( String customErrorMessage )
	{
		super( customErrorMessage );

	}

	@Override
	public boolean isValid( EditText et )
	{
		return TextUtils.equals( et.getText(), "ciao" );
	}

}