package com.andreabaccega.edittextformexample;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.andreabaccega.edittextformexample.utils.LayoutListItem;
import com.andreabaccega.edittextformexample.utils.ListItem;
import com.andreabaccega.edittextformexample.utils.SimpleListItem;

public class EditTextFormExampleActivity
    extends ListActivity
    implements OnItemClickListener
{
	public EditTextFormExampleActivity()
	{
		stringItems = new String[lItems.length];
		for ( int i = 0; i < lItems.length; i++ )
		{
			stringItems[i] = lItems[i].getListTitle();
			Log.d( stringItems[i], stringItems[i] );
		}
	}

	@Override
	public void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		ListView mLv = new ListView( this );
		mLv.setId( android.R.id.list );
		setContentView( mLv ); // Don't try this at home :)

		setListAdapter( new ArrayAdapter<String>( this, android.R.layout.simple_list_item_1, stringItems ) );
		getListView().setOnItemClickListener( this );
	}

	@Override
	public boolean onCreateOptionsMenu( android.view.Menu menu )
	{
		getMenuInflater().inflate( R.menu.menu, menu );
		return true;
	}

	public void onItemClick( AdapterView<?> av, View v, int pos, long id )
	{
		lItems[pos].goToDemo( this );
	}

	@Override
	public boolean onOptionsItemSelected( MenuItem item )
	{
		switch ( item.getItemId() )
		{
			case R.id.prefs:
				startActivity( new Intent( this, SettingsActivity.class ) );
				return true;

			default:
				return false;
		}
	}

	private ListItem[] lItems = new ListItem[] {

	new LayoutListItem( "Alpha", R.layout.example_alpha, R.string.explanation_alpha ),
	    new LayoutListItem( "Numeric only", R.layout.example_numeric, R.string.explanation_numeric ),
	    new LayoutListItem( "Email", R.layout.example_email, R.string.explanation_email ),
	    new LayoutListItem( "Credit Card Number", R.layout.example_creditcard, R.string.explanation_creditcard ),
	    new LayoutListItem( "Phone", R.layout.example_phone, R.string.explanation_phone ),
	    new LayoutListItem( "Domain Name", R.layout.example_domainname, R.string.explanation_domainname ),
	    new LayoutListItem( "IP Address", R.layout.example_ipaddress, R.string.explanation_ipaddress ),
	    new LayoutListItem( "WEB Url", R.layout.example_weburl, R.string.explanation_weburl ),
	    new LayoutListItem( "Regexp", R.layout.example_regexp, R.string.explanation_regexp ),
	    new LayoutListItem( "Emptyness (nocheck)", R.layout.example_nocheck, R.string.explanation_nocheck ),
	    new LayoutListItem( "Custom Messages", R.layout.example_phone_custommessages, R.string.explanation_phone_custommmessages ),
	    new LayoutListItem( "Allow Empty", R.layout.example_allowempty, R.string.explanation_allow_empty ),
	    new LayoutListItem( "Programmatically Added Checks", R.layout.example_custom, R.string.explanation_programatic ),
	    new SimpleListItem( "Email OR CreditCard", EmailOrCreditCard.class ), };

	private final String[] stringItems;

}