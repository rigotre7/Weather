package com.example.rigot.weather;

import android.os.AsyncTask;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by rigot on 10/6/2016.
 */

public class GetWeather extends AsyncTask<String, Void, ArrayList<Weather>> {
    WeatherInfo activity;

    public GetWeather(WeatherInfo activity){
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        activity.setProgress();
    }

    @Override
    protected ArrayList<Weather> doInBackground(String... strings) {

        try {
            URL url = new URL(strings[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            int statusCode = con.getResponseCode();

            if(statusCode == HttpURLConnection.HTTP_OK){
                BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder text = new StringBuilder();
                String line;

                while((line=reader.readLine()) != null){
                    text.append(line);
                }

                return WeatherUtil.weatherParser(text.toString());

            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;

    }

    @Override
    protected void onPostExecute(ArrayList<Weather> hours) {
        super.onPostExecute(hours);
        activity.setData(hours);
    }

    public interface WeatherInfo{
        void setData(ArrayList<Weather> hours);
        void setProgress();
    }
}
