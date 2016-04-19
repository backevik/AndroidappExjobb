package examensarbete.diacert_android.AsthmaForm;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import examensarbete.diacert_android.MainActivity;
import examensarbete.diacert_android.R;

/**
 * Created by backevik on 16-04-17.
 */
public class AsthmaFormResultFragment extends Fragment {
    private Bundle bundle;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.astma_form_view_result, container, false);
        bundle = this.getArguments();
        int result = 0;

        View bLayout = (LinearLayout) getActivity().findViewById(R.id.buttonLayout);
        Button nextBtn = (Button) bLayout.findViewById(R.id.nextbtn);
        Button backBtn = (Button) bLayout.findViewById(R.id.backbtn);
        nextBtn.setVisibility(View.INVISIBLE);
        backBtn.setVisibility(View.INVISIBLE);

        View pLayout = (LinearLayout) getActivity().findViewById(R.id.asthmaProgressLayout);
        ProgressBar progressBar = (ProgressBar) pLayout.findViewById(R.id.asthmaProgressBar);
        progressBar.setVisibility(View.INVISIBLE);

        TextView resultText = (TextView) v.findViewById(R.id.result);

        Button doneBtn = (Button) v.findViewById(R.id.donebtn);

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }
        });

        result = bundle.getInt("q1") + bundle.getInt("q2") + bundle.getInt("q3") + bundle.getInt("q4") + bundle.getInt("q5");
        if(result < 20){
            resultText.setText("Din astma verkar inte ha varit under kontroll under de senaste fyra veckorna. Din läkare eller sköterska kan " +
                    "rekommendera en åtgärdsplan för att hjälpa dig att få bättre kontroll över din astma.");
        }else if(20<=result && result<=24){
            resultText.setText("Din astma verkar ha varit välkontrollerad men inte helt kontrollerad under de senaste fyra veckorna." +
                    " Din läkare eller sköterska kanske kan hjälpa dig att försöka nå total kontroll.");
        }else{
            resultText.setText("Du har haft total kontroll under de senaste fyra veckorna. Du har inte haft några symptom och inga astmarelaterade begränsningar." +
                    " Kontakta din läkare eller sköterska om det sker några förändringar.");
        }
        return v;
    }
}
