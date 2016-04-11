package examensarbete.diacert_android;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by backevik on 16-04-08.
 */
public class LogAdaptor extends BaseAdapter {
    public ArrayList<HashMap<String, String>> list;
    Fragment fragment;
    TextView textViewName;
    TextView textViewData;
    TextView textViewDate;

    public LogAdaptor(Fragment fragment, ArrayList<HashMap<String, String>> list){
        super();
        this.fragment=fragment;
        this.list=list;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub



        LayoutInflater inflater=fragment.getActivity().getLayoutInflater();

        if(convertView == null){

            convertView=inflater.inflate(R.layout.log_list_item, null);

            textViewName=(TextView) convertView.findViewById(R.id.textViewName);
            textViewData=(TextView) convertView.findViewById(R.id.textViewData);
            textViewDate=(TextView) convertView.findViewById(R.id.textViewDate);

        }

        HashMap<String, String> map=list.get(position);
        textViewName.setText(map.get("Name"));
        textViewData.setText(map.get("Data"));
        textViewDate.setText(map.get("Date"));

        return convertView;
    }




}
