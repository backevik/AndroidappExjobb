package examensarbete.diacert_android.AnxietyDepressionForm;

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
 * Created by backevik on 16-04-25.
 */
public class ADFormQ11Fragment extends Fragment {
    private Bundle bundle;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.ad_form_view_q11, container, false);

        View pLayout = (LinearLayout) getActivity().findViewById(R.id.asthmaProgressLayout);
        ProgressBar progressBar = (ProgressBar) pLayout.findViewById(R.id.asthmaProgressBar);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setProgress(77);

        View bLayout = (LinearLayout) getActivity().findViewById(R.id.buttonLayout);
        Button nextBtn = (Button) bLayout.findViewById(R.id.nextbtn);
        Button backBtn = (Button) bLayout.findViewById(R.id.backbtn);

        bundle = this.getArguments();

        final CheckBox qa11 = (CheckBox) v.findViewById(R.id.qa111);
        final CheckBox qa12 = (CheckBox) v.findViewById(R.id.qa112);
        final CheckBox qa13 = (CheckBox) v.findViewById(R.id.qa113);
        final CheckBox qa14 = (CheckBox) v.findViewById(R.id.qa114);

        if(bundle != null){
            if(bundle.getInt("q11") == 4){qa11.toggle();}
            else if(bundle.getInt("q11") == 3){qa12.toggle();}
            else if(bundle.getInt("q11") == 2){qa13.toggle();}
            else if(bundle.getInt("q11") == 1){qa14.toggle();}
        }

        qa11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(qa11.isChecked()){
                    if(qa12.isChecked()){qa12.toggle();}
                    if(qa13.isChecked()){qa13.toggle();}
                    if(qa14.isChecked()){qa14.toggle();}
                }
            }
        });
        qa12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(qa12.isChecked()){
                    if(qa11.isChecked()){qa11.toggle();}
                    if(qa13.isChecked()){qa13.toggle();}
                    if(qa14.isChecked()){qa14.toggle();}
                }
            }
        });
        qa13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(qa13.isChecked()){
                    if(qa12.isChecked()){qa12.toggle();}
                    if(qa11.isChecked()){qa11.toggle();}
                    if(qa14.isChecked()){qa14.toggle();}
                }
            }
        });
        qa14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(qa14.isChecked()){
                    if(qa12.isChecked()){qa12.toggle();}
                    if(qa13.isChecked()){qa13.toggle();}
                    if(qa11.isChecked()){qa11.toggle();}
                }
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ADFormQ10Fragment adFormQ10Fragment = new ADFormQ10Fragment();
                adFormQ10Fragment.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.animator.fade_in_from_left, R.animator.fade_out_to_right);
                fragmentTransaction.add(R.id.form_rel_layout, adFormQ10Fragment, "Question 10 fragment Active");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!qa11.isChecked() && !qa12.isChecked() && !qa13.isChecked() && !qa14.isChecked()){
                    showErrorOnNextDialog();
                }else{
                    if(qa11.isChecked()){bundle.putInt("q11",4); bundle.putString("qt11","Väldigt ofta");}
                    if(qa12.isChecked()){bundle.putInt("q11",3); bundle.putString("qt11","Ganska ofta");}
                    if(qa13.isChecked()){bundle.putInt("q11",2); bundle.putString("qt11","Sällan");}
                    if(qa14.isChecked()){bundle.putInt("q11",1); bundle.putString("qt11","Aldrig");}

                    ADFormQ12Fragment adFormQ12Fragment = new ADFormQ12Fragment();
                    adFormQ12Fragment.setArguments(bundle);
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.setCustomAnimations(R.animator.fade_in_forms, R.animator.fade_out_forms);
                    fragmentTransaction.add(R.id.form_rel_layout, adFormQ12Fragment, "Question 12 fragment Active");
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
