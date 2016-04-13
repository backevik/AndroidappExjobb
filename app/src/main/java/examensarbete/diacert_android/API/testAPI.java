package examensarbete.diacert_android.API;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import examensarbete.diacert_android.MainActivity;

/**
 * Created by backevik on 16-04-11.
 */
public class testAPI extends AsyncTask<String, Void, String> {

    private HttpURLConnection urlConnection;
    private URL url;
    private StringBuilder sb;

    public testAPI(){

    }

    @Override
    protected String doInBackground(String... params) {
        // params[0] is command
        // if command is pair then param[1] is code
        // if command is steps then param[1] is data and param[2] is timestamp
        String[] param = params;
        try {

            if(param[0].equals("pair")){
                url = new URL("http://46.101.96.201:8080/api/users/"+param[1]);

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestProperty("Content-Type","x-www-form-urlencoded");
                urlConnection.setUseCaches(false);
                urlConnection.setAllowUserInteraction(false);

                urlConnection.setRequestMethod("GET");

            }else if(param[0].equals("steps")){

            }else{
                throw new Exception("Faulty command sent!");
            }

            urlConnection.connect();
            int status = urlConnection.getResponseCode();
            Log.d(getClass().getName().toString(), "Status code "+status);
            switch (status) {
                case 200:
                    BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line+"\n");
                    }
                    br.close();
                    Log.d("Status Code: ", status+"");
                    return sb.toString();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return null;
    }
}
