package examensarbete.diacert_android;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.*;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessStatusCodes;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.result.ListSubscriptionsResult;

import examensarbete.diacert_android.Database.KeyDBHandler;

/**
 * Created by backevik on 16-04-10.
 */
public class SettingsFragment extends PreferenceFragment {
    private CheckBoxPreference unpairCheckbox, recordSteps;
    private KeyDBHandler keyDBHandler;
    private GoogleApiClient googleApiClient;
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 1;
    private boolean activeSubscription;


    @Override
    public void onStop() {
        super.onStop();
        ((MainActivity)getActivity()).onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity)getActivity()).onResume();
    }



    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        if (!((MainActivity)getActivity()).checkPermissions()) {
            ((MainActivity)getActivity()).requestPermissions();
        }

        keyDBHandler = new KeyDBHandler(getActivity(), null);

        unpairCheckbox = (CheckBoxPreference) findPreference("pref_key_connect");
        unpairCheckbox.setChecked(true);
        unpairCheckbox.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                if(!unpairCheckbox.isChecked()){
                    showUnpairDialog();
                    return true;
                }else{
                    return false;
                }
            }
        });

        recordSteps = (CheckBoxPreference) findPreference("stepRecording");
        recordSteps.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener(){
            @Override
            public boolean onPreferenceClick(Preference preference) {
                if(recordSteps.isChecked()){
                    ((MainActivity)getActivity()).addStepSubscription();
                    return true;
                }else{
                    ((MainActivity)getActivity()).removeStepSubscription();
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
                        keyDBHandler.removeData();
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


    public void setRecordSteps(boolean value){
        recordSteps.setDefaultValue(value);
    }
}
