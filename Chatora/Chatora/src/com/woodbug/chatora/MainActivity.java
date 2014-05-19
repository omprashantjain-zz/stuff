package com.woodbug.chatora;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.foursquare.android.nativeoauth.FoursquareOAuth;
import com.foursquare.android.nativeoauth.model.AccessTokenResponse;
import com.foursquare.android.nativeoauth.model.AuthCodeResponse;
import com.woodbug.chatora.data.PersistantData;

public class MainActivity extends ActionBarActivity implements OnClickListener{

  public static final String
    CLIENT_ID     = "34SHI3D0MYXRMBENDLBUAX03M5ZD3L1MIMLF0DK0RKVMSWFD",
    CLIENT_SECRET = "2CGG25UP03QZ4RF425LDYUYYUJZHTSKHDBZ1DC3XJVS1E3LN";
  
  public static final int
    REQUEST_CODE_FSQ_CONNECT        = 777,
    REQUEST_CODE_FSQ_TOKEN_EXCHANGE = 778;
  
  private static String 
    authCode    = null,
    accessToken = null;
  
  LinearLayout layout;
  EditText displayName;
  RadioGroup gender;
  SeekBar age;
  TextView ageView;
  Spinner hhi;
  Button signIn;
  Context context;
  ProgressDialog progDailog;
  LocationManager lManager;
  Location location;
  NetworkLocationListener networkListener = new NetworkLocationListener();
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    
    context = getApplicationContext();
    setContentView(R.layout.activity_main);
    
    Intent intent = FoursquareOAuth.getConnectIntent(context, CLIENT_ID);
    startActivityForResult(intent, REQUEST_CODE_FSQ_CONNECT);
    
    lManager = (LocationManager)context.getSystemService(LOCATION_SERVICE);
    lManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1, 0, networkListener);
    
    layout = (LinearLayout)findViewById(R.id.container);
    Drawable background = layout.getBackground();
    background.setAlpha(90);
    
    displayName = (EditText)findViewById(R.id.display_name);
    gender      = (RadioGroup)findViewById(R.id.gender);
    age         = (SeekBar)findViewById(R.id.age_bar);
    ageView     = (TextView)findViewById(R.id.age_view);
    hhi         = (Spinner)findViewById(R.id.hhi);
    signIn      = (Button)findViewById(R.id.signin);
 
    signIn.setOnClickListener(this);
    signIn.setEnabled(false);
    
    age.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
    
      @Override
      public void onStopTrackingTouch(SeekBar seekBar) {}
    
      @Override
      public void onStartTrackingTouch(SeekBar seekBar) {}
    
      @Override
      public void onProgressChanged(SeekBar seekBar, int progress,
          boolean fromUser) {
        ageView.setText((progress + 10) + " Years");
      }
      
    });
    
  }
  
  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	
    switch (requestCode) {
      
      case REQUEST_CODE_FSQ_CONNECT:
        AuthCodeResponse codeResponse = FoursquareOAuth.getAuthCodeFromResult(resultCode, data);        
        if(codeResponse != null && codeResponse.getException() == null) {
          authCode = codeResponse.getCode();
          Intent intent = FoursquareOAuth.getTokenExchangeIntent(context, CLIENT_ID, CLIENT_SECRET, authCode);
          startActivityForResult(intent, REQUEST_CODE_FSQ_TOKEN_EXCHANGE);
          Log.i("AuthCode", "" + authCode);
        } else {
          Log.e("AuthCodeResponse", codeResponse.getException().getMessage() + ":" + authCode);
        }
      break;
     
      case REQUEST_CODE_FSQ_TOKEN_EXCHANGE:
        AccessTokenResponse tokenResponse = FoursquareOAuth.getTokenFromResult(resultCode, data);
        if(tokenResponse != null && tokenResponse.getException() == null) {
          accessToken = tokenResponse.getAccessToken();
          Log.i("AccessToken", "" + accessToken);
        } else {
          Log.e("AccessTokenResponse", tokenResponse.getException().getMessage() + ":" + accessToken);
        }
        break;
    }

  }
  
  
  @Override
  public void onClick(View v) {
    
    PersistantData persistantData = 
      new PersistantData(getApplicationContext());
    
    persistantData.setDisplayName(displayName.getText().toString());
    if(gender.getCheckedRadioButtonId() == R.id.male) {
      persistantData.setGender("Male");
    } else {
      persistantData.setGender("Female");
    }
    persistantData.setHHI(hhi.getSelectedItem().toString());
    persistantData.setAge(age.getProgress()+10);
  
//    Log.i("Saved info", displayName.getText().toString()
//      + ", " + (gender.getCheckedRadioButtonId() == R.id.male ? "male" : "female")
//      + ", " + hhi.getSelectedItem().toString()
//      + ", " + (age.getProgress()+10));

    progDailog = new ProgressDialog(this);
    progDailog.setMessage("Fetching you the best food around..");
    progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    progDailog.setCancelable(false);
    progDailog.setCanceledOnTouchOutside(false);
    progDailog.show(); 
    new NetworkOperation().execute();
    
  }
  
  private class NetworkLocationListener implements 
      LocationListener {

    @Override
    public void onLocationChanged(Location loc) {
      Log.i("Location", loc.getLatitude() + ", " + loc.getLongitude());
      location = loc;
      signIn.setEnabled(true);
      lManager.removeUpdates(networkListener);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}

    @Override
    public void onProviderEnabled(String provider) {}

    @Override
    public void onProviderDisabled(String provider) {}
      
  }
  
  private class NetworkOperation extends AsyncTask<Object, Object, Object> {

    Intent intent;
    boolean nError;
    StringBuilder path = new StringBuilder(
      "https://api.foursquare.com/v2/venues/search?client_id"
      + "=34SHI3D0MYXRMBENDLBUAX03M5ZD3L1MIMLF0DK0RKVMSWFD&"
      + "client_secret=2CGG25UP03QZ4RF425LDYUYYUJZHTSKHDBZ1DC3X"
      + "JVS1E3LN&v=20140511&ll=");

    
    @Override
    protected Object doInBackground(Object... params) {
      try {
        path.append(location.getLatitude() + "," + location.getLongitude());
        HttpClient client     = new DefaultHttpClient();
        HttpGet get           = new HttpGet(path.toString());
        HttpResponse response = client.execute(get);
        String result         = EntityUtils.toString(response.getEntity());
            
        intent = new Intent(context, Suggestion.class);
        intent.putExtra("status", response.getStatusLine().getStatusCode());
        intent.putExtra("result", result);
        //Log.i("NetworkResult", result);
            
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
      
      progDailog.dismiss();
      if(!nError) {
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplicationContext().startActivity(intent);
      }
      
    }  
    
  }

}