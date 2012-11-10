package com.andreabaccega.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;

import com.andreabaccega.formedittext.R;
import com.andreabaccega.formedittextvalidator.AlphaNumericValidator;
import com.andreabaccega.formedittextvalidator.AlphaValidator;
import com.andreabaccega.formedittextvalidator.AndValidator;
import com.andreabaccega.formedittextvalidator.CreditCardValidator;
import com.andreabaccega.formedittextvalidator.DomainValidator;
import com.andreabaccega.formedittextvalidator.DummyValidator;
import com.andreabaccega.formedittextvalidator.EmailValidator;
import com.andreabaccega.formedittextvalidator.EmptyValidator;
import com.andreabaccega.formedittextvalidator.IpAddressValidator;
import com.andreabaccega.formedittextvalidator.MultiValidator;
import com.andreabaccega.formedittextvalidator.NotValidator;
import com.andreabaccega.formedittextvalidator.NumericValidator;
import com.andreabaccega.formedittextvalidator.OrValidator;
import com.andreabaccega.formedittextvalidator.PhoneValidator;
import com.andreabaccega.formedittextvalidator.RegexpValidator;
import com.andreabaccega.formedittextvalidator.Validator;
import com.andreabaccega.formedittextvalidator.WebUrlValidator;

/**
 * Default implementation of an {@link EditTextValidator}
 */
