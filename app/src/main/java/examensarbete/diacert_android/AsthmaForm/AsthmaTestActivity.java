package examensarbete.diacert_android.AsthmaForm;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import examensarbete.diacert_android.OverviewFragment;
import examensarbete.diacert_android.R;

/**
 * Created by backevik on 16-04-14.
 */
public class AsthmaTestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.app_bar_form);

        AsthmaTestFragment asthmaFrontPage = new AsthmaTestFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.form_rel_layout, asthmaFrontPage, "Asthma frontpage fragment Active");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }
}
