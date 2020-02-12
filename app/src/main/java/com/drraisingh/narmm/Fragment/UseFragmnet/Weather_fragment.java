package com.drraisingh.narmm.Fragment.UseFragmnet;

import android.app.Activity;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.drraisingh.narmm.Model.Function;
import com.drraisingh.narmm.R;
import com.drraisingh.narmm.activity.MainActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

/**
 * Created by Raghvendra Sahu on 05-Jan-20.
 */
public class Weather_fragment extends Fragment{

TextView txDay,txTemp,txcld,txcloud, txhumid, txwind;
ImageView imgcloud;

    private FusedLocationProviderClient client;
    private double Latitude;
    private double Longitude;
    private String query = "Qry";
    Typeface weatherFont;
    Activity activity;

    public Weather_fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_weather, container, false);
        txDay=view.findViewById(R.id.txDay);
        txTemp = view.findViewById(R.id.txtemp);
        txcld = view.findViewById(R.id.txcloud);
        ((MainActivity) getActivity()).setTitle("Weather");

        activity=getActivity();
        weatherFont= Typeface.createFromAsset(activity.getAssets(), "fonts/weathericons-regular-webfont1.ttf");

        requestPermission();
        client = LocationServices.getFusedLocationProviderClient(activity);
        txTemp.setTypeface(weatherFont);
        txcld.setTypeface(weatherFont);
        latlon();

        return view;
    }

    private void requestPermission()
    {
        ActivityCompat.requestPermissions(activity, new String[]{ACCESS_FINE_LOCATION}, 1);
    }

    private void latlon()
    {
        if (ActivityCompat.checkSelfPermission(activity, ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED )
        {
            return;
        }
        client.getLastLocation().addOnSuccessListener(activity, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location!=null)
                {
                    Latitude = location.getLatitude();
                    Longitude = location.getLongitude();

                    DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    Date date = new Date(location.getTime()) ;
                    String dTime = format.format(date) ;

                    DownloadWeather dweather = new DownloadWeather();
                    dweather.execute(query);
                    //etx.setText("Loction_T : "+location.getTime());
                }
            }
        });
    }

    class DownloadWeather extends AsyncTask< String, Void, String > {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //loader.setVisibility(View.VISIBLE);
        }
        protected String doInBackground(String...args) {

            String xml = Function.executeGet("https://openweathermap.org/data/2.5/weather?"+ "lat="+Latitude+"&lon="+Longitude+"&appid=b6907d289e10d714a6e88b30761fae22");
            return xml;
        }
        @Override
        protected void onPostExecute(String xml) {

            try {
                JSONObject json = new JSONObject(xml);
                if (json != null) {
                    JSONObject details = json.getJSONArray("weather").getJSONObject(0);
                    JSONObject main = json.getJSONObject("main");
                    JSONObject wind = json.getJSONObject("wind");
                    SimpleDateFormat df = new SimpleDateFormat("EEEE");
                    Date date = new Date(json.getLong("dt")*1000);
                    String dayd = df.format(date);

                    String Temperature = String.format("%.2f", main.getDouble("temp")) + "°";

                    txDay.setText(dayd);
                    txTemp.setText(Html.fromHtml(Function.setWeatherIcon(details.getInt("id"),
                            json.getJSONObject("sys").getLong("sunrise") * 1000,
                            json.getJSONObject("sys").getLong("sunset") * 1000))+" "+Temperature);
                    //txcld.setText(Html.fromHtml(Function.setWeatherIcon(details.getInt("id"),
                         //   json.getJSONObject("sys").getLong("sunrise") * 1000,
                         //   json.getJSONObject("sys").getLong("sunset") * 1000)));
                   // txcld.setText(String.format("%.2f", main.getDouble("temp")) + "°");
                    //txhumid.setText(main.getString("humidity") + "%");
                   // txwind.setText(wind.getDouble("speed")+"m/s");
                    Log.e("GET DATA",">>>____DOUBLE");
                    //Log.e("XML",">>>"+xml);
                }
            }                                   catch (JSONException e) {
                Toast.makeText(activity, "Error, Check City", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
