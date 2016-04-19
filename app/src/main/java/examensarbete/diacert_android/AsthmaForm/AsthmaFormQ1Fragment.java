package examensarbete.diacert_android.AsthmaForm;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import examensarbete.diacert_android.FormsFragment;
import examensarbete.diacert_android.R;

/**
 * Created by backevik on 16-04-16.
 */
public class AsthmaFormQ1Fragment extends Fragment {
    private Bundle bundle;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.astma_form_view_q1, container, false);
        View pLayout = (LinearLayout) getActivity().findViewById(R.id.asthmaProgressLayout);
        ProgressBar progressBar = (ProgressBar) pLayout.findViewById(R.id.asthmaProgressBar);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setProgress(20);

        View bLayout = (LinearLayout) getActivity().findViewById(R.id.buttonLayout);
        Button nextBtn = (Button) bLayout.findViewById(R.id.nextbtn);
        Button backBtn = (Button) bLayout.findViewById(R.id.backbtn);

        nextBtn.setVisibility(View.VISIBLE);
        backBtn.setVisibility(View.VISIBLE);

        final CheckBox qa11 = (CheckBox) v.findViewById(R.id.qa11);
        final CheckBox qa12 = (CheckBox) v.findViewById(R.id.qa12);
        final CheckBox qa13 = (CheckBox) v.findViewById(R.id.qa13);
        final CheckBox qa14 = (CheckBox) v.findViewById(R.id.qa14);
        final CheckBox qa15 = (CheckBox) v.findViewById(R.id.qa15);

        bundle = this.getArguments();
        if(bundle == null){
            bundle = new Bundle();
        }else{
            if(bundle.getInt("q1") == 1){qa11.toggle();}
            else if(bundle.getInt("q1") == 2){qa12.toggle();}
            else if(bundle.getInt("q1") == 3){qa13.toggle();}
            else if(bundle.getInt("q1") == 4){qa14.toggle();}
            else if(bundle.getInt("q1") == 5){qa15.toggle();}
        }

        qa11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(qa11.isChecked()){
                    if(qa12.isChecked()){qa12.toggle();}
                    if(qa13.isChecked()){qa13.toggle();}
                    if(qa14.isChecked()){qa14.toggle();}
                    if(qa15.isChecked()){qa15.toggle();}
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
                    if(qa15.isChecked()){qa15.toggle();}
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
                    if(qa15.isChecked()){qa15.toggle();}
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
                    if(qa15.isChecked()){qa15.toggle();}
                }
            }
        });
        qa15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(qa15.isChecked()){
                    if(qa12.isChecked()){qa12.toggle();}
                    if(qa13.isChecked()){qa13.toggle();}
                    if(qa14.isChecked()){qa14.toggle();}
                    if(qa11.isChecked()){qa11.toggle();}
                }
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsthmaTestFragment asthmaFrontPage = new AsthmaTestFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.animator.fade_in_from_left, R.animator.fade_out_to_right);
                fragmentTransaction.add(R.id.form_rel_layout, asthmaFrontPage, "Asthma frontpage fragment Active");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!qa11.isChecked() && !qa12.isChecked() && !qa13.isChecked() && !qa14.isChecked() && !qa15.isChecked()){
                    showErrorOnNextDialog();
                }else{
                    if(qa11.isChecked()){bundle.putInt("q1",1);}
                    if(qa12.isChecked()){bundle.putInt("q1",2);}
                    if(qa13.isChecked()){bundle.putInt("q1",3);}
                    if(qa14.isChecked()){bundle.putInt("q1",4);}
                    if(qa15.isChecked()){bundle.putInt("q1",5);}

                    AsthmaFormQ2Fragment asthmaFormQ2Fragment = new AsthmaFormQ2Fragment();
                    asthmaFormQ2Fragment.setArguments(bundle);
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.setCustomAnimations(R.animator.fade_in_forms, R.animator.fade_out_forms);
                    fragmentTransaction.add(R.id.form_rel_layout, asthmaFormQ2Fragment, "Question 2 fragment Active");
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
