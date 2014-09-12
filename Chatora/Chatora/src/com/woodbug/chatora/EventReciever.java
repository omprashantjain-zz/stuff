package com.woodbug.chatora;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.TrafficStats;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.widget.Toast;

import com.woodbug.chatora.data.PersistantData;

public class EventReciever extends BroadcastReceiver {

  long userPresent = 0;
  
  @Override
  public void onReceive(Context context, Intent intent) {
    GlobalApp.init(context);
    Log.i("Done", "Generated");
    
    String action = intent.getAction();
    
    if (action.equals("android.intent.action.BOOT_COMPLETED")) {
      GlobalApp.setAlarm();
    
    } else if(action.equals(Intent.ACTION_SCREEN_OFF)) {
      SharedPreferences channel = GlobalApp.context.getSharedPreferences("Chatora", Context.MODE_PRIVATE);
      float screenMinutes = channel.getFloat("screenUsageMinutes", 0) 
                           + (System.currentTimeMillis() - userPresent) / 60;
      channel.edit().putFloat("screenUsageMinutes", screenMinutes).apply();
      
    } else if(action.equals(Intent.ACTION_USER_PRESENT)) {
      userPresent = System.currentTimeMillis();
    
    } else if(action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
      SharedPreferences channel = GlobalApp.context.getSharedPreferences("Chatora", Context.MODE_PRIVATE);
      float mobileInternetMB = channel.getFloat("mobileInternetMB", 0) 
                           + (TrafficStats.getMobileRxBytes() + TrafficStats.getMobileTxBytes()) / (1024 * 1024);
      channel.edit().putFloat("mobileInternetMB", mobileInternetMB).apply();
    
    } else {
      new PlacesList().execute();	
    }
  }

  public void generateNotification(Context context, Venue venue) {
    NotificationCompat.Builder mBuilder =
      new NotificationCompat.Builder(context)
      .setSmallIcon(R.drawable.ic_launcher)
      .setContentTitle("Have Lunch")
      .setContentText(venue.getNotificationText());
  
    Intent resultIntent = SuggestionActivity.fetchDetailsIntent(context, venue);
    TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
    
    stackBuilder.addParentStack(VenueActivity.class);
    stackBuilder.addNextIntent(resultIntent);
    PendingIntent resultPendingIntent =
      stackBuilder.getPendingIntent(
        0,
        PendingIntent.FLAG_UPDATE_CURRENT
      );
    mBuilder.setContentIntent(resultPendingIntent);
    NotificationManager mNotificationManager =
      (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    // mId allows you to update the notification later on.
    mNotificationManager.notify(1, mBuilder.build());
  }
  

  private class PlacesList extends AsyncTask<Object, Object, Object> {

    boolean nError;
    Venue venue;
    StringBuilder path = new StringBuilder(
      "https://api.foursquare.com/v2/venues/search?client_id"
      + "=34SHI3D0MYXRMBENDLBUAX03M5ZD3L1MIMLF0DK0RKVMSWFD&"
      + "client_secret=2CGG25UP03QZ4RF425LDYUYYUJZHTSKHDBZ1DC3X"
      + "JVS1E3LN&v=20140511&ll=");

    
    @Override
    protected Object doInBackground(Object... params) {
      try {
        path.append(new PersistantData(GlobalApp.context).getLoation());
        HttpClient client     = new DefaultHttpClient();
        HttpGet get           = new HttpGet(path.toString());
        HttpResponse response = client.execute(get);
        String res            = EntityUtils.toString(response.getEntity());
            
        JSONObject result = new JSONObject(res);
        JSONArray venues = result.getJSONObject("response").getJSONArray("venues");
        venue = new Venue(venues.getJSONObject(0));
            
      } catch (Exception e) {
        nError = true;
        Log.e("NetworkOperation::doInBackground",
        e.getClass().getName() + ":" + e.getMessage());
        Toast.makeText(GlobalApp.context, "Network error try again..", Toast.LENGTH_LONG).show();
      }
      return null;
    }
    
    
    @Override
    protected void onPostExecute(Object nothing) {
      if(!nError) {
        generateNotification(GlobalApp.context, venue);
      }
      
    }  
    
  }

}