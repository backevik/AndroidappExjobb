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

        if (googleApiClient != null) {
            googleApiClient.stopAutoManage((FragmentActivity) getActivity());
            googleApiClient.disconnect();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (googleApiClient != null) {
            googleApiClient.stopAutoManage((FragmentActivity) getActivity());
            googleApiClient.disconnect();
            googleApiClient.connect();
        } else {
            buildFitnessClient();
            googleApiClient.connect();
        }
    }



    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        if (!checkPermissions()) {
            requestPermissions();
        }

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

        recordSteps = (CheckBoxPreference) findPreference("stepRecording");
        recordSteps.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener(){
            @Override
            public boolean onPreferenceClick(Preference preference) {
                if(recordSteps.isChecked()){
                    addStepSubscription();
                    return true;
                }else{
                    removeStepSubscription();
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

    private void buildFitnessClient() {
        googleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(Fitness.RECORDING_API)
                .addScope(new Scope(Scopes.FITNESS_ACTIVITY_READ_WRITE))
                .addConnectionCallbacks(
                        new GoogleApiClient.ConnectionCallbacks() {
                            @Override
                            public void onConnected(Bundle bundle) {
                                recordSteps.setDefaultValue(checkStepSubscription());
                            }

                            @Override
                            public void onConnectionSuspended(int i) {
                                // If your connection to the sensor gets lost at some point,
                                // you'll be able to determine the reason and react to it here.
                                if (i == GoogleApiClient.ConnectionCallbacks.CAUSE_NETWORK_LOST) {
                                    Log.d("tag","Connection lost.  Cause: Network Lost.");
                                } else if (i
                                        == GoogleApiClient.ConnectionCallbacks.CAUSE_SERVICE_DISCONNECTED) {
                                    Log.d("tag",
                                            "Connection lost.  Reason: Service Disconnected");
                                }
                            }
                        }
                )
                .addOnConnectionFailedListener(
                        new GoogleApiClient.OnConnectionFailedListener() {
                            @Override
                            public void onConnectionFailed(ConnectionResult connectionResult) {
                                Log.d("tag", "Connection failed! " + connectionResult.getErrorMessage());
                            }
                        }
                )
                .enableAutoManage((FragmentActivity) getActivity(), 0, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(ConnectionResult result) {
                        Log.d("tag", "Google Play services connection failed. Cause: " +
                                result.toString());
                    }
                })
                .build();
    }

    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(getActivity(),
                android.Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                        android.Manifest.permission.ACCESS_FINE_LOCATION);

        if (shouldProvideRationale) {
            Log.d("tag", "Displaying permission rationale to provide additional context.");
            Snackbar.make(
                    getActivity().findViewById(R.id.app_bar_main_coordLayout),
                    "text for snackbar",
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Request permission
                            ActivityCompat.requestPermissions(getActivity(),
                                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.BODY_SENSORS},
                                    REQUEST_PERMISSIONS_REQUEST_CODE);
                        }
                    })
                    .show();
        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.BODY_SENSORS},
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                Log.d("tag", "User interaction was cancelled.");
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                buildFitnessClient();
            } else {
                Snackbar.make(
                        getActivity().findViewById(R.id.app_bar_main_coordLayout),
                        "permissions denied ",
                        Snackbar.LENGTH_INDEFINITE)
                        .setAction("Settings text", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // Build intent that displays the App settings screen.
                                Intent intent = new Intent();
                                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package",
                                        android.support.design.BuildConfig.APPLICATION_ID, null);
                                intent.setData(uri);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        })
                        .show();
            }
        }
    }
    public boolean checkStepSubscription(){

        if (googleApiClient == null){
            buildFitnessClient();
            if (!checkPermissions()) {
                requestPermissions();
            }else{
                buildFitnessClient();
            }
        }
        Fitness.RecordingApi.listSubscriptions(googleApiClient, DataType.TYPE_STEP_COUNT_DELTA)
                .setResultCallback(new ResultCallback<ListSubscriptionsResult>() {
                    @Override
                    public void onResult(@NonNull ListSubscriptionsResult listSubscriptionsResult) {
                        activeSubscription = listSubscriptionsResult.getSubscriptions().toString().contains("step_count.delta");
                    }
                });

        return activeSubscription;
    }
    public void addStepSubscription() {
        if (googleApiClient == null){
            if (!checkPermissions()) {
                requestPermissions();
            }else{
                buildFitnessClient();
            }
        }
        Fitness.RecordingApi.subscribe(googleApiClient, DataType.TYPE_STEP_COUNT_DELTA);
    }

    public void removeStepSubscription(){
        if (googleApiClient == null){
            if (!checkPermissions()) {
                requestPermissions();
            }else{
                buildFitnessClient();
            }
        }
        Fitness.RecordingApi.unsubscribe(googleApiClient, DataType.TYPE_STEP_COUNT_DELTA);

    }
}
