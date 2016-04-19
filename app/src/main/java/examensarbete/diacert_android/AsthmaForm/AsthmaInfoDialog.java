package examensarbete.diacert_android.AsthmaForm;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import examensarbete.diacert_android.R;

/**
 * Created by backevik on 16-04-15.
 */
public class AsthmaInfoDialog extends Dialog {

    public AsthmaInfoDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.asthma_info_dialog);

        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);

        Button asthmaButton = (Button) findViewById(R.id.asthmaDialogButton);
        asthmaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hide();
                cancel();
            }
        });

    }
}
