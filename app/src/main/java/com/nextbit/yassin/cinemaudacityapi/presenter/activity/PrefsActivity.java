package com.nextbit.yassin.cinemaudacityapi.presenter.activity;

import android.content.Intent;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.nextbit.yassin.cinemaudacityapi.R;

public class PrefsActivity extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref);
        final ListPreference pLocAuto = (ListPreference) findPreference("listpref");
        pLocAuto.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                int index = pLocAuto.findIndexOfValue(newValue.toString());
                Log.e("Sass",""+index+"");
                if (index == 1)
                {
                    Toast.makeText(getBaseContext(), pLocAuto.getEntries()[index], Toast.LENGTH_LONG).show();
                    Intent refresh = new Intent(PrefsActivity.this, MainActivity.class);
                    startActivity(refresh);//Start the same Activity
                    finishActivity(0);
                    finish();
                    //finish Activity.

                }
                else {
                    Toast.makeText(getBaseContext(), pLocAuto.getEntries()[index], Toast.LENGTH_LONG).show();
                    Intent refresh = new Intent(PrefsActivity.this, MainActivity.class);
                    startActivity(refresh);//Start the same Activity
                    finishActivity(0);
                    finish();
                    //finish Activity.
                }
                return true;
            }
        });


    }
    public void onBackPressed() {
        finishActivity(0);
    }
}
