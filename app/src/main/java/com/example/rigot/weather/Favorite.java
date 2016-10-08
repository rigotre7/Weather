package com.example.rigot.weather;

import java.util.Date;

/**
 * Created by rigot on 10/8/2016.
 */

public class Favorite {

    String city;
    String state;
    Date dateAdded;
    String temperature;

    public Favorite(String city, String state, Date dateAdded, String temperature) {
        this.temperature = temperature;
        this.city = city;
        this.state = state;
        this.dateAdded = dateAdded;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    @Override
    public String toString() {
        return "Favorite{" +
                "city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", dateAdded=" + dateAdded +
                ", temperature=" + temperature +
                '}';
    }
}
