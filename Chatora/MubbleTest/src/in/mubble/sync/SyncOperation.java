package in.mubble.sync;

import in.mubble.mubbletest.MainActivity;
import in.mubble.util.core.UAsyncTask;
import in.mubble.util.core.ULog;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.Callable;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.util.Log;

public class SyncOperation {

  public static void sendData() {
	
    UAsyncTask.runTask(new Callable<Object>() {
      
      @Override
      public Object call() throws Exception {
        // if no rows are present then returning.
        // if(GlobalApp.logData.rowCount() == 0) return null;
        
        try {
          ULog.i("SendData", "sending data......");
          HttpResponse response;
          HttpClient client = new DefaultHttpClient();
     
          //List<JSONObject> logsToSend = GlobalApp.logData.getLogsToSend();
          
          JSONObject data = new JSONObject();
          //data.put("LogArray", logsToSend);
          //Log.i("data", data.toString());
          
          HttpPost post = new HttpPost("http://10.1.1.110:8000/api/DESTINATION_SYNC");
          StringEntity se = new StringEntity(data.toString());
          se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
            "application/json"));
          
          post.setHeader("Accept", "application/json");
          post.setHeader("Content-type", "application/json");
          post.setHeader("mu_app_id", "BILLBYTWO");
          post.setHeader("mu_version", MainActivity.context.getPackageManager()
		    .getPackageInfo(MainActivity.context.getPackageName(), 0).versionName);
          post.setEntity(se);

          response = client.execute(post);
          
          int statusCode = response.getStatusLine().getStatusCode();
          
          if (statusCode == 200) {
          
            //GlobalApp.logData.deleteSentLogs();
            String result = EntityUtils.toString(response.getEntity());
            ULog.i("DataSent", "Response: " + result.length() + ": " + result);
            
            //State.getInstance().setLastSyncTime(System.currentTimeMillis());   
            
          } else {
            //State.getInstance().increaseSyncFailedCount();
            ULog.w("ServerError", "Error code: " + statusCode);  
          }
          
        } catch (UnsupportedEncodingException e) {
          //State.getInstance().increaseSyncFailedCount();
          Log.w("SyncOperation", e.getClass().getName() + ":" + e.getMessage());
          return false;
        
        } catch (ClientProtocolException e) {
          //State.getInstance().increaseSyncFailedCount();
          Log.w("SyncOperation", e.getClass().getName() + ":" + e.getMessage());
          return false;
        
        } catch (IOException e) {
          //State.getInstance().increaseSyncFailedCount();
          Log.w("SyncOperation", e.getClass().getName() + ":" + e.getMessage());
          return false;
        }
        
        return true;
      }
    });
    
  }
  
}