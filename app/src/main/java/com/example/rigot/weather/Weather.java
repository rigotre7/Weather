package com.example.rigot.weather;

import java.io.Serializable;

/**
 * Created by rigot on 10/6/2016.
 */

public class Weather implements Serializable{

    String time, temperature, dewpoint, clouds, iconURL, windSpeed, windDirection, climateType, humidity, feelsLike, pressure;

    public Weather(String time, String temperature, String clouds, String dewpoint, String iconURL, String windSpeed, String windDirection, String climateType, String humidity, String feelsLike, String pressure) {
        this.time = time;
        this.temperature = temperature;
        this.clouds = clouds;
        this.dewpoint = dewpoint;
        this.iconURL = iconURL;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
        this.climateType = climateType;
        this.humidity = humidity;
        this.feelsLike = feelsLike;
        this.pressure = pressure;
    }
}
