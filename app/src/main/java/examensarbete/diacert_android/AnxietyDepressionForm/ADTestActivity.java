package examensarbete.diacert_android.AnxietyDepressionForm;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import examensarbete.diacert_android.AsthmaForm.AsthmaTestFragment;
import examensarbete.diacert_android.R;

/**
 * Created by backevik on 16-04-21.
 */
public class ADTestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.app_bar_form);

        ADTestFragment adTestFragment = new ADTestFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.form_rel_layout, adTestFragment, "AD frontpage fragment Active");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }
}
