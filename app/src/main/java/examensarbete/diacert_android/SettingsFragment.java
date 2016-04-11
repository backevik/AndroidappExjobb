package examensarbete.diacert_android;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by backevik on 16-04-10.
 */
public class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }

}
