package examensarbete.diacert_android.AsthmaForm;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import examensarbete.diacert_android.R;

/**
 * Created by backevik on 16-04-17.
 */
public class AsthmaFormQ5Fragment extends Fragment{
    private Bundle bundle;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.astma_form_view_q5, container, false);

        View pLayout = (LinearLayout) getActivity().findViewById(R.id.asthmaProgressLayout);
        ProgressBar progressBar = (ProgressBar) pLayout.findViewById(R.id.asthmaProgressBar);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setProgress(100);

        View bLayout = (LinearLayout) getActivity().findViewById(R.id.buttonLayout);
        Button nextBtn = (Button) bLayout.findViewById(R.id.nextbtn);
        Button backBtn = (Button) bLayout.findViewById(R.id.backbtn);

        bundle = this.getArguments();

        final CheckBox qa51 = (CheckBox) v.findViewById(R.id.qa51);
        final CheckBox qa52 = (CheckBox) v.findViewById(R.id.qa52);
        final CheckBox qa53 = (CheckBox) v.findViewById(R.id.qa53);
        final CheckBox qa54 = (CheckBox) v.findViewById(R.id.qa54);
        final CheckBox qa55 = (CheckBox) v.findViewById(R.id.qa55);

        if(bundle != null){
            if(bundle.getInt("q5") == 1){qa51.toggle();}
            else if(bundle.getInt("q5") == 2){qa52.toggle();}
            else if(bundle.getInt("q5") == 3){qa53.toggle();}
            else if(bundle.getInt("q5") == 4){qa54.toggle();}
            else if(bundle.getInt("q5") == 5){qa55.toggle();}
        }

        qa51.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(qa51.isChecked()){
                    if(qa52.isChecked()){qa52.toggle();}
                    if(qa53.isChecked()){qa53.toggle();}
                    if(qa54.isChecked()){qa54.toggle();}
                    if(qa55.isChecked()){qa55.toggle();}
                }
            }
        });
        qa52.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(qa52.isChecked()){
                    if(qa51.isChecked()){qa51.toggle();}
                    if(qa53.isChecked()){qa53.toggle();}
                    if(qa54.isChecked()){qa54.toggle();}
                    if(qa55.isChecked()){qa55.toggle();}
                }
            }
        });
        qa53.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(qa53.isChecked()){
                    if(qa52.isChecked()){qa52.toggle();}
                    if(qa51.isChecked()){qa51.toggle();}
                    if(qa54.isChecked()){qa54.toggle();}
                    if(qa55.isChecked()){qa55.toggle();}
                }
            }
        });
        qa54.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(qa54.isChecked()){
                    if(qa52.isChecked()){qa52.toggle();}
                    if(qa53.isChecked()){qa53.toggle();}
                    if(qa51.isChecked()){qa51.toggle();}
                    if(qa55.isChecked()){qa55.toggle();}
                }
            }
        });
        qa55.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(qa55.isChecked()){
                    if(qa52.isChecked()){qa52.toggle();}
                    if(qa53.isChecked()){qa53.toggle();}
                    if(qa54.isChecked()){qa54.toggle();}
                    if(qa51.isChecked()){qa51.toggle();}
                }
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsthmaFormQ4Fragment asthmaFormQ4Fragment = new AsthmaFormQ4Fragment();
                asthmaFormQ4Fragment.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.animator.fade_in_from_left, R.animator.fade_out_to_right);
                fragmentTransaction.add(R.id.form_rel_layout, asthmaFormQ4Fragment, "Question 4 fragment Active");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!qa51.isChecked() && !qa52.isChecked() && !qa53.isChecked() && !qa54.isChecked() && !qa55.isChecked()){
                    showErrorOnNextDialog("Du måste välja ett alternativ innan du kan gå vidare!","Fel!");
                }else{
                    if(qa51.isChecked()){bundle.putInt("q5",1); bundle.putString("qt5",getString(R.string.qa51));}
                    if(qa52.isChecked()){bundle.putInt("q5",2); bundle.putString("qt5",getString(R.string.qa52));}
                    if(qa53.isChecked()){bundle.putInt("q5",3); bundle.putString("qt5",getString(R.string.qa53));}
                    if(qa54.isChecked()){bundle.putInt("q5",4); bundle.putString("qt5",getString(R.string.qa54));}
                    if(qa55.isChecked()){bundle.putInt("q5",5); bundle.putString("qt5",getString(R.string.qa55));}

                    AsthmaFormResultFragment asthmaFormResultFragment = new AsthmaFormResultFragment();
                    asthmaFormResultFragment.setArguments(bundle);
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.setCustomAnimations(R.animator.fade_in_forms, R.animator.fade_out_forms);
                    fragmentTransaction.add(R.id.form_rel_layout, asthmaFormResultFragment, "Result fragment Active");
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            }
        });

        return v;
    }

    private void showErrorOnNextDialog(String msg, String title){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
        builder1.setMessage(msg);
        builder1.setCancelable(true);
        builder1.setTitle(title);
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
