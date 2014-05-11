package com.woodbug.chatora;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.woodbug.chatora.data.PersistantData;

public class Suggestion extends ActionBarActivity {

  TextView welcome;
  ListView restaurantList;
  LinearLayout layout;
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_suggestion);

    welcome = (TextView)findViewById(R.id.welcome);
    restaurantList = (ListView)findViewById(R.id.restaurant_list);
    
    layout = (LinearLayout)findViewById(R.id.suggestion_activity);
    Drawable background = layout.getBackground();
    background.setAlpha(90);
    
    String name = new PersistantData(getApplicationContext()).getDisplayName();
    welcome.setText("Welcome chatore " + name + " this food is near you...");
    
    Intent intent = getIntent();    
    if(intent.getIntExtra("status", 0) == 200) {
      try {
    	  
        ArrayList<String> restaurants = new ArrayList<String>();
        JSONObject result = new JSONObject(intent.getStringExtra("result"));
        //Log.i("response", "" + result.getJSONObject("response"));
        //Log.i("response", "" + result.getJSONObject("response").getJSONArray("venues"));
        JSONArray venues = result.getJSONObject("response").getJSONArray("venues");
        
        Log.i("size", "" + venues.length());
        for(int i = 0; i < venues.length(); i++) {
          StringBuilder ven = new StringBuilder();
          JSONObject venue = venues.getJSONObject(i);
          ven.append(venue.getString("name") + " is " + venue.getJSONObject("location").getInt("distance") + " meters far.\n");
          restaurants.add(ven.toString());
        }
        
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
          (getApplicationContext(), android.R.layout.simple_list_item_1, 
            restaurants);
        restaurantList.setAdapter(adapter);
      
      } catch(JSONException e) {
        Log.e("JSONException", e.getMessage());
      }
      
    } else {
      finish();
    }
  }

}
