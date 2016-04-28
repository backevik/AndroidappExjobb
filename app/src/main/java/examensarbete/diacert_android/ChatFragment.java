package examensarbete.diacert_android;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.github.mikephil.charting.charts.LineChart;

/**
 * Created by Martin on 2016-04-28.
 */
public class ChatFragment extends Fragment {
    private final String TAG ="CHAT";

    private Button sendBtn;
    private EditText inputMsg;


    private View charView;
    private String messageText;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        charView = inflater.inflate(R.layout.chat_layout, container, false);

        inputMsg = (EditText) charView.findViewById(R.id.inputMsg);

        sendBtn = (Button) charView.findViewById(R.id.btnSend);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"sending");
                //make a method that handles this
                messageText = inputMsg.getText().toString();
                //call send message
            }
        });

        return charView;
    }





    /*
    Needs a method that collects all messages from REST api and puts them in an array of messages.
    values that needs to be stored in api is sender text and some sort of sorting maby time stamp.
     */
}
