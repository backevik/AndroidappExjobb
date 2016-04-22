package examensarbete.diacert_android;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.BuildConfig;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.Fitness;

import com.google.android.gms.fitness.data.Bucket;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;

import com.google.android.gms.fitness.request.DataReadRequest;

import com.google.android.gms.fitness.request.OnDataPointListener;

import com.google.android.gms.fitness.result.DataReadResult;


import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.jar.Manifest;



import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.fitness.result.ListSubscriptionsResult;

import examensarbete.diacert_android.R;



/**
 * Created by Martin on 2016-04-13.
 */
public class GraphViewFragment extends Fragment {


    public static final String TAG = "API";
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 1;
    private GoogleApiClient googleApiFitnessClient;
    private OnDataPointListener mListener;
    private View fragmentView;
    private LineChart chart;
    private boolean activeSubscription;
    private Context context;
    private Activity activity;

    @Override
    public void onStop() {
        super.onStop();

        if (googleApiFitnessClient != null) {
            Log.d(TAG, "onStop REACHED, client not null and is connected");
            googleApiFitnessClient.stopAutoManage((FragmentActivity) getActivity());
            googleApiFitnessClient.disconnect();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (googleApiFitnessClient != null) {
            Log.d(TAG, "onResume REACHED, client not null");
            googleApiFitnessClient.stopAutoManage((FragmentActivity) getActivity());
            googleApiFitnessClient.disconnect();
            googleApiFitnessClient.connect();
        } else {
            Log.d(TAG, "onResume REACHED, client null, buildingClient");
            buildFitnessClient();
            googleApiFitnessClient.connect();
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        fragmentView = inflater.inflate(R.layout.content_graph, container, false);

        chart = (LineChart) fragmentView.findViewById(R.id.chart);
        chart.setDescription("");
        chart.setNoDataText("Steg hämtas från Google Fit...");
        chart.setBottom(1);
        activity = getActivity();
        if (!checkPermissions()) {
            requestPermissions();
        }
        addCheckBoxListeners();
        return fragmentView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //5 is the auth request code that you send from MainActivity requestCode == 5
        if (googleApiFitnessClient != null) {
            Log.d(TAG, "Activity result finished authorissation, disconnect the client and reconnect");

            googleApiFitnessClient.stopAutoManage((FragmentActivity) getActivity());
            googleApiFitnessClient.disconnect();
            googleApiFitnessClient.connect();
        }
    }

    private void buildFitnessClient() {
        googleApiFitnessClient = new GoogleApiClient.Builder(getActivity())
                .addApi(Fitness.SENSORS_API)
                .addApi(Fitness.HISTORY_API)
                .addApi(Fitness.RECORDING_API)
//                .addScope(new Scope(Scopes.FITNESS_LOCATION_READ))
                .addScope(new Scope(Scopes.FITNESS_ACTIVITY_READ_WRITE))
//                .addScope(new Scope(Scopes.FITNESS_NUTRITION_READ_WRITE))
//                .addScope(new Scope(Scopes.FITNESS_BODY_READ_WRITE))
                .addConnectionCallbacks(
                        new GoogleApiClient.ConnectionCallbacks() {
                            @Override
                            public void onConnected(Bundle bundle) {
                                Log.d(TAG, "Connected!!!");
                                //insertStepsToApi(699,System.currentTimeMillis());
                                //addStepSubscription();
                                checkStepSubscription();
                                //insertStepsToApi(4,System.currentTimeMillis());
                                getStepsFrom(1);
                            }

                            @Override
                            public void onConnectionSuspended(int i) {
                                // If your connection to the sensor gets lost at some point,
                                // you'll be able to determine the reason and react to it here.
                                if (i == GoogleApiClient.ConnectionCallbacks.CAUSE_NETWORK_LOST) {
                                    Log.i(TAG, "Connection lost.  Cause: Network Lost.");
                                } else if (i
                                        == GoogleApiClient.ConnectionCallbacks.CAUSE_SERVICE_DISCONNECTED) {
                                    Log.d(TAG,
                                            "Connection lost.  Reason: Service Disconnected");
                                }
                            }
                        }
                )
                .addOnConnectionFailedListener(
                        new GoogleApiClient.OnConnectionFailedListener() {
                            @Override
                            public void onConnectionFailed(ConnectionResult connectionResult) {
                                Log.d(TAG, "Connection failed! " + connectionResult.getErrorMessage());
                            }
                        }
                )
                .enableAutoManage((FragmentActivity) getActivity(), 0, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(ConnectionResult result) {
                        Log.d(TAG, "Google Play services connection failed. Cause: " +
                                result.toString());
                    }
                })
                .build();
    }



    /**
     * Return the current state of the permissions needed.
     */
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
            Log.d(TAG, "Displaying permission rationale to provide additional context.");
            Snackbar.make(
                    fragmentView.findViewById(R.id.app_bar_main_coordLayout),
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
            Log.d(TAG, "Requesting permission");
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.BODY_SENSORS},
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionResult");
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                Log.d(TAG, "User interaction was cancelled.");
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                buildFitnessClient();
            } else {
                Snackbar.make(
                        fragmentView.findViewById(R.id.app_bar_main_coordLayout),
                        "permissions denied ",
                        Snackbar.LENGTH_INDEFINITE)
                        .setAction("Settings text", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // Build intent that displays the App settings screen.
                                Intent intent = new Intent();
                                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package",
                                        BuildConfig.APPLICATION_ID, null);
                                intent.setData(uri);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        })
                        .show();
            }
        }
    }

    private void getStepsFrom(int nrOfWeeks) {
        Log.d(TAG, "Inside Getsteps");
        // Setting a start and end date using a range of pastweeks before this moment.
        Calendar cal = Calendar.getInstance();
        Date now = new Date();
        cal.setTime(now);
        long endTime = cal.getTimeInMillis();
        Log.d(TAG,"Gets steps to:"  + new Date(endTime).toString());
        cal.add(Calendar.WEEK_OF_YEAR, -nrOfWeeks);


        long startTime = cal.getTimeInMillis();
        Log.d(TAG,"Gets steps from:"  + new Date(startTime).toString());

        final DataReadRequest dataReadRequest = new DataReadRequest.Builder()
                .aggregate(DataType.TYPE_STEP_COUNT_DELTA, DataType.AGGREGATE_STEP_COUNT_DELTA)
                .bucketByTime(1, TimeUnit.DAYS.DAYS)
                .setTimeRange(startTime, endTime, TimeUnit.MILLISECONDS)
                .build();

        PendingResult<DataReadResult> pendingResult = Fitness.HistoryApi.readData(googleApiFitnessClient, dataReadRequest);
        pendingResult.setResultCallback(
                new ResultCallback<DataReadResult>() {
                    @Override
                    public void onResult(@NonNull DataReadResult dataReadResult) {
                        Log.d(TAG,"Processing data from historyAPI");
                        final ArrayList<Float> steps= new ArrayList<Float>();
                        if (dataReadResult.getBuckets().size() > 0) {
                            for (Bucket bucket : dataReadResult.getBuckets()) {
                                List<DataSet> dataSets = bucket.getDataSets();
                                for (DataSet dSet : dataSets) {
                                    Log.d(TAG, "Print av data sets "+ dSet.toString());
                                    Log.d(TAG, "new Dataset");
                                    for (DataPoint dp : dSet.getDataPoints()) {
                                        DateFormat dateFormat = DateFormat.getTimeInstance();
                                        long dpStart = dp.getStartTime(TimeUnit.NANOSECONDS)/1000000;
                                        long dpEnd = dp.getEndTime(TimeUnit.NANOSECONDS)/ 1000000;
                                        float totalStep = 0;
                                        Log.i(TAG, "Data point:");
                                        Log.i(TAG, "\tType: " + dp.getDataType().getName());
                                        Log.i(TAG, "\tStart: " + dateFormat.format(dp.getStartTime(TimeUnit.DAYS)));
                                        Log.i(TAG, "\tEnd: " + dateFormat.format(dp.getEndTime(TimeUnit.DAYS)));
                                        for(Field field : dp.getDataType().getFields()) {
                                            Log.i(TAG, "\tField: " + field.getName() +
                                                    " Value: " + dp.getValue(field));
                                            totalStep = totalStep + dp.getValue(field).asInt();
                                        }
                                        steps.add(totalStep);
                                        Log.d(TAG, "Total steps " + totalStep +"\n"+ "Day dp: "+ new Date(dp.getTimestamp(TimeUnit.MILLISECONDS)).toString());
                                    }

                                }
                            }
                        }
                       setGraphData(steps);
                    }
                }
        );
    }


    private void insertStepsToApi(int nrOfSteps, long TimeMilliSec){


        Calendar cal = Calendar.getInstance();
        Date now = new Date(TimeMilliSec);
        cal.setTime(now);

        long endTime = cal.getTimeInMillis();
        Log.d(TAG,"Date of insertion end:"  + new Date(endTime).toString());

        cal.add(Calendar.MILLISECOND, -1);
        long startTime = cal.getTimeInMillis();
        Log.d(TAG,"Date of insertion start:"  + new Date(startTime).toString());


        DataSource dataSource = new DataSource.Builder()
                .setAppPackageName(getActivity())
                .setDataType(DataType.TYPE_STEP_COUNT_DELTA)
                .setStreamName(TAG + " - step count")
                .setType(DataSource.TYPE_RAW)
                .build();

        DataSet dataSet = DataSet.create(dataSource);

        DataPoint dataPoint = dataSet.createDataPoint()
                .setTimeInterval(startTime, endTime, TimeUnit.MILLISECONDS);
        dataPoint.getValue(Field.FIELD_STEPS).setInt(nrOfSteps);
        dataSet.add(dataPoint);




        Log.i(TAG, "Inserting the dataset in the History API.");
        PendingResult<com.google.android.gms.common.api.Status> pendingInsertStatus =  Fitness.HistoryApi.insertData(googleApiFitnessClient, dataSet);
        pendingInsertStatus.setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if(!status.isSuccess()){
                    Log.i(TAG, "There was a problem inserting the dataset.");
                }else{
                    Log.i(TAG, "Data insert was successful!");
                }

            }
        });



    }

    public boolean checkStepSubscription(){

        if (googleApiFitnessClient == null){
            if (!checkPermissions()) {
                requestPermissions();
            }else{
                buildFitnessClient();
            }
        }
        Fitness.RecordingApi.listSubscriptions(googleApiFitnessClient,DataType.TYPE_STEP_COUNT_DELTA)
                .setResultCallback(new ResultCallback<ListSubscriptionsResult>() {
                    @Override
                    public void onResult(@NonNull ListSubscriptionsResult listSubscriptionsResult) {

                        Log.d(TAG,"subscriptions list"+listSubscriptionsResult.getSubscriptions().toString().contains("step_count.delta"));
                        activeSubscription = listSubscriptionsResult.getSubscriptions().toString().contains("step_count.delta");

                    }
                });
        return activeSubscription;
    }

    private void setGraphData(ArrayList<Float> steps) {

        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < steps.size(); i++) {
            xVals.add((i) + "");
        }
        ArrayList<Entry> yVals = new ArrayList<Entry>();

        for (int i = 0; i < steps.size(); i++) {

            yVals.add(new Entry(steps.get(i), i));
        }
        LineDataSet lineDataSet = new LineDataSet(yVals,"Steg");
        lineDataSet.setLineWidth(3);
        lineDataSet.setCircleRadius(5);
        lineDataSet.setDrawFilled(true);
        lineDataSet.setColor(Color.parseColor("#00ADD0"));
        lineDataSet.setCircleColor(Color.parseColor("#0075B0"));

        ArrayList<ILineDataSet> iLineDataSets = new ArrayList<ILineDataSet>();
        iLineDataSets.add(lineDataSet);
        LineData linedata = new LineData(xVals,iLineDataSets);
        linedata.setValueTextSize(9);

        chart.setData(linedata);
        chart.invalidate();


    }
    private void addCheckBoxListeners(){

        final CheckBox week = (CheckBox) fragmentView.findViewById(R.id.week);
        final CheckBox one_month = (CheckBox) fragmentView.findViewById(R.id.one_month);
        final CheckBox three_months = (CheckBox) fragmentView.findViewById(R.id.three_months);
        final CheckBox six_months = (CheckBox) fragmentView.findViewById(R.id.six_months);
        final CheckBox year = (CheckBox) fragmentView.findViewById(R.id.year);

        week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getStepsFrom(1);
                if(!week.isChecked()){week.toggle();}
                if (one_month.isChecked()){ one_month.toggle();}
                if (three_months.isChecked()){ three_months.toggle();}
                if (six_months.isChecked()){ six_months.toggle();}
                if (year.isChecked()){ year.toggle();}
            }
        });
        one_month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getStepsFrom(4);
                if (!one_month.isChecked()){one_month.toggle();}
                if (week.isChecked()){ week.toggle();}
                if (three_months.isChecked()){ three_months.toggle();}
                if (six_months.isChecked()){ six_months.toggle();}
                if (year.isChecked()){ year.toggle();}
            }
        });
        three_months.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getStepsFrom(13);
                if (!three_months.isChecked()){three_months.toggle();}
                if (week.isChecked()){ week.toggle();}
                if (one_month.isChecked()){ one_month.toggle();}
                if (six_months.isChecked()){ six_months.toggle();}
                if (year.isChecked()){ year.toggle();}
            }
        });
        six_months.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getStepsFrom(26);
                if (!six_months.isChecked()){ six_months.toggle();}
                if (week.isChecked()){ week.toggle();}
                if (one_month.isChecked()){ one_month.toggle();}
                if (three_months.isChecked()){ three_months.toggle();}
                if (year.isChecked()){ year.toggle();}
            }
        });
        year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getStepsFrom(52);
                if (!year.isChecked()){year.isChecked();}
                if (week.isChecked()){ week.toggle();}
                if (one_month.isChecked()){ one_month.toggle();}
                if (three_months.isChecked()){ three_months.toggle();}
                if (six_months.isChecked()){ six_months.toggle();}
            }
        });
    }

}