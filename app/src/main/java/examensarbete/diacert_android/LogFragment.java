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
import java.util.Date;
import java.util.HashMap;

/**
 * Created by backevik on 16-04-08.
 */
public class LogFragment extends Fragment{

    ListView lv;
    Context context;

    private ArrayList<HashMap<String, String>> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.content_log, container, false);

        ListView listView=(ListView)v.findViewById(R.id.logListView);

        list=new ArrayList<HashMap<String,String>>();

        addDataToLog("Steps","250","08/04-16");
        addDataToLog("Steps","2500","07/04-16");
        addDataToLog("Steps","900","05/04-16");
        addDataToLog("Steps","670","03/04-16");
        addDataToLog("Steps","1600","02/04-16");
        addDataToLog("Steps","1900","01/04-16");

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
