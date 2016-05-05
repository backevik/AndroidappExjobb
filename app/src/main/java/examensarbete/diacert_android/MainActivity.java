package examensarbete.diacert_android;

import android.Manifest;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

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
import com.google.android.gms.fitness.result.DataReadResult;
import com.google.android.gms.fitness.result.ListSubscriptionsResult;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import examensarbete.diacert_android.API.TestAPI;
import examensarbete.diacert_android.Database.KeyDBHandler;
import examensarbete.diacert_android.Database.StepsDBHandler;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private Toolbar toolbar;
    private OverviewFragment overviewFragment;
    private LogFragment logFragment;
    private GoogleApiClient googleApiClient;
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 1;
    private boolean activeSubscription = true;
    private String googleApiCaller;
    public static final String TAG = "STEPSWEEKS";
    private StepsDBHandler stepsDBHandler;
    //private MainActivity activity;


    @Override
    public void onStop() {
        super.onStop();

        if (googleApiClient != null) {
            googleApiClient.stopAutoManage(this);
            googleApiClient.disconnect();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (googleApiClient != null) {
            googleApiClient.stopAutoManage(this);
            googleApiClient.disconnect();
            googleApiClient.connect();
        } else {
            buildFitnessClient();
            googleApiClient.connect();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        //activity = this;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Översikt");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        OverviewFragment overviewFragment = new OverviewFragment();
        fragmentTransaction.add(R.id.app_bar_main_coordLayout, overviewFragment, "OverviewFragment Active");
        fragmentTransaction.commit();

        stepsDBHandler = new StepsDBHandler(this,null);

        setApiCaller("main");
        if (!checkPermissions()) {
            requestPermissions();
        }



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            LicensesDialog aid = new LicensesDialog(this);
            aid.getWindow().getAttributes().windowAnimations = R.style.InfoDialogAnimation;
            aid.show();
            return true;
        } else if (id == R.id.action_help) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.overview) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.animator.fade_in, R.animator.fade_out);
            OverviewFragment overviewFragment = new OverviewFragment();
            fragmentTransaction.replace(R.id.app_bar_main_coordLayout, overviewFragment, "LogFragment Active");
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            toolbar.setTitle("Översikt");
        } else if (id == R.id.message) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.animator.fade_in, R.animator.fade_out);
            ChatFragment chatFragment = new ChatFragment();
            fragmentTransaction.replace(R.id.app_bar_main_coordLayout, chatFragment, "Message Active");
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            toolbar.setTitle("Meddelanden");
        } else if (id == R.id.log) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.animator.fade_in, R.animator.fade_out);
            LogFragment logFragment = new LogFragment();
            fragmentTransaction.replace(R.id.app_bar_main_coordLayout, logFragment, "LogFragment Active");
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            toolbar.setTitle("Logg");
        }else if (id == R.id.settings){
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.animator.fade_in, R.animator.fade_out);
            SettingsFragment settingsFragment = new SettingsFragment();
            fragmentTransaction.replace(R.id.app_bar_main_coordLayout, settingsFragment, "SettingsFragment Active");
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            toolbar.setTitle("Inställningar");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void buildFitnessClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Fitness.HISTORY_API)
                .addApi(Fitness.RECORDING_API)
                .addScope(new Scope(Scopes.FITNESS_ACTIVITY_READ_WRITE))
                .addConnectionCallbacks(
                        new GoogleApiClient.ConnectionCallbacks() {
                            @Override
                            public void onConnected(Bundle bundle) {
                                //insertStepsToApi(5000, System.currentTimeMillis());
                                if(googleApiCaller.equals("settings")){
                                    FragmentManager fm = getFragmentManager();
                                    SettingsFragment settingsFragment = (SettingsFragment) fm.findFragmentByTag("SettingsFragment Active");
                                    settingsFragment.setRecordSteps(checkStepSubscription());
                                }else if (googleApiCaller.equals("steps")){
                                    getStepsFromWeeks(1);
                                }else if(googleApiCaller.equals("main")){
                                    if(checkStepSubscription()){
                                        Log.d("main", "updating steps");
                                        //get time from db
                                        Log.d(TAG,"timestamp" +stepsDBHandler.getData());

                                        getStepsFrom(stepsDBHandler.getData());
                                    }
                                }
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
                .enableAutoManage(this, 0, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(ConnectionResult result) {
                        Log.d("tag", "Google Play services connection failed. Cause: " +
                                result.toString());
                    }
                })
                .build();
    }
    public boolean checkPermissions() {
        int permissionState = ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.BODY_SENSORS);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    public void requestPermissions() {

        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                        Manifest.permission.BODY_SENSORS);
        Log.d("main", "request permissions "+ shouldProvideRationale);
        if (shouldProvideRationale) {
            Log.d("tag", "Displaying permission rationale to provide additional context.");
            Snackbar.make(
                    findViewById(R.id.app_bar_main_coordLayout),
                    "text for snackbar",
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Request permission
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[]{Manifest.permission.BODY_SENSORS},
                                    REQUEST_PERMISSIONS_REQUEST_CODE);
                        }
                    })
                    .show();
        } else {
            Log.d("main", "should display login");
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.BODY_SENSORS},
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //5 is the auth request code that you send from MainActivity requestCode == 5
        if (googleApiClient != null) {
            Log.d("main", "Activity result finished authorissation, disconnect the client and reconnect");

            googleApiClient.stopAutoManage(MainActivity.this);
            googleApiClient.disconnect();
            googleApiClient.connect();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        Log.d("requestperm", "onRequestPermissionResult");
        Log.d("results perm", Integer.toString(grantResults[0]));
        Log.d("results perm", Integer.toString(PackageManager.PERMISSION_GRANTED ));
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                Log.d("requestperm", "User interaction was cancelled.");
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if(googleApiClient == null){
                    buildFitnessClient();
                }
            } else {
                Snackbar.make(
                        findViewById(R.id.app_bar_main_coordLayout),
                        "Tillgång till steg nekades ",
                        Snackbar.LENGTH_INDEFINITE)
                        .setAction("", new View.OnClickListener() {
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
    private void getStepsFrom(long startTime) {
        Log.d(TAG, "tries to get steps");
        final DataReadRequest dataReadRequest = new DataReadRequest.Builder()
                .aggregate(DataType.TYPE_STEP_COUNT_DELTA, DataType.AGGREGATE_STEP_COUNT_DELTA)
                .bucketByTime(1, TimeUnit.DAYS.DAYS)
                .setTimeRange(startTime, System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .build();

        PendingResult<DataReadResult> pendingResult = Fitness.HistoryApi.readData(googleApiClient, dataReadRequest);
        pendingResult.setResultCallback(
                new ResultCallback<DataReadResult>() {
                    @Override
                    public void onResult(@NonNull DataReadResult dataReadResult) {
                        final HashMap<Long, Integer> steps= new HashMap<Long, Integer>();
                        if (dataReadResult.getBuckets().size() > 0) {
                            for (Bucket bucket : dataReadResult.getBuckets()) {
                                List<DataSet> dataSets = bucket.getDataSets();
                                for (DataSet dSet : dataSets) {
                                    for (DataPoint dp : dSet.getDataPoints()) {
                                        DateFormat dateFormat = DateFormat.getTimeInstance();
                                        long dpStart = dp.getStartTime(TimeUnit.NANOSECONDS)/1000000;
                                        long dpEnd = dp.getEndTime(TimeUnit.NANOSECONDS)/ 1000000;
                                        int totalStep = 0;
                                        for(Field field : dp.getDataType().getFields()) {

                                            totalStep = totalStep + dp.getValue(field).asInt();
                                        }
                                        steps.put(dp.getTimestamp(TimeUnit.MILLISECONDS),totalStep);
                                        //Log.d(TAG, "Total steps " + totalStep +"\n"+ "Day dp: "+ new Date(dp.getTimestamp(TimeUnit.MILLISECONDS)).toString());
                                    }

                                }
                            }
                        }
                        uploadSteps(steps);
                    }
                }
        );
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

    void uploadSteps(HashMap<Long, Integer> steps){
        Log.d("upload", steps.toString());
        KeyDBHandler keyDBHandler = new KeyDBHandler(this,null);
        String resp = "";

        for(Map.Entry<Long,Integer> entry : steps.entrySet()){

            String timeStamp = entry.getKey().toString();
            String nrOfSteps = entry.getValue().toString();
            try {//finish parameters in POST request.

                resp = new TestAPI().execute("steps",keyDBHandler.getData(),nrOfSteps , timeStamp).get();

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        stepsDBHandler.addData(System.currentTimeMillis());
    }

    public void getStepsFromWeeks(int nrOfWeeks) {
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

        PendingResult<DataReadResult> pendingResult = Fitness.HistoryApi.readData(googleApiClient, dataReadRequest);
        pendingResult.setResultCallback(
                new ResultCallback<DataReadResult>() {
                    @Override
                    public void onResult(@NonNull DataReadResult dataReadResult) {
                        Log.d(TAG,"Processing data from historyAPI");
                        final TreeMap<Long,Integer> steps= new TreeMap<Long, Integer>();
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
                                        int totalStep = 0;
                                        Log.i(TAG, "Data point:");
                                        Log.i(TAG, "\tType: " + dp.getDataType().getName());
                                        Log.i(TAG, "\tStart: " + dateFormat.format(dp.getStartTime(TimeUnit.MILLISECONDS)));
                                        Log.i(TAG, "\tEnd: " + dateFormat.format(dp.getEndTime(TimeUnit.DAYS)));
                                        for(Field field : dp.getDataType().getFields()) {
                                            Log.i(TAG, "\tField: " + field.getName() +
                                                    " Value: " + dp.getValue(field));
                                            totalStep = totalStep + dp.getValue(field).asInt();
                                        }
                                        steps.put(dp.getTimestamp(TimeUnit.MILLISECONDS),totalStep);
                                        Log.d(TAG, "Total steps " + totalStep +"\n"+ "Day dp: "+ new Date(dp.getTimestamp(TimeUnit.MILLISECONDS)).toString());
                                    }

                                }
                            }
                        }
                        FragmentManager fm = getFragmentManager();
                        GraphViewFragment graphView = (GraphViewFragment) fm.findFragmentByTag("Graph Active");
                        Log.d(TAG,steps.toString());
                        Log.d("total",steps.toString());
                        graphView.setGraphData(steps);
                    }
                }
        );
    }
    public void setApiCaller(String caller){
        googleApiCaller = caller;
    }

    private void insertStepsToApi(int nrOfSteps, long TimeMilliSec){


        Calendar cal = Calendar.getInstance();
        Date now = new Date(TimeMilliSec);
        cal.setTime(now);

        long endTime = cal.getTimeInMillis();
        Log.d(TAG,"Date of insertion end:"  + new Date(endTime).toString());

        cal.add(Calendar.MILLISECOND, -9999);
        long startTime = cal.getTimeInMillis();
        Log.d(TAG,"Date of insertion start:"  + new Date(startTime).toString());


        DataSource dataSource = new DataSource.Builder()
                .setAppPackageName(this)
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
        PendingResult<com.google.android.gms.common.api.Status> pendingInsertStatus =  Fitness.HistoryApi.insertData(googleApiClient, dataSet);
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

    public void addStepSubscription() {
        if (googleApiClient == null){
            setApiCaller("");
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
            setApiCaller("settings");
            if (!checkPermissions()) {
                requestPermissions();
            }else{
                buildFitnessClient();
            }
        }
        Fitness.RecordingApi.unsubscribe(googleApiClient, DataType.TYPE_STEP_COUNT_DELTA);

    }


}
