package com.andreabaccega.edittextformexample;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

/**
 * A {@link PreferenceActivity} that presents a set of application settings. On handset devices, settings are presented
 * as a single list. On tablets, settings are split by category, with category headers shown to the left of the list of
 * settings.
 * <p>
 * See <a href="http://developer.android.com/design/patterns/settings.html"> Android Design: Settings</a> for design
 * guidelines and the <a href="http://developer.android.com/guide/topics/ui/settings.html">Settings API Guide</a> for
 * more information on developing a Settings UI.
 */
public class SettingsActivity
        extends PreferenceActivity {
    /**
     * Binds a preference's summary to its value. More specifically, when the preference's value is changed, its summary
     * (line of text below the preference title) is updated to reflect the value. The summary is also immediately
     * updated upon calling this method. The exact display format is dependent on the type of preference.
     *
     * @see #sBindPreferenceSummaryToValueListener
     */
    private static void bindPreferenceSummaryToValue(Preference preference) {
        // Set the listener to watch for value changes.
        preference.setOnPreferenceChangeListener(SettingsActivity.sBindPreferenceSummaryToValueListener);

        // Trigger the listener immediately with the preference's current value.
        String newValue = PreferenceManager.getDefaultSharedPreferences(preference.getContext()).getString(preference.getKey(), "");
        SettingsActivity.sBindPreferenceSummaryToValueListener.onPreferenceChange(preference, newValue);
    }

    /**
     * Determines whether the simplified settings UI should be shown. This is true if this is forced via
     * {@link #ALWAYS_SIMPLE_PREFS}, or the device doesn't have newer APIs like {@link PreferenceFragment}, or the
     * device doesn't have an extra-large screen. In these cases, a single-pane "simplified" settings UI should be
     * shown.
     */
    private static boolean isSimplePreferences(Context context) {
        return SettingsActivity.ALWAYS_SIMPLE_PREFS || Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB
                || !SettingsActivity.isXLargeTablet(context);
    }

    /**
     * Helper method to determine if the device has an extra-large screen. For example, 10" tablets are extra-large.
     */
    private static boolean isXLargeTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_XLARGE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onIsMultiPane() {
        return SettingsActivity.isXLargeTablet(this) && !SettingsActivity.isSimplePreferences(this);
    }

    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setupActionBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            // Show the Up button in the action bar.
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * Shows the simplified settings UI if the device configuration if the device configuration dictates that a
     * simplified, single-pane UI should be shown.
     */
    @SuppressWarnings("deprecation")
    private void setupSimplePreferencesScreen() {
        if (!SettingsActivity.isSimplePreferences(this)) {
            return;
        }

        // Class<ContactPreferenceFragment> a = ContactPreferenceFragment.class;

        // In the simplified UI, fragments are not used at all and we instead
        // use the older PreferenceActivity APIs.

        // Add 'general' preferences.
        PreferenceCategory fakeHeader = new PreferenceCategory(this);
        addPreferencesFromResource(R.xml.pref_contact);

        SettingsActivity.bindPreferenceSummaryToValue(findPreference(SettingsActivity.PREF_KEY_FULL_NAME));
        SettingsActivity.bindPreferenceSummaryToValue(findPreference(SettingsActivity.PREF_KEY_ADDRESS));
        SettingsActivity.bindPreferenceSummaryToValue(findPreference(SettingsActivity.PREF_KEY_PHONE_NUMBER));
        SettingsActivity.bindPreferenceSummaryToValue(findPreference(SettingsActivity.PREF_KEY_EMAIL));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupActionBar();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        setupSimplePreferencesScreen();
    }

    /**
     * This fragment shows general preferences only. It is used when the activity is showing a two-pane settings UI.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class ContactPreferenceFragment
            extends PreferenceFragment {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_contact);

            // Bind the summaries of EditText/List/Dialog/Ringtone preferences
            // to their values. When their values change, their summaries are
            // updated to reflect the new value, per the Android Design
            // guidelines.
            SettingsActivity.bindPreferenceSummaryToValue(findPreference(SettingsActivity.PREF_KEY_FULL_NAME));
            SettingsActivity.bindPreferenceSummaryToValue(findPreference(SettingsActivity.PREF_KEY_ADDRESS));
            SettingsActivity.bindPreferenceSummaryToValue(findPreference(SettingsActivity.PREF_KEY_PHONE_NUMBER));
            SettingsActivity.bindPreferenceSummaryToValue(findPreference(SettingsActivity.PREF_KEY_EMAIL));

        }
    }

    public static final String PREF_KEY_EMAIL = "email";

    public static final String PREF_KEY_PHONE_NUMBER = "phone_number";

    public static final String PREF_KEY_ADDRESS = "address";

    public static final String PREF_KEY_FULL_NAME = "full_name";

    /**
     * Determines whether to always show the simplified settings UI, where settings are presented in a single list. When
     * false, settings are shown as a master/detail two-pane view on tablets. When true, a single pane is shown on
     * tablets.
     */
    private static final boolean ALWAYS_SIMPLE_PREFS = false;

    /**
     * A preference value change listener that updates the preference's summary to reflect its new value.
     */
    private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {

        public boolean onPreferenceChange(Preference preference, Object value) {
            String stringValue = value.toString();

            if (preference instanceof ListPreference) {
                // For list preferences, look up the correct display value in
                // the preference's 'entries' list.
                ListPreference listPreference = (ListPreference) preference;
                int index = listPreference.findIndexOfValue(stringValue);

                // Set the summary to reflect the new value.
                preference.setSummary(index >= 0 ? listPreference.getEntries()[index] : null);

            } else {
                // For all other preferences, set the summary to the value's
                // simple string representation.
                preference.setSummary(stringValue);
            }
            return true;
        }
    };
}
