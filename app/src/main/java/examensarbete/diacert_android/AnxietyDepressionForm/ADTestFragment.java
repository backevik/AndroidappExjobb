package examensarbete.diacert_android.AnxietyDepressionForm;

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

import examensarbete.diacert_android.AsthmaForm.AsthmaFormQ1Fragment;
import examensarbete.diacert_android.AsthmaForm.AsthmaInfoDialog;
import examensarbete.diacert_android.R;

/**
 * Created by backevik on 16-04-21.
 */
public class ADTestFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.ad_form_view, container, false);

        View pLayout = (LinearLayout) getActivity().findViewById(R.id.asthmaProgressLayout);
        ProgressBar progressBar = (ProgressBar) pLayout.findViewById(R.id.asthmaProgressBar);
        progressBar.setVisibility(View.INVISIBLE);

        View bLayout = (LinearLayout) getActivity().findViewById(R.id.buttonLayout);
        Button nextBtn = (Button) bLayout.findViewById(R.id.nextbtn);
        Button backBtn = (Button) bLayout.findViewById(R.id.backbtn);

        nextBtn.setVisibility(View.INVISIBLE);
        backBtn.setVisibility(View.INVISIBLE);

        final Button infoButton = (Button) v.findViewById(R.id.adInfoButton);
        infoButton.setEnabled(true);
        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ADInfoDialog aid = new ADInfoDialog(getActivity());
                aid.getWindow().getAttributes().windowAnimations = R.style.InfoDialogAnimation;
                aid.show();
            }
        });

        final Button startButton = (Button) v.findViewById(R.id.adStartButton);
        startButton.setEnabled(true);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startButton.setEnabled(false);
                infoButton.setEnabled(false);
                ADFormQ1Fragment q1 = new ADFormQ1Fragment();
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
