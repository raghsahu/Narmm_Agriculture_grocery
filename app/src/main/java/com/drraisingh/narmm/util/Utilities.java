package com.drraisingh.narmm.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;


import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.drraisingh.narmm.Adapter.Card_NewAdapter;
import com.drraisingh.narmm.Model.UseableModel.Cartlist_model;
import com.drraisingh.narmm.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.drraisingh.narmm.Config.BaseURL.GET_get_cart;

/**
 * Created by ICS on 5/3/2016.
 */
public class Utilities {

    public static String getRequestAndfindJSON(String myurl){

        String result = "";

        InputStream is = null;
        // Only display the first 500 characters of the retrieved
        // web page content.


        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(25000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();

            is = conn.getInputStream();

            // Convert the InputStream into a string
            String contentAsString = "";
            int i = 0;
            while ((i = is.read()) != -1) {
                contentAsString = contentAsString + (char) i;
            }
            is.close();
            result=contentAsString;
            // Toast.makeText(CatlogueSelect.this,contentAsString,Toast.LENGTH_LONG).show();
            Log.d("The response : ", contentAsString);

        }
        catch (Exception e) {
            System.out.println("exception in jsonparser class ........");
            e.printStackTrace();
            return null;
        }

        return result;

    }

    //method for post json
    public static String postParamsAndfindJSON(String url, String params) {
        JSONObject jObj = new JSONObject();
        String result = "";



        System.out.println("URL comes in jsonparser class is:  " + url + params);
        try {
            URL myurl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) myurl.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length", "" +
                    Integer.toString(params.getBytes().length));
            connection.setRequestProperty("Content-Language", "en-US");

            //Send request
            DataOutputStream wr = new DataOutputStream(
                    connection.getOutputStream ());
            wr.writeBytes(params);
            wr.flush();
            wr.close();
            //Get Response
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();
            while((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            return response.toString();


        } catch (Exception e) {
            System.out.println("exception in json parser class ........");
            e.printStackTrace();
            return null;
        }


    }



    // Method or showing alert dialog
    public static void Alert(final Context context, final String string) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Alert");
//        builder.setIcon(R.drawable.logo);
        builder.setMessage(string)
                .setCancelable(false).setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }



    //Method for St type face


    //Method for checking internet connection
    public static boolean isConnectingToInternet(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
        }
        return false;
    }

    public static void ShowToastMessage(Context ctactivity, String message) {
        Toast toast = Toast.makeText(ctactivity, message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
    public static boolean isValidName(String name) {

        if (name==null){
            return false;
        }else {
            String NAME_PATTERN = "^[A-Za-z]+$";
            Pattern pattern = Pattern.compile(NAME_PATTERN);
            Matcher matcher = pattern.matcher(name);
            return matcher.matches();
        }

    }
    public static boolean isValidNumber(String number) {

        if (number==null){
            return false;
        }
        else {
            String NAME_PATTERN = "[0-9]+";
            Pattern pattern = Pattern.compile(NAME_PATTERN);
            Matcher matcher = pattern.matcher(number);
            return matcher.matches();
        }

    }
    public final static boolean isValidEmail(CharSequence email) {
        if (email == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
    }
   
}
