package examensarbete.diacert_android;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import examensarbete.diacert_android.Database.LogDBHandler;

/**
 * Created by backevik on 16-04-08.
 */
public class LogFragment extends Fragment{

    private ListView lv;
    private Context context;
    private LogDBHandler logDBHandler;

    private ArrayList<HashMap<String, String>> list;

    private HashMap<Long, ArrayList<String>> tempMap;
    private ArrayList<String> tempList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.content_log, container, false);

        ListView listView=(ListView)v.findViewById(R.id.logListView);

        logDBHandler = new LogDBHandler(getActivity(),null);

        tempMap = new HashMap<>();
        tempList = new ArrayList<>();

        list=new ArrayList<HashMap<String,String>>();

        tempMap = logDBHandler.getData();

        for(long date : tempMap.keySet()){
            tempList = tempMap.get(date);

            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(date);
            int month = cal.get(Calendar.MONTH) + 1 ;
            String dateString = cal.get(Calendar.DAY_OF_MONTH)+"/"+month+"-"+cal.get(Calendar.YEAR);
            addDataToLog(tempList.get(0),tempList.get(1),dateString);
        }

        LogAdaptor adapter=new LogAdaptor(this, list);
        listView.setAdapter(adapter);

        context=v.getContext();

        return v;
    }

    public void addDataToLog(final String name, final String data, final String date){
        list.add(new HashMap<String, String>(){{
            put("Name",name);
            put("Data",data);
            put("Date",date);
        }});
    }
}
