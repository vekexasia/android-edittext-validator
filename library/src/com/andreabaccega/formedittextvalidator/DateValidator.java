package com.andreabaccega.formedittextvalidator;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.widget.EditText;


public class DateValidator extends Validator {
	private String[] formats;
	public DateValidator(String _customErrorMessage, String _format) {
		super(_customErrorMessage);
		formats = TextUtils.isEmpty(_format) ? new String[]{"DefaultDate","DefaultTime","DefaultDateTime"} : _format.split(";") ;
	}

	@SuppressLint("SimpleDateFormat")
	@Override
	public boolean isValid(EditText et) {
		if(TextUtils.isEmpty(et.getText()))
			return true;
		String value = et.getText().toString();
		for(String _format : formats){
			DateFormat format;
			if("DefaultDate".equalsIgnoreCase(_format)){
				format = SimpleDateFormat.getDateInstance();
			} else if("DefaultTime".equalsIgnoreCase(_format)){
				format = SimpleDateFormat.getTimeInstance();
			} else if("DefaultDateTime".equalsIgnoreCase(_format)){
				format = SimpleDateFormat.getDateTimeInstance();
			} else {
				format = new SimpleDateFormat(_format);
			}
			Date date = null;
			try {
				date = format.parse(value);
			} catch (ParseException e) {
				return false;
			}
			if(date != null){
				return true;
			}
		}
		return false;
	}

}
