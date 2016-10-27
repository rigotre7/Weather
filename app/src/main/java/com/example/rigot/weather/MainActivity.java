package com.example.rigot.weather;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.lang3.text.WordUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    EditText city;
    EditText state;
    ArrayList<Favorite> favoriteArrayList;
    FavoriteAdapter adapter;
    HashMap<String, Favorite> favorites;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //key: 47773ca7f1a3c6c5
        //example: http://api.wunderground.com/api/47773ca7f1a3c6c5/hourly/q/CA/San_Francisco.json

        city = (EditText)findViewById(R.id.cityText);
        state = (EditText)findViewById(R.id.stateText);
        Button submitButton = (Button) findViewById(R.id.submitButton);
        ListView lv = (ListView)findViewById(R.id.listView);
        TextView emptyText = (TextView)findViewById(R.id.textViewNoFAv);
        lv.setEmptyView(emptyText);
        ShowFavorites();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(city.getText().length() == 0){
                    Toast.makeText(MainActivity.this, "Enter a City", Toast.LENGTH_SHORT).show();
                }else if(state.getText().length() < 2){
                    Toast.makeText(MainActivity.this, "Enter a State", Toast.LENGTH_SHORT).show();
                }else{
                    String c = city.getText().toString();
                    String s = state.getText().toString();
                    Intent weatherIntent = new Intent(MainActivity.this, CityWeather.class);
                    weatherIntent.putExtra("city", WordUtils.capitalize(c));
                    weatherIntent.putExtra("state", s.toUpperCase());
                    startActivity(weatherIntent);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        ShowFavorites();
        super.onResume();
    }

    private void ShowFavorites(){
        SharedPreferences preferences = getSharedPreferences("favoriteJSONFile", MODE_PRIVATE);
        String favoriteJSONString = preferences.getString("favoriteJSON", "");
        if(!favoriteJSONString.isEmpty()){  //if the list of favorite cities isn't empty
            Gson gson =  new Gson();
            Type listType = new TypeToken<HashMap<String, Favorite>>(){}.getType();
            favorites = gson.fromJson(favoriteJSONString, listType);    //get favorites list

            ListView listView = (ListView) findViewById(R.id.listView);
            favoriteArrayList = new ArrayList<Favorite>(favorites.values());
            adapter = new FavoriteAdapter(this, R.layout.row_layout, favoriteArrayList);
            adapter.setNotifyOnChange(true);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent weatherIntent = new Intent(MainActivity.this, CityWeather.class);
                    TextView loc = (TextView)view.findViewById(R.id.city);
                    String[] cityState = String.valueOf(loc.getText()).split(",");

                    weatherIntent.putExtra("city", cityState[0]);
                    weatherIntent.putExtra("state", cityState[1].trim());
                    startActivity(weatherIntent);

                }
            });

            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                    new AlertDialog.Builder(MainActivity.this).setMessage("Are you sure you want to delete this City?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    favorites.remove(favoriteArrayList.get(position).getCity());
                                    favoriteArrayList.remove(favoriteArrayList.get(position));
                                    adapter.notifyDataSetChanged();

                                    Gson gson =  new Gson();
                                    SharedPreferences preferences = getSharedPreferences("favoriteJSONFile", MODE_PRIVATE);
                                    String jsonString = gson.toJson(favorites);
                                    SharedPreferences.Editor prefEditor = preferences.edit();
                                    prefEditor.putString("favoriteJSON", jsonString);
                                    prefEditor.commit();

                                    Toast.makeText(getApplicationContext(), "City Deleted", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeButton("No", null)
                            .show();
                    return true;
                }
            });
        }else{
        }
    }
}
