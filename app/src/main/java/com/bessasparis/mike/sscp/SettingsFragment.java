package com.bessasparis.mike.sscp;


import android.os.Bundle;
import android.app.Fragment;
import android.preference.PreferenceFragment;
import android.util.Log;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
    }


}