public class DefaultEditTextValidator
    implements EditTextValidator
{

	public DefaultEditTextValidator( EditText editText, AttributeSet attrs, Context context )
	{
		TypedArray typedArray = context.obtainStyledAttributes( attrs, R.styleable.FormEditText );
		emptyAllowed = typedArray.getBoolean( R.styleable.FormEditText_emptyAllowed, false );
		testType = typedArray.getInt( R.styleable.FormEditText_testType, EditTextValidator.TEST_NOCHECK );
		testErrorString = typedArray.getString( R.styleable.FormEditText_testErrorString );
		classType = typedArray.getString( R.styleable.FormEditText_classType );
		customRegexp = typedArray.getString( R.styleable.FormEditText_customRegexp );
		emptyErrorString = typedArray.getString( R.styleable.FormEditText_emptyErrorString );
		typedArray.recycle();

		this.editText = editText;
		resetValidators( context );

	}

	@Override
	public void addValidator( Validator theValidator )
	    throws IllegalArgumentException
	{
		if ( theValidator == null )
		{
			throw new IllegalArgumentException( "theValidator argument should not be null" );
		}
		mValidator.enqueue( theValidator );
	}

	public String getClassType()
	{
		return classType;
	}

	public String getCustomRegexp()
	{
		return customRegexp;
	}

	public EditText getEditText()
	{
		return editText;
	}

	public String getTestErrorString()
	{
		return testErrorString;
	}

	public int getTestType()
	{
		return testType;
	}

	@Override
	public TextWatcher getTextWatcher()
	{
		if ( tw == null )
		{
			tw = new TextWatcher()
			{

				public void afterTextChanged( Editable s )
				{
				}

				public void beforeTextChanged( CharSequence s, int start, int count, int after )
				{
				}

				public void onTextChanged( CharSequence s, int start, int before, int count )
				{
					if ( s != null && s.length() > 0 && editText.getError() != null )
					{
						editText.setError( null );
					}
				}
			};
		}
		return tw;
	}

	@Override
	public boolean isEmptyAllowed()
	{
		return emptyAllowed;
	}

	@Override
	public void resetValidators( Context context )
	{
		// its possible the context may have changed so re-get the defaultEmptyErrorString
		defaultEmptyErrorString = context.getString( R.string.error_field_must_not_be_empty );
		setEmptyErrorString( emptyErrorString );

		mValidator = new AndValidator();
		Validator toAdd;

		switch ( testType )
		{
			default:
			case TEST_NOCHECK:
				toAdd = new DummyValidator();
				break;

			case TEST_ALPHA:
				toAdd =
				    new AlphaValidator( TextUtils.isEmpty( testErrorString ) ? context.getString( R.string.error_only_standard_letters_are_allowed )
				                    : testErrorString );
				break;
			case TEST_ALPHANUMERIC:
				toAdd =
				    new AlphaNumericValidator(
				                               TextUtils.isEmpty( testErrorString ) ? context.getString( R.string.error_this_field_cannot_contain_special_character )
				                                               : testErrorString );
				break;

			case TEST_NUMERIC:
				toAdd =
				    new NumericValidator( TextUtils.isEmpty( testErrorString ) ? context.getString( R.string.error_only_numeric_digits_allowed )
				                    : testErrorString );
				break;
			case TEST_REGEXP:

				toAdd = new RegexpValidator( testErrorString, customRegexp );
				break;
			case TEST_CREDITCARD:
				toAdd =
				    new CreditCardValidator( TextUtils.isEmpty( testErrorString ) ? context.getString( R.string.error_creditcard_number_not_valid )
				                    : testErrorString );
				break;
			case TEST_EMAIL:
				toAdd =
				    new EmailValidator( TextUtils.isEmpty( testErrorString ) ? context.getString( R.string.error_email_address_not_valid )
				                    : testErrorString );
				break;
			case TEST_PHONE:
				toAdd =
				    new PhoneValidator( TextUtils.isEmpty( testErrorString ) ? context.getString( R.string.error_phone_not_valid ) : testErrorString );
				break;
			case TEST_DOMAINNAME:
				toAdd =
				    new DomainValidator( TextUtils.isEmpty( testErrorString ) ? context.getString( R.string.error_domain_not_valid )
				                    : testErrorString );
				break;
			case TEST_IPADDRESS:
				toAdd =
				    new IpAddressValidator( TextUtils.isEmpty( testErrorString ) ? context.getString( R.string.error_ip_not_valid ) : testErrorString );
				break;
			case TEST_WEBURL:
				toAdd =
				    new WebUrlValidator( TextUtils.isEmpty( testErrorString ) ? context.getString( R.string.error_url_not_valid ) : testErrorString );
				break;

			case TEST_CUSTOM:
				// must specify the fully qualified class name & an error message

				if ( classType == null )
				{
					throw new RuntimeException( "Trying to create a custom validator but no classType has been specified." );
				}
				if ( TextUtils.isEmpty( testErrorString ) )
				{
					throw new RuntimeException( String.format( "Trying to create a custom validator (%s) but no error string specified.", classType ) );
				}

				Class<? extends Validator> customValidatorClass;
				try
				{
					Class<?> loadedClass = this.getClass().getClassLoader().loadClass( classType );

					if ( !Validator.class.isAssignableFrom( loadedClass ) )
					{
						throw new RuntimeException( String.format( "Custom validator (%s) does not extend %s", classType, Validator.class.getName() ) );
					}
					customValidatorClass = (Class<? extends Validator>) loadedClass;
				}
				catch ( ClassNotFoundException e )
				{
					throw new RuntimeException( String.format( "Unable to load class for custom validator (%s).", classType ) );
				}

				try
				{
					toAdd = customValidatorClass.getConstructor( String.class ).newInstance( testErrorString );
				}
				catch ( Exception e )
				{
					throw new RuntimeException( String.format( "Unable to construct custom validator (%s) with argument: %s", classType,
					                                           testErrorString ) );
				}

				break;
		}

		MultiValidator tmpValidator;
		if ( !emptyAllowed )
		{ // If the xml tells us that this is a required field, we will add the EmptyValidator.
			tmpValidator = new AndValidator();
			tmpValidator.enqueue( new EmptyValidator( emptyErrorStringActual ) );
			tmpValidator.enqueue( toAdd );
		}
		else
		{
			tmpValidator = new OrValidator( toAdd.getErrorMessage(), new NotValidator( null, new EmptyValidator( null ) ), toAdd );
		}

		addValidator( tmpValidator );
	}

	public void setClassType( String classType, String testErrorString, Context context )
	{
		testType = EditTextValidator.TEST_CUSTOM;
		this.classType = classType;
		this.testErrorString = testErrorString;
		resetValidators( context );
	}

	public void setCustomRegexp( String customRegexp, Context context )
	{
		testType = EditTextValidator.TEST_REGEXP;
		this.customRegexp = customRegexp;
		resetValidators( context );
	}

	public void setEditText( EditText editText )
	{
		this.editText.removeTextChangedListener( getTextWatcher() );
		this.editText = editText;
		editText.addTextChangedListener( getTextWatcher() );
	}

	public void setEmptyAllowed( boolean emptyAllowed, Context context )
	{
		this.emptyAllowed = emptyAllowed;
		resetValidators( context );
	}

	public void setEmptyErrorString( String emptyErrorString )
	{
		if ( !TextUtils.isEmpty( emptyErrorString ) )
		{
			emptyErrorStringActual = emptyErrorString;
		}
		else
		{
			emptyErrorStringActual = defaultEmptyErrorString;
		}
	}

	public void setTestErrorString( String testErrorString, Context context )
	{
		this.testErrorString = testErrorString;
		resetValidators( context );
	}

	public void setTestType( int testType, Context context )
	{
		this.testType = testType;
		resetValidators( context );
	}

	/**
	 * Calling *testValidity()* will cause the EditText to go through customValidators and call
	 * {@link #Validator.isValid(EditText)}
	 * 
	 * @return true if the validity passes false otherwise.
	 */
	@Override
	public boolean testValidity()
	{
		boolean isValid = mValidator.isValid( editText );
		if ( !isValid )
		{
			if ( mValidator.hasErrorMessage() )
			{
				editText.setError( mValidator.getErrorMessage() );
			}
		}
		return isValid; 
	}

	private TextWatcher tw;

	private String defaultEmptyErrorString;

	/**
	 * The custom validators setted using
	 */
	protected MultiValidator mValidator;

	protected String testErrorString;

	protected boolean emptyAllowed;

	protected EditText editText;

	protected int testType;

	protected String classType;

	protected String customRegexp;

	protected String emptyErrorStringActual;

	protected String emptyErrorString;

}
