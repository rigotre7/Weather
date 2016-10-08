package com.example.rigot.weather;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by rigot on 10/7/2016.
 */

public class WeatherAdapter extends ArrayAdapter<Weather> {

    Context context;
    ArrayList<Weather> hours;
    public WeatherAdapter(Context context, int resource, ArrayList<Weather> objects) {
        super(context, resource, objects);
        this.context =context;
        this.hours = objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;  //create ViewHolder instance
        if(convertView == null){    //not a recycled view
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_layout_hourly, parent, false);  //inflate the convertView
            holder = new ViewHolder();
            holder.image = (ImageView) convertView.findViewById(R.id.image);    //store views in holder class
            holder.timecondition = (TextView) convertView.findViewById(R.id.timecondition);
            holder.temp = (TextView) convertView.findViewById(R.id.temp);
            convertView.setTag(holder); //set holder in convertView tag
        }

        holder = (ViewHolder) convertView.getTag(); //get holder

        Picasso.with(context).load(hours.get(position).iconURL).into(holder.image);    //display weather attributes
        holder.timecondition.setText(hours.get(position).time + "\n" + hours.get(position).climateType);
        holder.temp.setText(hours.get(position).temperature + " F");


        return convertView;

    }

    //class holds references to views
    static class ViewHolder{
        ImageView image;
        TextView timecondition;
        TextView temp;
    }
}
