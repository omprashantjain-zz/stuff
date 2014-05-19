package com.woodbug.chatora;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.foursquare.android.nativeoauth.FoursquareOAuth;
import com.foursquare.android.nativeoauth.model.AccessTokenResponse;
import com.foursquare.android.nativeoauth.model.AuthCodeResponse;
import com.woodbug.chatora.data.PersistantData;

public class MainActivity extends ActionBarActivity {

  private static String 
    authCode    = null,
    accessToken = null;
	  
  Button loginFourSquare;
  Button registerWithChatora;
  Intent intent;
  Context context;
  ProgressDialog progDialogProfile;
  PersistantData persistantData;
  LocationManager lManager;
  Location location;
  NetworkLocationListener networkListener = new NetworkLocationListener();
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    
	super.onCreate(savedInstanceState);
    context = getApplicationContext();
    persistantData = new PersistantData(context);
    intent = new Intent(context, Register.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    
    lManager = (LocationManager)context.getSystemService(LOCATION_SERVICE);
    lManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1, 0, networkListener);
 
    if(persistantData.isFourSquareRegistered() || persistantData.isChatoraRegistered()) {
      Intent i = FoursquareOAuth.getConnectIntent(context, Config.CLIENT_ID);
      startActivityForResult(i, Config.REQUEST_CODE_FSQ_CONNECT);
    }
	
	setContentView(R.layout.activity_main);

    loginFourSquare     = (Button)findViewById(R.id.login_four_square);
    registerWithChatora = (Button)findViewById(R.id.register);
    
    loginFourSquare.setOnClickListener(new OnClickListener() {
    
      @Override
      public void onClick(View v) {
        Intent i = FoursquareOAuth.getConnectIntent(context, Config.CLIENT_ID);
    	startActivityForResult(i, Config.REQUEST_CODE_FSQ_CONNECT);   
      }
    });
    
    registerWithChatora.setOnClickListener(new OnClickListener() {
    
      @Override
      public void onClick(View v) {
        getApplicationContext().startActivity(intent);
      }
    });
    
  }
  
  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	
    switch (requestCode) {
      
      case Config.REQUEST_CODE_FSQ_CONNECT:
        AuthCodeResponse codeResponse = FoursquareOAuth.getAuthCodeFromResult(resultCode, data);        
        if(codeResponse != null && codeResponse.getException() == null) {
          authCode = codeResponse.getCode();
          Intent i = FoursquareOAuth.getTokenExchangeIntent(context, Config.CLIENT_ID, Config.CLIENT_SECRET, authCode);
          startActivityForResult(i, Config.REQUEST_CODE_FSQ_TOKEN_EXCHANGE);
          Log.i("AuthCode", "" + authCode);
        } else {
          Log.e("AuthCodeResponse", codeResponse.getException().getMessage() + ":" + authCode);
        }
      break;
     
      case Config.REQUEST_CODE_FSQ_TOKEN_EXCHANGE:
        AccessTokenResponse tokenResponse = FoursquareOAuth.getTokenFromResult(resultCode, data);
        if(tokenResponse != null && tokenResponse.getException() == null) {
          accessToken = tokenResponse.getAccessToken();
          persistantData.setFourSquareRegistered();
          Log.i("AccessToken", "" + accessToken);
          Toast.makeText(context, "FourSquare login Successful", Toast.LENGTH_SHORT).show();

          progDialogProfile = new ProgressDialog(this);
          progDialogProfile.setMessage("Getting your details..");
          progDialogProfile.setProgressStyle(ProgressDialog.STYLE_SPINNER);
          progDialogProfile.setCancelable(false);
          progDialogProfile.setCanceledOnTouchOutside(false);
          progDialogProfile.show(); 
          new Profile().execute();
          
        } else {
          Log.e("AccessTokenResponse", tokenResponse.getException().getMessage() + ":" + accessToken);
        }
        break;
    }

  }
  private class NetworkLocationListener implements 
    LocationListener {

    @Override
    public void onLocationChanged(Location loc) {
      Log.i("Location", loc.getLatitude() + ", " + loc.getLongitude());
      location = loc;
      persistantData.setLocation(location.getLatitude() + "," + location.getLongitude());
      lManager.removeUpdates(networkListener); 
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}

    @Override
    public void onProviderEnabled(String provider) {}

    @Override
    public void onProviderDisabled(String provider) {}
  
  }

  private class Profile extends AsyncTask<Object, JSONObject, JSONObject> {

    boolean nError;
    StringBuilder path = new StringBuilder(
      "https://api.foursquare.com/v2/users/self?oauth_token=" + accessToken + "&v=20140519");
    
    @Override
    protected JSONObject doInBackground(Object... params) {
      JSONObject resultJSON = null,
                 user       = null;
      try {
        HttpClient client     = new DefaultHttpClient();
        HttpGet get           = new HttpGet(path.toString());
        HttpResponse response = client.execute(get);
        String result         = EntityUtils.toString(response.getEntity());
            
        //Log.i("NetworkResult", result);
        resultJSON = new JSONObject(result);
        user       = resultJSON.getJSONObject("response").getJSONObject("user");
        
      } catch (Exception e) {
        nError = true;
        Log.e("NetworkOperation::doInBackground",
        e.getClass().getName() + ":" + e.getMessage());
        Toast.makeText(getApplicationContext(), "Network error try again..", Toast.LENGTH_LONG).show();
      }
      return user;
    }
    
    
    @Override
    protected void onPostExecute(JSONObject user) {
      
      progDialogProfile.dismiss();
      if(!nError) {
        try {
          persistantData.setDisplayName(user.getString("firstName") + " " + user.getString("lastName"));
          persistantData.setGender(user.getString("gender"));
          getApplicationContext().startActivity(intent);    
        } catch (JSONException e) {
          Log.e("Profile", e.getClass() + ":" + e.getMessage());
        }
      }
      
    }  
    
  }
}
