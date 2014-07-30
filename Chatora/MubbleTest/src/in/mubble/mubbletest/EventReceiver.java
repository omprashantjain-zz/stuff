package in.mubble.mubbletest;

import in.mubble.util.core.ULog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;

public class EventReceiver extends BroadcastReceiver {

  public static final String 
    ACTION_LOG_DATA_USAGE = "LOG-DATA-USAGE";
  
  @Override
  public void onReceive(Context context, Intent intent) {
     
    String action = intent.getAction();
    ULog.v(this.getClass().getSimpleName(), "Received " + action);

    if (action.equals(ACTION_LOG_DATA_USAGE)) {
      MainActivity.allData.put(MubbleUtil.getInstalledApplicationsInfo());
    } else if(action.equals(TelephonyManager.ACTION_PHONE_STATE_CHANGED)) {
      ULog.v("AA", "gaye");
      ULog.v("simslot: ", ""+intent.getExtras().getInt("subscription"));
      ReflectionDump.testForSingleSim(intent.getExtras());
    }
    
  }
  
}