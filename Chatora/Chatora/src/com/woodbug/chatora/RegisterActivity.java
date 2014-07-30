package com.woodbug.chatora;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.CallLog;
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

@SuppressWarnings("unused")
public class RegisterActivity extends Activity implements OnClickListener{

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
  enum CONTENT_TYPE {
	  CALL("call"), SMS("sms");
	  public String name;

	  private CONTENT_TYPE(String s) {
	    name = s;
	  }
	}

    class MubbleContentObserver extends ContentObserver {

	  public final Uri callUri = android.provider.CallLog.Calls.CONTENT_URI;
	  public final Uri smsUri = Uri.parse("content://sms/");
	  private String contentType;
	  
	  public MubbleContentObserver(Handler handler, String contentType) {
	    super(handler);
	    this.contentType = contentType;
	  }
	  
	  public MubbleContentObserver(String contentType) {
	    this(new Handler(), contentType);
	  }
	  
	  @Override 
	  public boolean deliverSelfNotifications() {
	    return false; 
	  }
	    
	  /*********************** Called on new call entry **************************/  
	  
	public void logCallLog() {
	    
	    JSONObject json = new JSONObject();
	    long timestamp;
	    String columns[] = new String[] {
	      CallLog.Calls._ID, 
	      CallLog.Calls.NUMBER, 
	      CallLog.Calls.CACHED_NAME, 
	      CallLog.Calls.DATE, 
	      CallLog.Calls.DURATION, 
	      CallLog.Calls.TYPE };
	    
	    //last record first
	    Cursor c = GlobalApp.context.getContentResolver()
	      .query(callUri, columns, null, null, CallLog.Calls._ID + " DESC");
	    
	    int lastCallLogId = 0;
	    
	    int indexId       = c.getColumnIndex(CallLog.Calls._ID),
	        indexNumber   = c.getColumnIndex(CallLog.Calls.NUMBER),
	        indexDuration = c.getColumnIndex(CallLog.Calls.DURATION),
	        indexType     = c.getColumnIndex(CallLog.Calls.TYPE),
	        indexName     = c.getColumnIndex(CallLog.Calls.CACHED_NAME),
	        indexDate     = c.getColumnIndex(CallLog.Calls.DATE);
	        		        
	    if (c.moveToFirst() && c.getInt(indexId) > lastCallLogId) {
	      
	      try {        
	        
	        String number    = c.getString(indexNumber),
	               name      = c.getString(indexName),
	               type      = null;
	       	long   duration  = c.getLong(indexDuration);
	       	
	        //converting type to human readable type
	        int typeInt = c.getInt(indexType);
	        switch(typeInt) {
	          case 1:  type = "Incoming";
	                   break;
	          case 2:  type = "Outgoing";
	                   break;
	          case 3:  type = "Missed";
	                   break;
	          case 4:  type = "VoiceMail";
	                   break;
	          case 5:  type = "Rejected";
	                   break;
	          case 6:  type = "RefusedList";
	                   break;
	          default: type = "Other";
	                   break;
	        }
	           
	        json.put("Number", number);
	        json.put("Name", name);
	        json.put("Duration", duration);
	        json.put("Type", type);
	        
	        timestamp = c.getLong(indexDate);
	        // todo: make sure that this time is in GMT

	      } catch (JSONException e) {
	        Log.d("CallLog",
	          e.getClass().getName() + ":" + e.getMessage());    
	      }
	      
	    }
	    c.close();
	  }
	  

	  /********************* Called on new sms entry **************************/
	  public void logSmsLog() {
		  
	    JSONObject json = new JSONObject();
	    long timestamp;
	    String columns[] = new String[] { "_id",
	                                      "address",
	                                      "thread_id",
	                                      "subject",
	    //                                  "body",
	                                      "status",
	                                      "type",
	                                      "date" };
	    
	    //last record first
	    Cursor c = GlobalApp.context.getContentResolver()
	      .query(smsUri, columns, null, null, "_id DESC");

	    int indexId       = c.getColumnIndex("_id"),
	        indexAddress  = c.getColumnIndex("address"),
	        indexThreadId = c.getColumnIndex("thread_id"),
	        indexSubject  = c.getColumnIndex("subject"),
	        indexBody     = c.getColumnIndex("body"),
	        indexStatus   = c.getColumnIndex("status"),
	        indexType     = c.getColumnIndex("type"),
	        indexDate     = c.getColumnIndex("date");	 
	    if (c.moveToFirst()) {

	      try {
	        String number  = c.getString(indexAddress),
	               subject = c.getString(indexSubject),
	               body    = null,
	               type    = null,
	               status  = null;
	        int threadId   = c.getInt(indexThreadId);
	        
	        // if the number is a private number not sending the message body
	        if (!number.matches(".*\\d{10}.*")) {
	          body = c.getString(indexBody);
	        }
	        
	        //converting status to human readable status
	        int statusInt = c.getInt(indexStatus);
	        switch(statusInt) {
	          case -1: status = "NONE";
	                   break;

	          case 0 : status = "COMPLETED";
	                   break;

	          case 32: status = "PENDING";
	                   break;

	          case 64: status = "FAILED";
	                   break;
	        }
	        
	        //converting type to human readable type
	        int typeInt = c.getInt(indexType);
	        switch(typeInt) {
	          case 0: type = "Unknown";
	                  break;
	          case 1: type = "Inbox";
	                  break;
	          case 2: type = "Sent";
	                  break;
	          case 3: type = "Draft";
	                  break;
	          case 4: type = "Outbox";
	                  break;
	          case 5: type = "failed";
	                  break;
	          case 6: type = "Queued";
	                  break;
	        }
	        
	        json.put("Number", number);
	        json.put("ThreadId", threadId);
	        json.put("Subject", subject);
	        //json.put("Body", body);
	        json.put("Status", status);
	        json.put("Type", type);
	        timestamp = c.getLong(indexDate);
	        // todo: make sure that this time is in GMT
	      
	      } catch (JSONException e) {
	        Log.e("SMSLog", e.getClass().getName() + ":" + e.getMessage());
	      }
	    }
	    c.close();
	  }
	  
	  public void onChange(boolean selfChange) {
	    
	    super.onChange(selfChange);
	    Log.i("PhoneService",
	      "StringsContentObserver.onChange( " + selfChange + ")");
	    if (this.contentType.equals(CONTENT_TYPE.CALL.name)) {
	      logCallLog();
	    } else {
	      logSmsLog();
	    }
	    
	  }
    }
}
