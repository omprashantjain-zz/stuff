package com.woodbug.chatora;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.woodbug.chatora.data.PersistantData;

public class SuggestionActivity extends ActionBarActivity {

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
    	  
        ArrayList<Venue> restaurants = new ArrayList<Venue>();
        JSONObject result = new JSONObject(intent.getStringExtra("result"));
        //Log.i("response", "" + result.getJSONObject("response"));
        Log.i("response", "" + result.getJSONObject("response").getJSONArray("venues"));
        JSONArray venues = result.getJSONObject("response").getJSONArray("venues");
        
        //Log.i("size", "" + venues.length());
        for(int i = 0; i < venues.length(); i++) {
          Venue venue = new Venue(venues.getJSONObject(i));
          restaurants.add(venue);
        }
        
        RestaurantAdapter adapter = new RestaurantAdapter(this, restaurants);
        restaurantList.setAdapter(adapter);
        restaurantList.setOnItemClickListener(new OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> parent, View view,
            int position, long id) {
            Intent intent = SuggestionActivity.fetchDetailsIntent
              (getApplicationContext(), (Venue)restaurantList.getItemAtPosition(position));
            startActivity(intent);
          }
        });
      
      } catch(JSONException e) {
        Log.e("JSONException", e.getMessage());
      }
      
    } else {
      finish();
    }
  }

  public static Intent fetchDetailsIntent(Context context, Venue venue) {
    Intent intent = new Intent(context, VenueActivity.class);
    Bundle bundle = new Bundle();
    bundle.putString("uniqueId", venue.getUniqueId());
    intent.putExtras(bundle);
    return intent;
  }
  
  @Override
  public void onBackPressed() {
    Intent i = new Intent(Intent.ACTION_MAIN);
    i.addCategory(Intent.CATEGORY_HOME);
    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    startActivity(i);
  }

}
