package examensarbete.diacert_android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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

/**
 * Created by backevik on 16-04-07.
 */
public class ConnectionActivity  extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_connection);

        //Connect view widgets
        final EditText connectText = (EditText) findViewById(R.id.connectEditText);
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
                            Intent intent = new Intent(ConnectionActivity.this, MainActivity.class);
                            ConnectionActivity.this.startActivity(intent);
                            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
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
}
