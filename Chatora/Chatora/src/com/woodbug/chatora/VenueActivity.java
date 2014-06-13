package com.woodbug.chatora;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class VenueActivity extends ActionBarActivity {

  TextView name, location, stats, category,
    verified, hereNow, contact, special, hours, price,
    rating, beenHere, likes, like, dislike;

  Context context;
  ProgressDialog progDialogVenue;
  String venueId;
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_venue);
        
    name     = (TextView)findViewById(R.id.name);
    location = (TextView)findViewById(R.id.location);
    stats    = (TextView)findViewById(R.id.stats);
    category = (TextView)findViewById(R.id.category);
    verified = (TextView)findViewById(R.id.verified);
    hereNow  = (TextView)findViewById(R.id.hereNow);
    contact  = (TextView)findViewById(R.id.contact);
    special  = (TextView)findViewById(R.id.special);
    hours    = (TextView)findViewById(R.id.hours);
    price    = (TextView)findViewById(R.id.price);
    rating   = (TextView)findViewById(R.id.rating);
    beenHere = (TextView)findViewById(R.id.beenHere);
    likes    = (TextView)findViewById(R.id.likes);
    like     = (TextView)findViewById(R.id.like);
    dislike  = (TextView)findViewById(R.id.dislike);
    
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
        name.setText(cVenue.getName());
      }
        
    }  
      
  }

}