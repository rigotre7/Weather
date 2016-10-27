package com.example.rigot.weather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {

    ArrayList<Weather> hourlyWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        TextView locationtime = (TextView) findViewById(R.id.locationtime);
        TextView temp = (TextView) findViewById(R.id.temp);
        ImageView img = (ImageView)findViewById(R.id.image);
        TextView condition = (TextView) findViewById(R.id.conditionText);
        TextView maxTemp = (TextView)findViewById(R.id.maxtemp);
        TextView minTemp = (TextView)findViewById(R.id.mintemp);
        TextView feelsLike = (TextView)findViewById(R.id.feelsLike);
        TextView dewPoint = (TextView)findViewById(R.id.dewPoint);
        TextView humidity = (TextView)findViewById(R.id.humidity);
        TextView pressure = (TextView)findViewById(R.id.pressure);
        TextView clouds = (TextView)findViewById(R.id.clouds);
        TextView winds = (TextView)findViewById(R.id.winds);
        String state, city;
        int index;

        if(getIntent()!=null){
            city = getIntent().getStringExtra("city");
            state = getIntent().getStringExtra("state");
            index = getIntent().getIntExtra("index", 0);

            hourlyWeather = (ArrayList<Weather>) getIntent().getSerializableExtra("hours");
            int max = 0;
            int min = 1000;

            for(int j=0; j<hourlyWeather.size(); j++){
                int tempVal = Integer.parseInt(hourlyWeather.get(j).temperature);
                if(max < tempVal)
                    max = tempVal;

                if(min > tempVal)
                    min = tempVal;

            }

            locationtime.setText("Current Location: " + city + ", " + state + " (" + hourlyWeather.get(index).time + ")");
            temp.setText(hourlyWeather.get(index).temperature + (char) 0x00B0 + "F");
            condition.setText(hourlyWeather.get(index).climateType);
            Picasso.with(this).load(hourlyWeather.get(index).iconURL).into(img);
            maxTemp.setText(Integer.toString(max) + (char) 0x00B0 + " F");
            minTemp.setText(Integer.toString(min) + (char) 0x00B0 + " F");
            feelsLike.setText(hourlyWeather.get(index).feelsLike + (char) 0x00B0 + " F");
            humidity.setText(hourlyWeather.get(index).humidity + "%");
            dewPoint.setText(hourlyWeather.get(index).dewpoint + (char) 0x00B0 + " F");
            pressure.setText(hourlyWeather.get(index).pressure + " hPa");
            clouds.setText(hourlyWeather.get(index).clouds);
            winds.setText(hourlyWeather.get(index).windSpeed + " mph, " + hourlyWeather.get(index).windDirection);
        }
    }
}
