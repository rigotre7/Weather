package com.example.rigot.weather;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import java.util.Date;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class CityWeather extends AppCompatActivity implements GetWeather.WeatherInfo{

    TextView location;
    ListView listView;
    ArrayList<Weather> hourlyWeather;
    String state, city;
    SharedPreferences preferences;
    public static ProgressDialog progDialog;
    boolean isCity = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_weather);

        location = (TextView)findViewById(R.id.currentLocation);
        listView = (ListView) findViewById(R.id.listView);

            String url = "http://api.wunderground.com/api/47773ca7f1a3c6c5/hourly/q/state/city.json";
        if(getIntent()!=null){
            state = getIntent().getStringExtra("state");
            city = getIntent().getStringExtra("city");
            if(city.contains(" ")) {
                url = url.replace("state", state);
                url = url.replace("city", city.replace(" ", "_"));
            }else{
                url = url.replace("state", state);
                url = url.replace("city", city);
            }
        }

        new GetWeather(CityWeather.this).execute(url);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent detailsIntent = new Intent(CityWeather.this, DetailsActivity.class);
                detailsIntent.putExtra("index", i);
                detailsIntent.putExtra("hours", hourlyWeather);
                detailsIntent.putExtra("city", city);
                detailsIntent.putExtra("state", state);
                startActivity(detailsIntent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.favorite, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item1:
                HashMap<String, Favorite> favorites;    //create HashMap of favorite cities
                Gson gson =  new Gson();
                preferences = getSharedPreferences("favoriteJSONFile", MODE_PRIVATE); //retrieve/create shared preference file
                String favoriteJSONString = preferences.getString("favoriteJSON", "");  //retrieve json string from preferences

                Type listType = new TypeToken<HashMap<String, Favorite>>(){}.getType(); //store the type we expect
                favorites = gson.fromJson(favoriteJSONString, listType);
                //if there aren't any favorites already stored
                if(favorites == null)
                    favorites  = new HashMap<String, Favorite>();   //initialize favorites HashMap

                //add the favorite city to the HashMap
                favorites.put(city, new Favorite(city, state, new Date(), hourlyWeather.get(0).temperature));

                String jsonString = gson.toJson(favorites); //convert favorites HashMap to JSON
                SharedPreferences.Editor prefEditor = preferences.edit();
                prefEditor.putString("favoriteJSON", jsonString);       //edit the shared preferences
                prefEditor.commit();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(!isCity){
            menu.getItem(0).setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void setData(ArrayList<Weather> hours) {
        progDialog.dismiss();
        //get error message, display message to the user and finish activity after 5 seconds
        if(hours==null) {
            Toast.makeText(this, "No cities match your search query", Toast.LENGTH_LONG).show();
            isCity = false;
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    finish();
                }
            }, 5000);
        }else{
            hourlyWeather = hours;
            isCity = true;
            location.setText("Current Location: " + city + ", " + state);
            WeatherAdapter adapter = new WeatherAdapter(this, R.layout.row_layout_hourly, hourlyWeather);
            listView.setAdapter(adapter);
            adapter.setNotifyOnChange(true);
        }
    }

    @Override
    public void setProgress() {

        progDialog = ProgressDialog.show(CityWeather.this, "Loading", "Loading Hourly Data", true);

    }

}
