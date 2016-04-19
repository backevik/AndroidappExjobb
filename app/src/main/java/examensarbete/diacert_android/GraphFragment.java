package examensarbete.diacert_android;


import android.app.Activity;
import android.app.Fragment;

import android.content.Context;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.data.Bucket;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.request.DataReadRequest;
import com.google.android.gms.fitness.result.DataReadResult;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by Martin on 2016-04-11.
 *
 */
public class GraphFragment extends Fragment {
    private GoogleApiClient mClient = null;
    private Context context = null;
    private View fView = null;
    private Activity parentActivity = null;
/*
    @Override
    public void onStop() {
        super.onStop();

        if (mClient != null) {
            Log.d("Graph", "onStop REACHED, client not null and is connected");
            mClient.stopAutoManage(getActivity());
            mClient.disconnect();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mClient != null) {
            Log.d("Graph", "onResume REACHED, client not null");
            mClient.stopAutoManage(getActivity());
            mClient.disconnect();
            mClient.connect();
        } else {
            Log.d("Graph", "onResume REACHED, client null, buildingClient");
            buildFitnessClient();
            mClient.connect();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Activity) {
            parentActivity = (Activity) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        fView = inflater.inflate(R.layout.content_graph, container, false);

        initializeLogging();

        if (!checkPermissions()) {
            requestPermissions();
        }

        return fragmentView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Main_Activity.USER_AUTHORISED_REQUEST_CODE && googleApiFitnessClient != null) {
            Log.d(TAG, "Activity result finished authorissation, disconnect the client and reconnect");

            googleApiFitnessClient.stopAutoManage(getActivity());
            googleApiFitnessClient.disconnect();
            googleApiFitnessClient.connect();
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.content_graph, container, false);
        context = v.getContext();
        buildFitnessClient();
        //getStepsFrom(4);
        return v;
    }

    private void buildFitnessClient() {
        if (mClient == null) {
            mClient = new GoogleApiClient.Builder(parentActivity)
                    .addApi(Fitness.HISTORY_API)
                    .addScope(new Scope(Scopes.FITNESS_ACTIVITY_READ))
                    .addConnectionCallbacks(
                            new GoogleApiClient.ConnectionCallbacks() {
                                @Override
                                public void onConnected(Bundle bundle) {

                                    // Now you can make calls to the Fitness APIs.
                                    Log.d("Graph", "connected");
                                    getStepsFrom(4);
                                }

                                @Override
                                public void onConnectionSuspended(int i) {
                                    // If your connection to the sensor gets lost at some point,
                                    // you'll be able to determine the reason and react to it here.
                                    if (i == GoogleApiClient.ConnectionCallbacks.CAUSE_NETWORK_LOST) {
                                        //Log.i(TAG, "Connection lost.  Cause: Network Lost.");
                                    } else if (i == GoogleApiClient.ConnectionCallbacks.CAUSE_SERVICE_DISCONNECTED) {
                                        //Log.i(TAG, "Connection lost.  Reason: Service Disconnected");
                                    }
                                }
                            }
                    )
                    .addOnConnectionFailedListener(
                            new GoogleApiClient.OnConnectionFailedListener() {
                                @Override
                                public void onConnectionFailed(ConnectionResult connectionResult) {
                                    Log.d("Graph", "Connection failed! " + connectionResult.getErrorMessage());
                                }
                            }
                    )
                    .enableAutoManage((FragmentActivity) parentActivity, 0, new GoogleApiClient.OnConnectionFailedListener() {
                        @Override
                        public void onConnectionFailed(ConnectionResult result) {
                            Log.d("Graph", "Google Play services connection failed. Cause: " +
                                    result.toString());
                        }
                    })
                    .build();

        }
    }


    private void getStepsFrom(int pastweeks){

        // Setting a start and end date using a range of pastweeks before this moment.
        Calendar cal = Calendar.getInstance();
        Date now = new Date();
        cal.setTime(now);
        long endTime = cal.getTimeInMillis();
        cal.add(Calendar.WEEK_OF_YEAR, -pastweeks);
        long startTime = cal.getTimeInMillis();

        final DataReadRequest dataReadRequest = new DataReadRequest.Builder()
                .aggregate(DataType.TYPE_STEP_COUNT_DELTA, DataType.AGGREGATE_STEP_COUNT_DELTA)
                .bucketByTime(1, TimeUnit.DAYS.DAYS)
                .setTimeRange(startTime, endTime, TimeUnit.MILLISECONDS)
                .build();
        PendingResult<DataReadResult> pendingResult = Fitness.HistoryApi.readData(mClient,dataReadRequest);
        pendingResult.setResultCallback(
                new ResultCallback<DataReadResult>() {
                    @Override
                    public void onResult(@NonNull DataReadResult dataReadResult) {
                        if (dataReadResult.getBuckets().size() >0){
                            for(Bucket bucket: dataReadResult.getBuckets()){

                            }
                        }
                    }
                }
        );





    }

*/
}