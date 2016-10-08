package com.example.rigot.weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by rigot on 10/6/2016.
 */

public class WeatherUtil {

    static ArrayList<Weather> weatherParser (String str) throws JSONException {

        ArrayList<Weather> hourlyWeather = new ArrayList<>();
        JSONObject root = new JSONObject(str);

        if(root.getJSONObject("response").has("error")){
            return null;
        }else{
            JSONArray hourlyForecast = root.getJSONArray("hourly_forecast");
            JSONObject hour;
            Weather currentHour;
            String time, temperature, dewpoint, clouds, iconURL, windSpeed, windDirection, climateType, humidity, feelsLike,
                    pressure;

            //get 10 hours worth of weather
            for(int j=0; j<hourlyForecast.length(); j++){
                hour = hourlyForecast.getJSONObject(j);
                time = hour.getJSONObject("FCTTIME").getString("civil");
                temperature = hour.getJSONObject("temp").getString("english");
                dewpoint = hour.getJSONObject("dewpoint").getString("english");
                clouds = hour.getString("condition");
                iconURL = hour.getString("icon_url");
                windSpeed = hour.getJSONObject("wspd").getString("english");
                windDirection = hour.getJSONObject("wdir").getString("dir");
                climateType = hour.getString("wx");
                humidity = hour.getString("humidity");
                feelsLike = hour.getJSONObject("feelslike").getString("english");
                pressure = hour.getJSONObject("mslp").getString("metric");
                currentHour = new Weather(time, temperature, clouds, dewpoint, iconURL, windSpeed, windDirection,
                        climateType, humidity, feelsLike, pressure);
                hourlyWeather.add(currentHour); //add current hour to arraylist of hourly weather
            }

            return hourlyWeather;
        }
    }
}
