package examensarbete.diacert_android.API;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

import examensarbete.diacert_android.Database.KeyDBHandler;
import examensarbete.diacert_android.MainActivity;

/**
 * Created by backevik on 16-04-11.
 */
public class TestAPI extends AsyncTask<String, Void, String> {

    private HttpURLConnection urlConnection;
    private URL url;
    private StringBuilder sb;

    public TestAPI(){

    }

    @Override
    protected String doInBackground(String... params) {
        // params[0] is command
        // if command is pair then param[1] is code
        // if command is steps then param[1] is data and param[2] is timestamp
        String[] param = params;
        try {

            if(param[0].equals("pair")){
                url = new URL("http://46.101.96.201:8080/api/devices/connect/"+param[1]);

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestProperty("Content-Type","x-www-form-urlencoded");
                urlConnection.setUseCaches(false);
                urlConnection.setAllowUserInteraction(false);

                urlConnection.setRequestMethod("GET");

                urlConnection.connect();
                int status = urlConnection.getResponseCode();
                Log.d(getClass().getName().toString(), "Status code "+status);
                switch (status) {
                    case 200:
                        BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                        sb = new StringBuilder();
                        String line;
                        while ((line = br.readLine()) != null) {
                            sb.append(line + "\n");
                        }
                        br.close();
                        Log.d("Status Code: ", status + "");
                        return sb.toString();
                }

            }else if(param[0].equals("steps")) {

                try {
                    url = new URL("http://46.101.96.201:8080/api/steps");
                    urlConnection = (HttpURLConnection) url.openConnection();

                    urlConnection.setRequestProperty("Content-Type","application/json");
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setRequestProperty("key",param[1]);

                    urlConnection.setUseCaches(false);
                    urlConnection.setDoInput(true);
                    urlConnection.setDoOutput(true);

                    urlConnection.connect();

                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("user",param[1]);
                    jsonParam.put("steps",param[2]);
                    jsonParam.put("timestamp",param[3]);

                    DataOutputStream printout = new DataOutputStream(urlConnection.getOutputStream ());
                    String str = jsonParam.toString();
                    byte[] data=str.getBytes("UTF-8");
                    printout.write(data);
                    printout.flush ();
                    printout.close ();

                    StringBuilder sb = new StringBuilder();

                    BufferedReader br = new BufferedReader(new InputStreamReader(
                            urlConnection.getInputStream(),"utf-8"));
                    String line = "";
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();

                    System.out.println("POST Request: "+sb.toString());


                } catch (Exception e) {

                    e.printStackTrace();
                    return null;

                } finally {

                    if(urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }

            }else if(param[0].equals("adform")){

                try {
                    url = new URL("http://46.101.96.201:8080/api/forms/ad/");
                    urlConnection = (HttpURLConnection) url.openConnection();

                    urlConnection.setRequestProperty("Content-Type","application/json");
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setRequestProperty("key",param[1]);

                    urlConnection.setUseCaches(false);
                    urlConnection.setDoInput(true);
                    urlConnection.setDoOutput(true);

                    urlConnection.connect();

                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("user",param[1]);
                    for(int i = 1; i<=14; i++){
                        jsonParam.put("question"+i,param[i+1]);
                    }
                    jsonParam.put("anxietyscore",param[16]);
                    jsonParam.put("depressionscore",param[17]);

                    DataOutputStream printout = new DataOutputStream(urlConnection.getOutputStream ());
                    String str = jsonParam.toString();
                    byte[] data=str.getBytes("UTF-8");
                    printout.write(data);
                    printout.flush ();
                    printout.close ();

                    StringBuilder sb = new StringBuilder();

                    BufferedReader br = new BufferedReader(new InputStreamReader(
                            urlConnection.getInputStream(),"utf-8"));
                    String line = "";
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();

                    System.out.println("POST Request: "+sb.toString());


                } catch (Exception e) {

                    e.printStackTrace();
                    return null;

                } finally {

                    if(urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }

            }else if(param[0].equals("asthmaform")){

                try {
                    url = new URL("http://46.101.96.201:8080/api/forms/asthma/");
                    urlConnection = (HttpURLConnection) url.openConnection();

                    urlConnection.setRequestProperty("Content-Type","application/json");
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setRequestProperty("key",param[1]);

                    urlConnection.setUseCaches(false);
                    urlConnection.setDoInput(true);
                    urlConnection.setDoOutput(true);

                    urlConnection.connect();

                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("user",param[1]);
                    jsonParam.put("question1",param[2]);
                    jsonParam.put("question2",param[3]);
                    jsonParam.put("question3",param[4]);
                    jsonParam.put("question4",param[5]);
                    jsonParam.put("question5",param[6]);
                    jsonParam.put("score",param[7]);

                    DataOutputStream printout = new DataOutputStream(urlConnection.getOutputStream ());
                    String str = jsonParam.toString();
                    byte[] data=str.getBytes("UTF-8");
                    printout.write(data);
                    printout.flush ();
                    printout.close ();

                    StringBuilder sb = new StringBuilder();

                    BufferedReader br = new BufferedReader(new InputStreamReader(
                            urlConnection.getInputStream(),"utf-8"));
                    String line = "";
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();

                    System.out.println("POST Request: "+sb.toString());


                } catch (Exception e) {

                    e.printStackTrace();
                    return null;

                } finally {

                    if(urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }


            }else{
                throw new Exception("Faulty command sent!");
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
