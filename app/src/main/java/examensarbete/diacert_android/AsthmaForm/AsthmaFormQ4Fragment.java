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
public class AsthmaFormQ4Fragment extends Fragment {
    private Bundle bundle;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.astma_form_view_q4, container, false);

        View pLayout = (LinearLayout) getActivity().findViewById(R.id.asthmaProgressLayout);
        ProgressBar progressBar = (ProgressBar) pLayout.findViewById(R.id.asthmaProgressBar);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setProgress(80);

        View bLayout = (LinearLayout) getActivity().findViewById(R.id.buttonLayout);
        Button nextBtn = (Button) bLayout.findViewById(R.id.nextbtn);
        Button backBtn = (Button) bLayout.findViewById(R.id.backbtn);

        bundle = this.getArguments();

        final CheckBox qa41 = (CheckBox) v.findViewById(R.id.qa41);
        final CheckBox qa42 = (CheckBox) v.findViewById(R.id.qa42);
        final CheckBox qa43 = (CheckBox) v.findViewById(R.id.qa43);
        final CheckBox qa44 = (CheckBox) v.findViewById(R.id.qa44);
        final CheckBox qa45 = (CheckBox) v.findViewById(R.id.qa45);

        if(bundle != null){
            if(bundle.getInt("q4") == 1){qa41.toggle();}
            else if(bundle.getInt("q4") == 2){qa42.toggle();}
            else if(bundle.getInt("q4") == 3){qa43.toggle();}
            else if(bundle.getInt("q4") == 4){qa44.toggle();}
            else if(bundle.getInt("q4") == 5){qa45.toggle();}
        }

        qa41.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(qa41.isChecked()){
                    if(qa42.isChecked()){qa42.toggle();}
                    if(qa43.isChecked()){qa43.toggle();}
                    if(qa44.isChecked()){qa44.toggle();}
                    if(qa45.isChecked()){qa45.toggle();}
                }
            }
        });
        qa42.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(qa42.isChecked()){
                    if(qa41.isChecked()){qa41.toggle();}
                    if(qa43.isChecked()){qa43.toggle();}
                    if(qa44.isChecked()){qa44.toggle();}
                    if(qa45.isChecked()){qa45.toggle();}
                }
            }
        });
        qa43.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(qa43.isChecked()){
                    if(qa42.isChecked()){qa42.toggle();}
                    if(qa41.isChecked()){qa41.toggle();}
                    if(qa44.isChecked()){qa44.toggle();}
                    if(qa45.isChecked()){qa45.toggle();}
                }
            }
        });
        qa44.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(qa44.isChecked()){
                    if(qa42.isChecked()){qa42.toggle();}
                    if(qa43.isChecked()){qa43.toggle();}
                    if(qa41.isChecked()){qa41.toggle();}
                    if(qa45.isChecked()){qa45.toggle();}
                }
            }
        });
        qa45.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(qa45.isChecked()){
                    if(qa42.isChecked()){qa42.toggle();}
                    if(qa43.isChecked()){qa43.toggle();}
                    if(qa44.isChecked()){qa44.toggle();}
                    if(qa41.isChecked()){qa41.toggle();}
                }
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsthmaFormQ3Fragment asthmaFormQ3Fragment = new AsthmaFormQ3Fragment();
                asthmaFormQ3Fragment.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.animator.fade_in_from_left, R.animator.fade_out_to_right);
                fragmentTransaction.add(R.id.form_rel_layout, asthmaFormQ3Fragment, "Question 3 fragment Active");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!qa41.isChecked() && !qa42.isChecked() && !qa43.isChecked() && !qa44.isChecked() && !qa45.isChecked()){
                    showErrorOnNextDialog();
                }else{
                    if(qa41.isChecked()){bundle.putInt("q4",1);}
                    if(qa42.isChecked()){bundle.putInt("q4",2);}
                    if(qa43.isChecked()){bundle.putInt("q4",3);}
                    if(qa44.isChecked()){bundle.putInt("q4",4);}
                    if(qa45.isChecked()){bundle.putInt("q4",5);}

                    AsthmaFormQ5Fragment asthmaFormQ5Fragment = new AsthmaFormQ5Fragment();
                    asthmaFormQ5Fragment.setArguments(bundle);
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.setCustomAnimations(R.animator.fade_in_forms, R.animator.fade_out_forms);
                    fragmentTransaction.add(R.id.form_rel_layout, asthmaFormQ5Fragment, "Question 5 fragment Active");
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            }
        });

        return v;
    }

    private void showErrorOnNextDialog(){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
        builder1.setMessage("Du måste välja ett alternativ innan du kan gå vidare!");
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
