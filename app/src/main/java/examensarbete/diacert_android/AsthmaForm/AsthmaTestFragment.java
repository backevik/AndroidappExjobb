package examensarbete.diacert_android.AsthmaForm;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import examensarbete.diacert_android.R;

/**
 * Created by backevik on 16-04-16.
 */
public class AsthmaTestFragment extends Fragment{
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment

            View v = inflater.inflate(R.layout.asthma_form_view, container, false);

            View pLayout = (LinearLayout) getActivity().findViewById(R.id.asthmaProgressLayout);
            ProgressBar progressBar = (ProgressBar) pLayout.findViewById(R.id.asthmaProgressBar);
            progressBar.setVisibility(View.INVISIBLE);

            Button infoButton = (Button) v.findViewById(R.id.asthmaInfoButton);
            infoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AsthmaInfoDialog aid = new AsthmaInfoDialog(getActivity());
                    aid.getWindow().getAttributes().windowAnimations = R.style.InfoDialogAnimation;
                    aid.show();
                }
            });

            Button startButton = (Button) v.findViewById(R.id.asthmaStartButton);
            startButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AsthmaFormQ1Fragment q1 = new AsthmaFormQ1Fragment();
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.setCustomAnimations(R.animator.fade_in_forms, R.animator.fade_out_forms);
                    fragmentTransaction.add(R.id.form_rel_layout, q1, "Question 1 fragment Active");
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            });

            return v;
        }
}
