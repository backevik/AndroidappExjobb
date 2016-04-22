package examensarbete.diacert_android.AsthmaForm;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.sql.Date;
import java.util.concurrent.ExecutionException;

import examensarbete.diacert_android.API.TestAPI;
import examensarbete.diacert_android.Database.KeyDBHandler;
import examensarbete.diacert_android.Database.LogDBHandler;
import examensarbete.diacert_android.MainActivity;
import examensarbete.diacert_android.R;

/**
 * Created by backevik on 16-04-17.
 */
public class AsthmaFormResultFragment extends Fragment {
    private Bundle bundle;
    private TestAPI testAPI;
    private KeyDBHandler keyDBHandler;
    private LogDBHandler logDBHandler;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.astma_form_view_result, container, false);
        bundle = this.getArguments();
        int result = bundle.getInt("q1") + bundle.getInt("q2") + bundle.getInt("q3") + bundle.getInt("q4") + bundle.getInt("q5");
        testAPI = new TestAPI();
        keyDBHandler = new KeyDBHandler(getActivity(),null);
        logDBHandler = new LogDBHandler(getActivity(),null);
        String resp = "";

        try {//finish parameters in POST request.
            resp = testAPI.execute("form",keyDBHandler.getData(),bundle.getString("qt1"),bundle.getString("qt2"),bundle.getString("qt3")
                    ,bundle.getString("qt4"),bundle.getString("qt5"), result+"").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

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

        logDBHandler.addData(System.currentTimeMillis(),"Formulär",result+"");

        return v;
    }
}
