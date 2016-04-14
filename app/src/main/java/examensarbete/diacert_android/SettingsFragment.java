package examensarbete.diacert_android;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import examensarbete.diacert_android.Database.KeyDBHandler;

/**
 * Created by backevik on 16-04-10.
 */
public class SettingsFragment extends PreferenceFragment {
    private CheckBoxPreference unpairCheckbox;
    private KeyDBHandler keyDBHandler;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        keyDBHandler = new KeyDBHandler(getActivity(), null);

        unpairCheckbox = (CheckBoxPreference) findPreference("pref_key_connect");
        unpairCheckbox.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                if(unpairCheckbox.isChecked()){
                    showUnpairDialog();
                    return true;
                }else{
                    return false;
                }
            }
        });
    }

    private void showUnpairDialog(){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
        builder1.setMessage("Vill du verkligen ta bort parning?");
        builder1.setCancelable(true);
        builder1.setTitle("Fel!");
        builder1.setIcon(R.drawable.ic_popup_warning);
        builder1.setPositiveButton(
                "Ja",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        keyDBHandler.removeData(keyDBHandler.getData());
                        Intent intent = new Intent(getActivity(), ConnectionActivity.class);
                        Bundle mBundle = new Bundle();
                        mBundle.putString("API", "MOCK");
                        intent.putExtras(mBundle);
                        startActivity(intent);
                        getActivity().overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                    }
                });
        builder1.setNegativeButton(
                "Nej",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }



}
