package examensarbete.diacert_android;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.wearable.view.BoxInsetLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.github.mikephil.charting.charts.LineChart;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import examensarbete.diacert_android.API.TestAPI;
import examensarbete.diacert_android.Database.KeyDBHandler;
import examensarbete.diacert_android.Messages.Message;
import examensarbete.diacert_android.Messages.MessagesListAdapter;

/**
 * Created by Martin on 2016-04-28.
 */
public class ChatFragment extends Fragment {
    private final String TAG ="CHAT";

    private Button sendBtn;
    private EditText inputMsg;
    private ListView listView;
    private ArrayList<Message> msgList;
    private MessagesListAdapter messagesListAdapter;


    private View charView;
    private String messageText;

    @Override
    public void onStop() {
        super.onStop();
        hideKeyboard(getActivity());
    }
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        charView = inflater.inflate(R.layout.chat_layout, container, false);

        inputMsg = (EditText) charView.findViewById(R.id.inputMsg);

        listView = (ListView) charView.findViewById(R.id.list_view_messages);

        sendBtn = (Button) charView.findViewById(R.id.btnSend);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(inputMsg.getText().length()>0){
                    sendMsg(inputMsg.getText().toString());
                }
                hideKeyboard(getActivity());
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG,"listener works");
                hideKeyboard(getActivity());
            }
        });


        msgList = new ArrayList<Message>();

        getMessages();

        //addDemoMsg();
        messagesListAdapter = new MessagesListAdapter(getActivity(),msgList);

        listView.setAdapter(messagesListAdapter);

        return charView;
    }

    private void sendMsg(String msg) {
        KeyDBHandler keyDBHandler = new KeyDBHandler(getActivity(), null);
        String resp = "";


        try {//finish parameters in POST request.
            //order is table name, your id, message in text, time stamp, and bool isSelf
            resp = new TestAPI().execute("sendMsg", keyDBHandler.getData(), msg, Long.toString(System.currentTimeMillis()), "true").get();
            msgList.add(new Message(msg,true));

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        inputMsg.setText("");
        listView.setAdapter(messagesListAdapter);

    }

    private void getMessages(){

        KeyDBHandler keyDBHandler = new KeyDBHandler(getActivity(), null);
        String msgResp = "";


        try {//finish parameters in POST request.
            //order is table name, your id, message in text, time stamp, and bool isSelf
            msgResp = new TestAPI().execute("getMsg", keyDBHandler.getData()).get();
            Log.d(TAG,keyDBHandler.getData());
            Log.d(TAG,msgResp);
            if(msgResp ==null){
                Log.d(TAG,"Kunde ej hämta meddelanden.");
            }else if (!msgResp.isEmpty()){
                JsonElement json = new JsonParser().parse(msgResp);
                JsonArray jArray = json.getAsJsonArray();
                for(int i = 0; i < jArray.size(); i++){
                    JsonObject jobject = jArray.get(i).getAsJsonObject();
                    String responseText = jobject.get("text").toString();
                    String parsedMsg= responseText.replace("\"","");
                    String isSelf = jobject.get("isSelf").toString();
                    Message msg = new Message(parsedMsg,true);
                    if(isSelf.equals("false")){
                        msg.setSelf(false);
                    }
                    msgList.add(msg);
                }

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    private void sendMsgAsCaregiver(String msg){
        KeyDBHandler keyDBHandler = new KeyDBHandler(getActivity(), null);
        String resp;

        try {//finish parameters in POST request.
            //order is table name, your id, message in text, time stamp, and bool isSelf
            resp = new TestAPI().execute("sendMsg", keyDBHandler.getData(), msg, Long.toString(System.currentTimeMillis()), "false").get();
            msgList.add(new Message(msg,false));

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
    private void addDemoMsg(){
        msgList.add(new Message("Hej hur ser mina prover ut?", true));
        msgList.add(new Message("Hej hur ser mina prover ut?", true));
        msgList.add(new Message("Hej hur ser mina prover ut?", true));
        msgList.add(new Message("Hej hur ser mina prover ut?", true));
        msgList.add(new Message("Hej hur ser mina prover ut?", true));
        msgList.add(new Message("Hej hur ser mina prover ut?", true));
        msgList.add(new Message("Hej hur ser mina prover ut?", true));
        msgList.add(new Message("Har tyvärr inte fått svar från labbet. Men återkommer så fort jag ver något.", false));
        msgList.add(new Message("Okej, kan du svara på hur lång tid det kan ta?", true));
        msgList.add(new Message("Det kan jag tyvärr inte. Men lovar att höra av mig så fort jag kan", false));
    }

    public static void hideKeyboard(Context ctx) {
        InputMethodManager inputManager = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);

        View v = ((Activity) ctx).getCurrentFocus();
        if (v == null){
            Log.d("CHAT","No context cant remove keayboard");
            return;
        }
        inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }


}
