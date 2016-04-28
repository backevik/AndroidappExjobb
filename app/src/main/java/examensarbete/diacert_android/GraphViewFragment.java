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
        ((MainActivity)getActivity()).onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity)getActivity()).setApiCaller("steps");
        ((MainActivity)getActivity()).onResume();

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        fragmentView = inflater.inflate(R.layout.content_graph, container, false);

        chart = (LineChart) fragmentView.findViewById(R.id.chart);
        chart.setDescription("");
        chart.setNoDataText("Steg hämtas från Google Fit...");
        chart.setBottom(1);
        activity = getActivity();
        if (!((MainActivity)getActivity()).checkPermissions()) {
            ((MainActivity)getActivity()).requestPermissions();
        }
        addCheckBoxListeners();
        return fragmentView;
    }

    public void setGraphData(ArrayList<Float> steps) {

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
                ((MainActivity)getActivity()).getStepsFromWeeks(1);
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
                ((MainActivity)getActivity()).getStepsFromWeeks(4);
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
                ((MainActivity)getActivity()).getStepsFromWeeks(13);
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
                ((MainActivity)getActivity()).getStepsFromWeeks(26);
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
                ((MainActivity)getActivity()).getStepsFromWeeks(52);
                if (!year.isChecked()){year.isChecked();}
                if (week.isChecked()){ week.toggle();}
                if (one_month.isChecked()){ one_month.toggle();}
                if (three_months.isChecked()){ three_months.toggle();}
                if (six_months.isChecked()){ six_months.toggle();}
            }
        });
    }

}