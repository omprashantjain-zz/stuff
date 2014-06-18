package in.mubble.mubbletest;

import org.json.JSONException;
import org.json.JSONObject;

import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.util.Log;

public class MubbleContentObserver {

  public static final Uri callUri = android.provider.CallLog.Calls.CONTENT_URI;
  public static final Uri smsUri = Uri.parse("content://sms/");
  JSONObject global = new JSONObject();
  
  public void logCallLog() {
    JSONObject main = new JSONObject();
    String columns[] = new String[] {
      CallLog.Calls._ID, 
      CallLog.Calls.NUMBER, 
      CallLog.Calls.CACHED_NAME, 
      CallLog.Calls.DATE, 
      CallLog.Calls.DURATION, 
      CallLog.Calls.TYPE };
    
    //last record first
    Cursor c = MainActivity.context.getContentResolver()
      .query(callUri, columns, null, null, CallLog.Calls._ID + " DESC");
    
    int indexNumber   = c.getColumnIndex(CallLog.Calls.NUMBER),
        indexDuration = c.getColumnIndex(CallLog.Calls.DURATION),
        indexType     = c.getColumnIndex(CallLog.Calls.TYPE),
        indexName     = c.getColumnIndex(CallLog.Calls.CACHED_NAME),
        indexDate     = c.getColumnIndex(CallLog.Calls.DATE);
        
    c.moveToFirst();
    while (!c.isAfterLast()) {
      try {     
        JSONObject json = new JSONObject();
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
        main.put(""+c.getLong(indexDate), json);
        // todo: make sure that this time is in GMT

      } catch (JSONException e) {
        Log.d("CallLog",
          e.getClass().getName() + ":" + e.getMessage());    
      }
      
      c.moveToNext();
    }
    Log.e("CallLog", main.toString());
    try {
      global.put("CallLog", main);
    } catch (JSONException e) {
      e.printStackTrace();
    }
    c.close();
  }
  

  /********************* Called on new sms entry **************************/
  public void logSmsLog() {
  
    JSONObject main = new JSONObject();
    
    String columns[] = new String[] { "_id",
                                      "address",
                                      "thread_id",
                                      "subject",
                                      "status",
                                      "type",
                                      "date" };
    
    //last record first
    Cursor c = MainActivity.context.getContentResolver()
      .query(smsUri, columns, null, null, "_id DESC");

    int indexAddress  = c.getColumnIndex("address"),
        indexThreadId = c.getColumnIndex("thread_id"),
        indexSubject  = c.getColumnIndex("subject"),
    //    indexBody     = c.getColumnIndex("body"),
        indexStatus   = c.getColumnIndex("status"),
        indexType     = c.getColumnIndex("type"),
        indexDate     = c.getColumnIndex("date");
 
    c.moveToFirst();
    while (!c.isAfterLast()) {
      JSONObject json = new JSONObject();  
      try {
        String number  = c.getString(indexAddress),
               subject = c.getString(indexSubject),
               type    = null,
               status  = null;
        int threadId   = c.getInt(indexThreadId);
        
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
        main.put("" + c.getLong(indexDate), json);
        
      } catch (JSONException e) {
        Log.e("SMSLog", e.getClass().getName() + ":" + e.getMessage());
      }
      c.moveToNext();
    }  
    Log.i("SmsLog", main.toString());
    try {
      global.put("SmsLog", main);
    } catch (JSONException e) {
      e.printStackTrace();
    }
    c.close();
  }
}