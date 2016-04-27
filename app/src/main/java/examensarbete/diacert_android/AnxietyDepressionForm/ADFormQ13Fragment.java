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
public class ADFormQ13Fragment extends Fragment {
    private Bundle bundle;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.ad_form_view_q13, container, false);

        View pLayout = (LinearLayout) getActivity().findViewById(R.id.asthmaProgressLayout);
        ProgressBar progressBar = (ProgressBar) pLayout.findViewById(R.id.asthmaProgressBar);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setProgress(91);

        View bLayout = (LinearLayout) getActivity().findViewById(R.id.buttonLayout);
        Button nextBtn = (Button) bLayout.findViewById(R.id.nextbtn);
        Button backBtn = (Button) bLayout.findViewById(R.id.backbtn);

        bundle = this.getArguments();

        final CheckBox qa11 = (CheckBox) v.findViewById(R.id.qa131);
        final CheckBox qa12 = (CheckBox) v.findViewById(R.id.qa132);
        final CheckBox qa13 = (CheckBox) v.findViewById(R.id.qa133);
        final CheckBox qa14 = (CheckBox) v.findViewById(R.id.qa134);

        if(bundle != null){
            if(bundle.getInt("q13") == 1){qa11.toggle();}
            else if(bundle.getInt("q13") == 2){qa12.toggle();}
            else if(bundle.getInt("q13") == 3){qa13.toggle();}
            else if(bundle.getInt("q13") == 4){qa14.toggle();}
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
                ADFormQ12Fragment adFormQ12Fragment = new ADFormQ12Fragment();
                adFormQ12Fragment.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.animator.fade_in_from_left, R.animator.fade_out_to_right);
                fragmentTransaction.add(R.id.form_rel_layout, adFormQ12Fragment, "Question 12 fragment Active");
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
                    if(qa11.isChecked()){bundle.putInt("q13",1); bundle.putString("qt13","Absolut");}
                    if(qa12.isChecked()){bundle.putInt("q13",2); bundle.putString("qt13","Vanligtvis");}
                    if(qa13.isChecked()){bundle.putInt("q13",3); bundle.putString("qt13","Sällan");}
                    if(qa14.isChecked()){bundle.putInt("q13",4); bundle.putString("qt13","Aldrig");}

                    ADFormQ14Fragment adFormQ14Fragment = new ADFormQ14Fragment();
                    adFormQ14Fragment.setArguments(bundle);
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.setCustomAnimations(R.animator.fade_in_forms, R.animator.fade_out_forms);
                    fragmentTransaction.add(R.id.form_rel_layout, adFormQ14Fragment, "Question 14 fragment Active");
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
