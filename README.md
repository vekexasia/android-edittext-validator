# Android Form EditText
[![Release](https://jitpack.io/v/com.andreabaccega/android-edittext-validator.svg)](https://jitpack.io/#com.andreabaccega/android-edittext-validator)


Android form edit text is an extension of EditText that brings data validation facilities to the edittext.

# Example App
I built an example app that showcase some of the possibilities of the library. 

You'll be able to find the app in the [Play Store](https://play.google.com/store/apps/details?id=com.andreabaccega.edittextformexample)
Here some screenshot of the Example app ( and the library )

![Examples list](http://lh6.ggpht.com/mYceoyXym2U4-6tRJWsudY4-6-V1TyFlqDfzL9P2R4Z059WZQLTZ3C9Gqcwr-hRrDQ) - ![Email validation](http://lh6.ggpht.com/yTzsI6-9VTtJVH331EA6gKc4GRBMv_DXxjAqPPlV9Yj5g-VGzcWtJ77T_m2JcbmbOoQ)

The app source code is located under this repo!

# How to include it

This library can be found in maven central repo. If you're using Android studio you can include it by writing the following in the corresponding _dependencies_ block
#### Gradle:
```groovy
allprojects {
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}
dependencies {
	// ...
	compile 'com.andreabaccega:android-edittext-validator:1.3.5'
	// ...
}
```
#### Maven
```xml
        <repositories>
		    <repository>
		        <id>jitpack.io</id>
		        <url>https://jitpack.io</url>
		    </repository>
	    </repositories>
		<dependency>
			<groupId>com.andreabaccega</groupId>
			<artifactId>android-edittext-validator</artifactId>
			<version>${...}</version>
			<type>aar</type>
			<scope>provided</scope>
		</dependency>
```

Since 1.3.+ the library comes with a new optional dependency: [com.android.support.design](http://android-developers.blogspot.it/2015/05/android-design-support-library.html). This will enable the new [TextInputLayout](http://developer.android.com/reference/android/support/design/widget/TextInputLayout.html) features to be used with the validation engine.
Version 1.3.+ depends on com.android.support.design:2.2.0 but if you're not using the support design library you can safely exclude it while including this with gradle by doing so:

```groovy
dependencies {
    // ..
    implementation 'com.andreabaccega:android-form-edittext:1.3.5'
    // ..
}
```

# How to use


In your xml import an extra namespace on the root of your layout

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:whatever="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="wrap_content" 
    android:orientation="vertical">
    ....
    <!-- Your actual layout -->
    ....
</LinearLayout>
```

**Note:** It's not mandatory to use it on the root element. Also remember to change the xmlns value with your package name

Whenever you need to use the FormEditText just do the following in your xml.

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:whatever="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="vertical">

    <!-- Some stuff -->

	<com.andreabaccega.widget.FormEditText
           whatever:testType="alpha"
           android:id="@+id/et_firstname"
           android:layout_width="match_parent"
           android:layout_height="wrap_content"
           />    

    <!-- Some other stuff -->

</LinearLayout>
```

As you noticed there is a *whatever:test* attribute setted to *alpha*. This let the FormEditText know that the data inside it should be only Alpha characters.

There are several values you can set to the test attribute:
- **regexp**: for custom regexp
- **numeric**: for an only numeric field
- **alpha**: for an alpha only field
- **alphaNumeric**: guess what?
- **email**: checks that the field is a valid email
- **creditCard**: checks that the field contains a valid credit card using [Luhn Algorithm](http://en.wikipedia.org/wiki/Luhn_algorithm)
- **phone**: checks that the field contains a valid phone number
- **domainName**: checks that field contains a valid domain name
- **ipAddress**: checks that the field contains a valid ip address
- **webUrl**: checks that the field contains a valid url
- **personName**: checks if the entered text is a person first or last name.
- **personFullName**: checks if the entered value is a complete full name.
- **date**: checks that the field is a valid date/datetime format ( if customFormat is set, checks with customFormat )
- **numericRange**: checks that the field is a valid value between integers. (minNumber and maxNumber must be set)
- **floatNumericRange**: checks that the field is a valid value between integers (but decimal values are allowed). (minNumber and maxNumber must be set)
- **nocheck**: It does not check anything except the emptyness of the field.

For most of the test type values this library comes with a couple of default strings. This means that error strings ( english only ) are already available for the following test types: numeric, alpha, alphanumeric

You can customize them using the attributes 
- **testErrorString** used when the field does not pass the test
- **emptyErrorString** used when the field is empty

### Example:

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:whatever="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="vertical">

    <!-- Some stuff -->

	<com.andreabaccega.widget.FormEditText
           whatever:testType="alpha"
           whatever:emptyErrorString="@string/your_name_cannot_be_empty"
           whatever:testErrorString="@string/your_name_is_ugly"
           android:id="@+id/et_firstname"
           android:layout_width="match_parent"
           android:layout_height="wrap_content"
           />    

    <!-- Some other stuff -->

</LinearLayout>
```


Furthermore you can ask the FormEditText to allow the content to be *optional*. 
Just use the **emptyAllowed** attribute and set it to *true*. **Note:** If you don't specify the **emptyAllowed** attribute the default value is *false*.

If you want to use **regexp** as **test** attribute value you'll need to also use the **customRegexp** attribute. Take a look in the following example:

### Example: (Email check)

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:whatever="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="wrap_content" >

    <!-- Some stuff -->

	<com.andreabaccega.widget.FormEditText
           whatever:testType="regexp"
           whatever:customRegexp="^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$"
           whatever:testErrorString="@string/error_emailnotvalid"
           android:id="@+id/et_email"
           android:layout_width="match_parent"
           android:layout_height="wrap_content"
           android:hint="@string/hint_email"
           android:inputType="textEmailAddress"
           />    

    <!-- Some other stuff -->

</LinearLayout>
```
**Note:** The library supports the email check natively using the **email** value as the **test** attribute.
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:whatever="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="wrap_content" 
    android:orientation="vertical">

    <!-- Some stuff -->

	<com.andreabaccega.widget.FormEditText
           whatever:testType="email"
           android:id="@+id/et_email"
           android:layout_width="match_parent"
           android:layout_height="wrap_content"
           android:hint="@string/hint_email"
           android:inputType="textEmailAddress"
           />    

    <!-- Some other stuff -->

</LinearLayout>
```

In your Java code you'll need to call a method when you want to know if the field validates.

```java
	public void onClickNext(View v) {
		FormEditText[] allFields	= { etFirstname, etLastname, etAddress, etZipcode, etCity };
		
		
		boolean allValid = true;
		for (FormEditText field: allFields) {
			allValid = field.testValidity() && allValid;
		}
		
		if (allValid) {
			// YAY
		} else {
			// EditText are going to appear with an exclamation mark and an explicative message.
		}
	}
```

Calling *testValidity()* will cause the EditText to cycle through all the validators and call the isValid method. it will stop when one of them returns false or there are no validators left.

Furthermore *testValidity()* will also place an exclamation mark on the right of the  [EditText](http://developer.android.com/reference/android/widget/EditText.html) and will call the [setError](http://developer.android.com/reference/android/widget/TextView.html#setError) method.



## Add your custom validators
You can add your custom validators runtime through the *addValidator* method. For example, let's suppouse we want to add a validator that checks that the text input is equal to the string "ciao":
```java
public class CiaoValidator extends Validator {

	public CiaoValidator() {
		super("You should enter 'ciao' here");
	}

	public boolean isValid(EditText et) {
		return TextUtils.equals(et.getText(), "ciao");
	}
	
}
```

As you can see in the constructor you'll be required to set an Error Message that will be handled ( in this simple scenario ) by the super class. That piece of code will set the error message to: *You should enter 'ciao' here*.

This means that if the user will not enter "ciao" in the edit text it will get that error message in the popup.

## Binary operators
You can use the following binary operators in order to perform checks on the field value:
- **AND**: will return true if every enqueued validator returns true
- **OR**: will return true if just one enqueued validator returns true
- **NOT**: will return the inverse of the passed Validator

With these binary operator validators you'll be able to perform as many different checks as you want. For example, lets say you want a field to be valid either if the user enters his email address or his credit card. Use 'nocheck' in the xml and programmatically do something like this:

```java
  protected void onCreate(Bundle savedInstanceState) {
    // Blabla

    FormEditText fdt = (FormEditText) findViewById(R.id.et);
    fdt.addValidator(
        new OrValidator(
            "This is neither a creditcard or an email", 
            new CreditCardValidator(null), // we specify null as the message string cause the Or validator will use his own message  
            new EmailValidator(null) // same here for null
            )
        );
  }
```


# Author

*  Andrea Baccega <me@andreabaccega.com> - _Author/Ideator of the library_

# Contributors

*  [ffrog8](https://github.com/ffrog8) - _Added the ability to use the library inside the preferences_
*  [indication](https://github.com/indication) - _Added japanese translations_
*  [renclav](https://github.com/renclav) - _Fixed weird bug affecting some 4.2+ devices that prevented the error icon to be shown_
