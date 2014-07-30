package com.woodbug.chatora;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class VenueActivity extends Activity {

  TextView result;
  Context context;
  ProgressDialog progDialogVenue;
  String venueId;
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_venue);
        
    result     = (TextView)findViewById(R.id.name);
    
    venueId = getIntent().getExtras().getString("uniqueId");
    
    progDialogVenue = new ProgressDialog(this);
    progDialogVenue.setMessage("Fetching you the Venue Details..");
    progDialogVenue.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    progDialogVenue.setCancelable(false);
    progDialogVenue.setCanceledOnTouchOutside(false);
    progDialogVenue.show(); 
    new FetchCompleteVenue().execute();
  }

  
  private class FetchCompleteVenue extends AsyncTask<Object, Object, Object> {

    boolean nError;
    CompleteVenue cVenue;
    StringBuilder path = new StringBuilder(
      "https://api.foursquare.com/v2/venues/VENUE_ID?client_id"
      + "=34SHI3D0MYXRMBENDLBUAX03M5ZD3L1MIMLF0DK0RKVMSWFD&"
      + "client_secret=2CGG25UP03QZ4RF425LDYUYYUJZHTSKHDBZ1DC3X"
      + "JVS1E3LN&v=20140511");

      
    @Override
    protected Object doInBackground(Object... params) {
      try {
        HttpClient client     = new DefaultHttpClient();
        HttpGet get           = new HttpGet(path.toString().replaceAll("VENUE_ID", venueId));
        HttpResponse response = client.execute(get);
        String result         = EntityUtils.toString(response.getEntity());
        cVenue = new CompleteVenue(new JSONObject(result).getJSONObject("response").getJSONObject("venue"));
        
      } catch (Exception e) {
        nError = true;
        Log.e("NetworkOperation::doInBackground",
        e.getClass().getName() + ":" + e.getMessage());
        Toast.makeText(getApplicationContext(), "Network error try again..", Toast.LENGTH_LONG).show();
      }
      return null;
    }
      
      
    @Override
    protected void onPostExecute(Object nothing) {
        
      progDialogVenue.dismiss();
      if(!nError) {
        StringBuilder sb = new StringBuilder();
        if(cVenue.getName() != null)
          sb.append("Name: " + cVenue.getName());
        if(cVenue.getLocation() != null)
          sb.append("\n" + "Location: " + cVenue.getLocation());
        if(cVenue.getCheckins() != null)
          sb.append("\n" + "Checkins: " + cVenue.getCheckins());
        sb.append("\n" + (cVenue.getVerified() ? "This place is verified" : "This place is not verified"));
        if(cVenue.getHereNow() != null)
          sb.append("\n" + "Here now: " + cVenue.getHereNow());
        if(cVenue.getContact() != null)
          sb.append("\n" + "Phone: " + cVenue.getContact());
        if(cVenue.getSpecial() != null)
          sb.append("\n" + "Specials: " + cVenue.getSpecial());
        if(cVenue.getHours() != null)
          sb.append("\n" + "Timing: " + cVenue.getHours());
        if(cVenue.getPrice() != null)
          sb.append("\n" + "Price: " + cVenue.getPrice());
        if(cVenue.getRating() != null)
          sb.append("\n" + "Ratings: " + cVenue.getRating());
        if(cVenue.getBeenHere() != null)
          sb.append("\n" + "BeenHere: " + cVenue.getBeenHere());
        if(cVenue.getLikes() != null)
          sb.append("\n" + "Likes: " + cVenue.getLikes());
        if(cVenue.getDislike()) sb.append("\n" + "You dislike this Place");
        result.setText(sb.toString());
      }
        
    }
      
  }

}