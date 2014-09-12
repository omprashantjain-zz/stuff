package com.woodbug.chatora;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.woodbug.chatora.data.PersistantData;

public class SuggestionActivity extends Activity {

  TextView welcome;
  ListView restaurantList;
  LinearLayout layout;

  float psychographicFactor_X,
        demoGraphicScore,
        collaborativeScore,
        netScore,
        finalRecommendationScore,
        scaledAdjustmentFactor;
  
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
  
  public float calculateFinalRecommendationScore() {
	psychographicFactor_X = calculatePsychographicFactor(); 
	demoGraphicScore      = calculateDemoGraphicScore();
	collaborativeScore    = calculateCollaborativeScore();
  
    netScore                 = (psychographicFactor_X * collaborativeScore) + (1-psychographicFactor_X) * demoGraphicScore;
    scaledAdjustmentFactor   = netScore / 10;
    return finalRecommendationScore = 50 * scaledAdjustmentFactor;
  }
  
  private float calculatePsychographicFactor() {
    
    SharedPreferences channel = GlobalApp.context.getSharedPreferences("Chatora", Context.MODE_PRIVATE);
    float noOfCalls          = channel.getFloat("noOfCalls", 0),
          screenUsageMinutes = channel.getFloat("screenUsageMinutes", 0),
          mobileInternetMB   = channel.getFloat("mobileInternetMB", 0),
          messagePerDay      = channel.getFloat("messagePerDay", 0);
    
    return (noOfCalls / 10) + (screenUsageMinutes / 180) + (mobileInternetMB / 512) + (messagePerDay / 15);
  }

  private float calculateDemoGraphicScore() {
    
    SharedPreferences channel = GlobalApp.context.getSharedPreferences("Chatora", Context.MODE_PRIVATE);
    float hHI        = channel.getFloat("hHI", 0),
          age        = channel.getFloat("age", 0),
          checkinVal = channel.getFloat("checkinVal", 0),
          likeVal    = channel.getFloat("likeVal", 0),
          genderVal  = channel.getFloat("genderVal", 0);
    
    return (hHI / 5) + (25 / age) + checkinVal + likeVal + genderVal;
  }
  
  private float calculateCollaborativeScore() {
    
    SharedPreferences channel = GlobalApp.context.getSharedPreferences("Chatora", Context.MODE_PRIVATE);
    float communityRating = channel.getFloat("communityRating", 0),
          noOfCheckin     = channel.getFloat("noOfCheckin", 0);
    
    return (communityRating / 10) + (noOfCheckin / 100) * 5;
  }
  
}