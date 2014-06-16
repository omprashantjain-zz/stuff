package com.woodbug.chatora;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class GlobalApp {

  public static Context context;
  private static boolean initDone = false;
  
  public static void init(Context activity) {
    if(initDone) return;
    
    initDone = true;
    GlobalApp.context = activity;
    //setAlarm();
    
  }
  
  public static void setAlarm() {
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(System.currentTimeMillis());
    calendar.set(Calendar.HOUR_OF_DAY, 13);
    calendar.set(Calendar.MINUTE, 30);

    AlarmManager alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
    Intent intent = new Intent(context, EventReciever.class);
    intent.setAction("MubbleAlarm");
    PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
    alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
      (((long)24)*60*60*1000) , alarmIntent);
    Log.i("globalapp init", "Done");
    
  }
}
