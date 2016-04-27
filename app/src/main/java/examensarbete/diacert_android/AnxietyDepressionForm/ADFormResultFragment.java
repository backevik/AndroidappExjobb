package examensarbete.diacert_android.AnxietyDepressionForm;

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

import java.util.concurrent.ExecutionException;

import examensarbete.diacert_android.API.TestAPI;
import examensarbete.diacert_android.Database.KeyDBHandler;
import examensarbete.diacert_android.Database.LogDBHandler;
import examensarbete.diacert_android.MainActivity;
import examensarbete.diacert_android.R;

/**
 * Created by backevik on 16-04-25.
 */
public class ADFormResultFragment extends Fragment {
    private Bundle bundle;
    private TestAPI testAPI;
    private KeyDBHandler keyDBHandler;
    private LogDBHandler logDBHandler;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.ad_form_view_result, container, false);
        bundle = this.getArguments();
        int resultAnxiety = bundle.getInt("q1") + bundle.getInt("q3") + bundle.getInt("q5") + bundle.getInt("q7") + bundle.getInt("q9") +
                bundle.getInt("q11") + bundle.getInt("q13") - 7;
        int resultDepression = bundle.getInt("q2") + bundle.getInt("q4") + bundle.getInt("q6") + bundle.getInt("q8") + bundle.getInt("q10") +
                bundle.getInt("q12") + bundle.getInt("q14") - 7;
        testAPI = new TestAPI();
        keyDBHandler = new KeyDBHandler(getActivity(),null);
        logDBHandler = new LogDBHandler(getActivity(),null);
        String resp = "";

        try {//finish parameters in POST request.
            resp = testAPI.execute("adform",keyDBHandler.getData(),bundle.getString("qt1"),bundle.getString("qt2"),bundle.getString("qt3")
                    ,bundle.getString("qt4"),bundle.getString("qt5"), bundle.getString("qt6"), bundle.getString("qt7"),
                    bundle.getString("qt8"),bundle.getString("qt9"),bundle.getString("qt10"),bundle.getString("qt11"),
                    bundle.getString("qt12"),bundle.getString("qt13"),bundle.getString("qt14"), resultAnxiety+"",resultDepression+"").get();
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

        TextView anxietyResultTextView = (TextView) v.findViewById(R.id.anxietyresult);
        TextView depressionResultTextView = (TextView) v.findViewById(R.id.depressionresult);

        Button doneBtn = (Button) v.findViewById(R.id.donebtn);

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }
        });

        if(resultAnxiety<=6){
            anxietyResultTextView.setText("Ingen besvärande ångest");
        }else if(resultAnxiety>=7 && resultAnxiety<=10){
            anxietyResultTextView.setText("Mild till måttlig ångest");
        }else{
        anxietyResultTextView.setText("Förekomst av eventuell ångeststörning");
        }
        if(resultDepression<=6){
            depressionResultTextView.setText("Ej deprimerad");
        }else if(resultDepression>=7 && resultDepression<=10){
            depressionResultTextView.setText("Nedstämdhet");
        }else{
            depressionResultTextView.setText("Risk för depressionstillstånd som kan kräva läkarbehandling");
        }



        logDBHandler.addData(System.currentTimeMillis(),"Formulär","A:"+resultAnxiety+"/D:"+resultDepression);

        return v;
    }
}
