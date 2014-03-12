package ca.osipovas.simplefilter;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceFragment;

public class SettingsActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getFragmentManager().beginTransaction().replace(android.R.id.content, new PreferenceFragment(){
			@Override
			public void onCreate(Bundle savedInstanceState) {
				super.onCreate(savedInstanceState);
				addPreferencesFromResource(R.xml.preferences);	
				
				/**
				 * Dynamic Stuff for Validating that the Chosen Value is Odd
				 * 
				 * Used the following Resource:
				 * http://stackoverflow.com/questions/7564322/validate-preferences-android
				 */
				
				EditTextPreference filterPreference = (EditTextPreference) getPreferenceScreen().findPreference("filter_size");
				filterPreference.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
					
					@Override
					public boolean onPreferenceChange(Preference preference, Object newValue) {
						boolean isValid = true;
						if (newValue != null){
							
							Integer filterSizeInteger = Integer.parseInt((String) newValue);
							
							if ((filterSizeInteger & 1) == 0 ){
								//EVEN - BAD
								final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			                    builder.setTitle("Invalid Input");
			                    builder.setMessage("Filter Size needs to be an odd number.");
			                    builder.setPositiveButton(android.R.string.ok, null);
			                    builder.show();
			                    isValid = false;
							} 
						}
						return isValid;
					}
				});
			}
		})
        .commit();
	}
}