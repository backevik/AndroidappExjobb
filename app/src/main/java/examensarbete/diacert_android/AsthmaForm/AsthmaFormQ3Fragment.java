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
public class AsthmaFormQ3Fragment extends Fragment {
    private Bundle bundle;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.astma_form_view_q3, container, false);

        View pLayout = (LinearLayout) getActivity().findViewById(R.id.asthmaProgressLayout);
        ProgressBar progressBar = (ProgressBar) pLayout.findViewById(R.id.asthmaProgressBar);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setProgress(60);

        View bLayout = (LinearLayout) getActivity().findViewById(R.id.buttonLayout);
        Button nextBtn = (Button) bLayout.findViewById(R.id.nextbtn);
        Button backBtn = (Button) bLayout.findViewById(R.id.backbtn);

        bundle = this.getArguments();

        final CheckBox qa31 = (CheckBox) v.findViewById(R.id.qa31);
        final CheckBox qa32 = (CheckBox) v.findViewById(R.id.qa32);
        final CheckBox qa33 = (CheckBox) v.findViewById(R.id.qa33);
        final CheckBox qa34 = (CheckBox) v.findViewById(R.id.qa34);
        final CheckBox qa35 = (CheckBox) v.findViewById(R.id.qa35);

        if(bundle != null){
            if(bundle.getInt("q3") == 1){qa31.toggle();}
            else if(bundle.getInt("q3") == 2){qa32.toggle();}
            else if(bundle.getInt("q3") == 3){qa33.toggle();}
            else if(bundle.getInt("q3") == 4){qa34.toggle();}
            else if(bundle.getInt("q3") == 5){qa35.toggle();}
        }

        qa31.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(qa31.isChecked()){
                    if(qa32.isChecked()){qa32.toggle();}
                    if(qa33.isChecked()){qa33.toggle();}
                    if(qa34.isChecked()){qa34.toggle();}
                    if(qa35.isChecked()){qa35.toggle();}
                }
            }
        });
        qa32.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(qa32.isChecked()){
                    if(qa31.isChecked()){qa31.toggle();}
                    if(qa33.isChecked()){qa33.toggle();}
                    if(qa34.isChecked()){qa34.toggle();}
                    if(qa35.isChecked()){qa35.toggle();}
                }
            }
        });
        qa33.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(qa33.isChecked()){
                    if(qa32.isChecked()){qa32.toggle();}
                    if(qa31.isChecked()){qa31.toggle();}
                    if(qa34.isChecked()){qa34.toggle();}
                    if(qa35.isChecked()){qa35.toggle();}
                }
            }
        });
        qa34.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(qa34.isChecked()){
                    if(qa32.isChecked()){qa32.toggle();}
                    if(qa33.isChecked()){qa33.toggle();}
                    if(qa31.isChecked()){qa31.toggle();}
                    if(qa35.isChecked()){qa35.toggle();}
                }
            }
        });
        qa35.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(qa35.isChecked()){
                    if(qa32.isChecked()){qa32.toggle();}
                    if(qa33.isChecked()){qa33.toggle();}
                    if(qa34.isChecked()){qa34.toggle();}
                    if(qa31.isChecked()){qa31.toggle();}
                }
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsthmaFormQ2Fragment asthmaFormQ2Fragment = new AsthmaFormQ2Fragment();
                asthmaFormQ2Fragment.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.animator.fade_in_from_left, R.animator.fade_out_to_right);
                fragmentTransaction.add(R.id.form_rel_layout, asthmaFormQ2Fragment, "Question 2 fragment Active");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!qa31.isChecked() && !qa32.isChecked() && !qa33.isChecked() && !qa34.isChecked() && !qa35.isChecked()){
                    showErrorOnNextDialog();
                }else{
                    if(qa31.isChecked()){bundle.putInt("q3",1); bundle.putString("qt3",getString(R.string.qa31));}
                    if(qa32.isChecked()){bundle.putInt("q3",2); bundle.putString("qt3",getString(R.string.qa32));}
                    if(qa33.isChecked()){bundle.putInt("q3",3); bundle.putString("qt3",getString(R.string.qa33));}
                    if(qa34.isChecked()){bundle.putInt("q3",4); bundle.putString("qt3",getString(R.string.qa34));}
                    if(qa35.isChecked()){bundle.putInt("q3",5); bundle.putString("qt3",getString(R.string.qa35));}

                    AsthmaFormQ4Fragment asthmaFormQ4Fragment = new AsthmaFormQ4Fragment();
                    asthmaFormQ4Fragment.setArguments(bundle);
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.setCustomAnimations(R.animator.fade_in_forms, R.animator.fade_out_forms);
                    fragmentTransaction.add(R.id.form_rel_layout, asthmaFormQ4Fragment, "Question 4 fragment Active");
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
