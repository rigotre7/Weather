package com.example.rigot.weather;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by shah on 10/7/2016.
 */

public class FavoriteAdapter extends ArrayAdapter<Favorite> {

    Context mContext;
    List<Favorite> favorites;
    int mResource;

    public FavoriteAdapter(Context context, int resource, List<Favorite> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
        this.favorites = objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {  //no recycled view
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mResource, parent, false);   //inflate the view
            holder.city = (TextView) convertView.findViewById(R.id.city);   //store city TextView
            holder.tempdate = (TextView) convertView.findViewById(R.id.tempDate);   //store tempDate TextView
            convertView.setTag(holder); //set holder as Tag in convertView
        }

        holder = (ViewHolder) convertView.getTag();
        DateFormat df = new SimpleDateFormat("MM/dd/yy");
        Favorite favorite = favorites.get(position);
        holder.city.setText(favorite.getCity() + ", " + favorite.getState());
        holder.tempdate.setText(favorite.getTemperature() + (char) 0x00B0 + " F" + "\n" + "Updated on: " + df.format(favorite.getDateAdded()));

        return convertView;
    }

    static class ViewHolder{
        TextView city;
        TextView tempdate;
    }
}
