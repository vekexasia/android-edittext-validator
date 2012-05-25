package com.andreabaccega.edittextformexample.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class SimpleListItem extends ListItem {

	private Class<? extends Activity> clazz;

	public SimpleListItem(String _listString, Class<? extends Activity> _clazz) {
		super(_listString);
		clazz = _clazz;
	}

	public void goToDemo(Context ctx) {
		ctx.startActivity(new Intent(ctx, clazz));
	}

}
