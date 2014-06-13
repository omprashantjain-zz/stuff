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

import com.woodbug.chatora.data.PersistantData;

public class RegisterActivity extends ActionBarActivity implements OnClickListener{

  LinearLayout layout;
  EditText displayName;
  RadioGroup gender;
  SeekBar age;
  TextView ageView;
  Spinner hhi;
  Button signIn;
  Context context;
  ProgressDialog progDialogPlaces;
  String location = null;
  PersistantData persistantData;
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    
    context = getApplicationContext();
    persistantData = new PersistantData(getApplicationContext());
    location = persistantData.getLoation();
    
    if(persistantData.isChatoraRegistered()) {
      goToPlaces();	
    } else {
    	
      setContentView(R.layout.activity_register);
   
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
      
      displayName.setText(persistantData.getDisplayName());
      if(persistantData.getGender().equals("male")) {
        gender.check(R.id.male);
      } else {
        gender.check(R.id.female);
      }
    
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
  }
 
  @Override
  public void onClick(View v) {
	  
    persistantData.setDisplayName(displayName.getText().toString());
    if(gender.getCheckedRadioButtonId() == R.id.male) {
      persistantData.setGender("male");
    } else {
      persistantData.setGender("female");
    }
    persistantData.setHHI(hhi.getSelectedItem().toString());
    persistantData.setAge(age.getProgress()+10);
    persistantData.setChatoraRegistered();
    goToPlaces();

  }

  public void goToPlaces() {

    progDialogPlaces = new ProgressDialog(this);
    progDialogPlaces.setMessage("Fetching you the best food around..");
    progDialogPlaces.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    progDialogPlaces.setCancelable(false);
    progDialogPlaces.setCanceledOnTouchOutside(false);
    progDialogPlaces.show(); 
    new PlacesList().execute();
	    
  }  
 
  private class PlacesList extends AsyncTask<Object, Object, Object> {

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
        path.append(location);
        HttpClient client     = new DefaultHttpClient();
        HttpGet get           = new HttpGet(path.toString());
        HttpResponse response = client.execute(get);
        String result         = EntityUtils.toString(response.getEntity());
            
        intent = new Intent(context, SuggestionActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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
      
      progDialogPlaces.dismiss();
      if(!nError) {
        getApplicationContext().startActivity(intent);
      }
      
    }  
    
  }

}