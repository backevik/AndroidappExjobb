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
 * Created by backevik on 16-04-16.
 */
public class AsthmaFormQ2Fragment extends Fragment {
    private Bundle bundle;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.astma_form_view_q2, container, false);

        View pLayout = (LinearLayout) getActivity().findViewById(R.id.asthmaProgressLayout);
        ProgressBar progressBar = (ProgressBar) pLayout.findViewById(R.id.asthmaProgressBar);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setProgress(40);

        View bLayout = (LinearLayout) getActivity().findViewById(R.id.buttonLayout);
        Button nextBtn = (Button) bLayout.findViewById(R.id.nextbtn);
        Button backBtn = (Button) bLayout.findViewById(R.id.backbtn);

        bundle = this.getArguments();

        final CheckBox qa21 = (CheckBox) v.findViewById(R.id.qa21);
        final CheckBox qa22 = (CheckBox) v.findViewById(R.id.qa22);
        final CheckBox qa23 = (CheckBox) v.findViewById(R.id.qa23);
        final CheckBox qa24 = (CheckBox) v.findViewById(R.id.qa24);
        final CheckBox qa25 = (CheckBox) v.findViewById(R.id.qa25);

        if(bundle != null){
            if(bundle.getInt("q2") == 1){qa21.toggle();}
            else if(bundle.getInt("q2") == 2){qa22.toggle();}
            else if(bundle.getInt("q2") == 3){qa23.toggle();}
            else if(bundle.getInt("q2") == 4){qa24.toggle();}
            else if(bundle.getInt("q2") == 5){qa25.toggle();}
        }

        qa21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(qa21.isChecked()){
                    if(qa22.isChecked()){qa22.toggle();}
                    if(qa23.isChecked()){qa23.toggle();}
                    if(qa24.isChecked()){qa24.toggle();}
                    if(qa25.isChecked()){qa25.toggle();}
                }
            }
        });
        qa22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(qa22.isChecked()){
                    if(qa21.isChecked()){qa21.toggle();}
                    if(qa23.isChecked()){qa23.toggle();}
                    if(qa24.isChecked()){qa24.toggle();}
                    if(qa25.isChecked()){qa25.toggle();}
                }
            }
        });
        qa23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(qa23.isChecked()){
                    if(qa22.isChecked()){qa22.toggle();}
                    if(qa21.isChecked()){qa21.toggle();}
                    if(qa24.isChecked()){qa24.toggle();}
                    if(qa25.isChecked()){qa25.toggle();}
                }
            }
        });
        qa24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(qa24.isChecked()){
                    if(qa22.isChecked()){qa22.toggle();}
                    if(qa23.isChecked()){qa23.toggle();}
                    if(qa21.isChecked()){qa21.toggle();}
                    if(qa25.isChecked()){qa25.toggle();}
                }
            }
        });
        qa25.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(qa25.isChecked()){
                    if(qa22.isChecked()){qa22.toggle();}
                    if(qa23.isChecked()){qa23.toggle();}
                    if(qa24.isChecked()){qa24.toggle();}
                    if(qa21.isChecked()){qa21.toggle();}
                }
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsthmaFormQ1Fragment asthmaFormQ1Fragment = new AsthmaFormQ1Fragment();
                asthmaFormQ1Fragment.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.animator.fade_in_from_left, R.animator.fade_out_to_right);
                fragmentTransaction.add(R.id.form_rel_layout, asthmaFormQ1Fragment, "Question 1 fragment Active");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!qa21.isChecked() && !qa22.isChecked() && !qa23.isChecked() && !qa24.isChecked() && !qa25.isChecked()){
                    showErrorOnNextDialog();
                }else{
                    if(qa21.isChecked()){bundle.putInt("q2",1);}
                    if(qa22.isChecked()){bundle.putInt("q2",2);}
                    if(qa23.isChecked()){bundle.putInt("q2",3);}
                    if(qa24.isChecked()){bundle.putInt("q2",4);}
                    if(qa25.isChecked()){bundle.putInt("q2",5);}

                    AsthmaFormQ3Fragment asthmaFormQ3Fragment = new AsthmaFormQ3Fragment();
                    asthmaFormQ3Fragment.setArguments(bundle);
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.setCustomAnimations(R.animator.fade_in_forms, R.animator.fade_out_forms);
                    fragmentTransaction.add(R.id.form_rel_layout, asthmaFormQ3Fragment, "Question 3 fragment Active");
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
