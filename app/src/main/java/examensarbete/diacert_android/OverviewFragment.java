package examensarbete.diacert_android;

import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import android.support.design.widget.Snackbar;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.fitness.Fitness;

/**
 * Created by backevik on 16-04-08.
 */
public class OverviewFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.content_main, container, false);

        final Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);

        View formsView = (View) v.findViewById(R.id.layout_forms);
        ImageView formsImage = (ImageView) formsView.findViewById(R.id.overview_forms);
        formsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.animator.scale_in, R.animator.scale_out);
                FormsFragment formsFragment = new FormsFragment();
                fragmentTransaction.replace(R.id.app_bar_main_coordLayout, formsFragment, "FormsFragment Active");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                toolbar.setTitle("Formulär");
            }
        });


        return v;
    }

}
