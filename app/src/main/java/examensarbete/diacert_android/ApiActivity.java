package examensarbete.diacert_android;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

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

    }
