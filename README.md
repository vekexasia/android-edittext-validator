Android Form EditText
=====================

Android form edit text is an extension of EditText that brings data validation facilities to the edittext.

How to use
==========

In your xml import an extra namespace on the root of your layout

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:whatever="http://schemas.android.com/apk/res/your.package.name"
    android:layout_width="match_parent"
    android:layout_height="wrap_content" >
    ....
    <!-- Your actual layout -->
    ....
</LinearLayout>
```

** Note: ** It's not mandatory to use it on the root element. Also remember to change the xmlns value with your package name

Whenever you need to use the FormEditText just do the following in your xml.

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:whatever="http://schemas.android.com/apk/res/your.package.name"
    android:layout_width="match_parent"
    android:layout_height="wrap_content" >

    <!-- Some stuff -->

	<com.andreabaccega.widget.FormEditText
           style="@android:style/Widget.EditText"
           whatever:test="alpha"
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
- **alphanumeric**: guess what?
- **nocheck**: It does not check anything except the emptyness of the field.

For most of the test type values this library comes with a couple of default strings. This means that error strings ( english only ) are already available for the following test types: numeric, alpha, alphanumeric

You can customize them using the attributes 
- **testErrorString** used when the field does not pass the test
- **emptyErrorString** used when the field is empty

**Example:**

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:whatever="http://schemas.android.com/apk/res/your.package.name"
    android:layout_width="match_parent"
    android:layout_height="wrap_content" >

    <!-- Some stuff -->

	<com.andreabaccega.widget.FormEditText
           style="@android:style/Widget.EditText"
           whatever:test="alpha"
           whatever:emptyErrorString="@string/your_name_cannot_be_empty"
           whatever:testErrorString="@string/your_name_is_ugly"
           android:id="@+id/et_firstname"
           android:layout_width="match_parent"
           android:layout_height="wrap_content"
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

Calling *testValidity()* will cause the EditText to:
1. Check if the field is empty,
2. Check if the field passes the tests

It returns true if both steps are passed.
Furthermore *testValidity()* will also place an exclamation mark on the right of the  [EditText](http://developer.android.com/reference/android/widget/EditText.html) and will call the [setError](http://developer.android.com/reference/android/widget/TextView.html#setError(java.lang.CharSequence)) method.

Author
=======
*  Andrea Baccega <me@andreabaccega.com> - _Author/Ideator of the library_