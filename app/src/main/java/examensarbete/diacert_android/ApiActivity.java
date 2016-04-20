package examensarbete.diacert_android;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.concurrent.ExecutionException;

import examensarbete.diacert_android.API.testAPI;
import examensarbete.diacert_android.Database.KeyDBHandler;

/**
 * Created by backevik on 16-04-07.
 */
public class ApiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_api);

        Button developButton = (Button) findViewById(R.id.developButton);
        Button prototypeButton = (Button) findViewById(R.id.prototypeButton);

        //|-------------------------- LISTENERS --------------------------|

        developButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    public void run() {
                        Intent intent = new Intent(ApiActivity.this, ConnectionActivity.class);
                        ApiActivity.this.startActivity(intent);
                        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                    }
                });
            }
        });

        prototypeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    public void run() {
                        Intent intent = new Intent(ApiActivity.this, ConnectionActivity.class);
                        Bundle mBundle = new Bundle();
                        mBundle.putString("API", "MOCK");
                        intent.putExtras(mBundle);
                        ApiActivity.this.startActivity(intent);
                        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                    }
                });
            }
        });
    }

    /**
     * Created by backevik on 16-04-07.
     */
    public static class ConnectionActivity  extends AppCompatActivity {
        private String API = "";
        private String CODE = "";
        private String jsonString = "";
        private EditText connectText;
        private KeyDBHandler keyDBHandler;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            keyDBHandler = new KeyDBHandler(this,null);
            if(!keyDBHandler.getData().isEmpty()){
                Intent intent = new Intent(ConnectionActivity.this, MainActivity.class);
                ConnectionActivity.this.startActivity(intent);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }
            setContentView(R.layout.app_bar_connection);

                API = getIntent().getExtras().getString("API");

            //Connect view widgets
            connectText = (EditText) findViewById(R.id.connectEditText);
            connectText.setTransformationMethod(new NumericKeyBoardTransformationMethod()); // Using overriding method

            final Button connectButton = (Button) findViewById(R.id.connectButton);
            connectButton.setEnabled(false);
            connectButton.setClickable(false);

            RelativeLayout relativeConnectLayout = (RelativeLayout) findViewById(R.id.relativeConnectLayout);

            // |--------------------------- EVENT LISTENERS ---------------------------|

            connectText.addTextChangedListener(new TextWatcher() {

                @Override
                public void afterTextChanged(Editable s) {
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (connectText.getText().length() < 6) {
                        connectButton.setEnabled(false);
                        connectButton.setClickable(false);
                        Log.d("Less than six", "Disabled button!");
                    } else if (connectText.getText().length() == 6) {
                        connectButton.setEnabled(true);
                        connectButton.setClickable(true);
                        Log.d("Equals to six", "Enabled button!");
                    }
                }
            });

            relativeConnectLayout.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            });

            connectText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_DONE && connectButton.isEnabled()) {
                        connectButton.performClick();
                        return true;
                    }
                    return false;
                }
            });

            connectButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Handler handler = new Handler(Looper.getMainLooper());
                        handler.post(new Runnable() {
                            public void run() {
                                switch (API){
                                    case "MOCK":
                                        testAPI testAPI = new testAPI();
                                        try {
                                            jsonString = testAPI.execute("pair",connectText.getText().toString()).get();
                                            if(jsonString == null){
                                                showErrorOnConnectDialog("Kunde ej ansluta.");
                                            }else if(!jsonString.isEmpty()){
                                                JsonElement json = new JsonParser().parse(jsonString);
                                                JsonObject jobject = json.getAsJsonObject();
                                                CODE = jobject.get("key").toString();
                                                keyDBHandler.addData(CODE);
                                            }
                                            Log.d("Testing API", "API resp is: "+keyDBHandler.getData());
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        } catch (ExecutionException e) {
                                            e.printStackTrace();
                                        }
                                        break;
                                    case "DEV":

                                        break;
                                }
                                if(CODE == null){
                                    showErrorOnConnectDialog("Koden matchade inte! Prova igen.");
                                }else if(jsonString != null){
                                    Intent intent = new Intent(ConnectionActivity.this, MainActivity.class);
                                    ConnectionActivity.this.startActivity(intent);
                                    overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                                }
                            }
                        });
                }
            });
        }

        @Override
        public void onBackPressed() {
            super.onBackPressed();
        }

        /*
        * Overriding input type password for soft keyboard, showing the input.
        */
        private class NumericKeyBoardTransformationMethod extends PasswordTransformationMethod {
            @Override
            public CharSequence getTransformation(CharSequence source, View view) {
                return source;
            }
        }

        private void showErrorOnConnectDialog(String msg){
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setMessage(msg);
            builder1.setCancelable(true);
            builder1.setTitle("Fel!");
            builder1.setIcon(R.drawable.ic_popup_warning);
            builder1.setPositiveButton(
                    "Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();
        }
    }
}
