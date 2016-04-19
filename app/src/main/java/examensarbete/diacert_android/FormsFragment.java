package examensarbete.diacert_android;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import examensarbete.diacert_android.AsthmaForm.AsthmaTestActivity;

/**
 * Created by backevik on 16-04-14.
 */
public class FormsFragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.content_forms, container, false);

        ImageView asthmaIconView = (ImageView) v.findViewById(R.id.formAstma);
        asthmaIconView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), AsthmaTestActivity.class);
                        getActivity().startActivity(intent);
                        getActivity().overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }
        });

        return v;
    }

}
