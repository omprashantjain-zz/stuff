package in.mubble.mubbletest;

import in.mubble.callsmstojson.R;

import org.json.JSONArray;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class MainActivity extends ActionBarActivity {

  public static Context context;
  Button start, stop;
  TextView text;
  
  public static JSONArray allData = new JSONArray();
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    context = getApplicationContext();
    
    GSONTest test = new GSONTest("hahaha");
    Gson gson     = new Gson();

    String json     = gson.toJson(test);
    Log.e("json", json);
    GSONTest result = gson.fromJson(json, GSONTest.class);
    Log.e("result", result.toString());
  
  }
}


/* text = (TextView) findViewById(R.id.textView1);
 StringBuilder sb = new StringBuilder("DualSimGuru\n");
 
 sb.append("\nSimSerialNumber1: " + DualSimGuru.getSerialSim1());
 sb.append("\nSimSerialNumber2: " + DualSimGuru.getSerialSim2());
 sb.append("\nSimNetwork1: "      + DualSimGuru.getNetworkSim1());
 sb.append("\nSimNetwork2: "      + DualSimGuru.getNetworkSim2());
 sb.append("\nSimOperator1: "     + DualSimGuru.getOperatorSim1());
 sb.append("\nSimOperator2: "     + DualSimGuru.getOperatorSim2());
 sb.append("\nDataActiveInSim: "     + DualSimGuru.getDataActiveSimId());
 
 text.setText(sb.toString());
 
 try {
   Class<?> clazz = ClassLoader.getSystemClassLoader().loadClass("android.provider.Telephony.SIMInfo");
   ReflectionDump.testForSingleSim(clazz.newInstance());
   ReflectionDump.testForDualSim(clazz.newInstance());
   
 } catch (ClassNotFoundException e) {
   Log.e("Exception", e.getMessage());
   e.printStackTrace();
 } catch (InstantiationException e) {
   Log.e("Exception", e.getMessage());
   e.printStackTrace();
 } catch (IllegalAccessException e) {
   Log.e("Exception", e.getMessage());
   e.printStackTrace();
 }*/



  /*    
    TelephonyManager tm = (TelephonyManager)getApplicationContext()
          .getSystemService(Context.TELEPHONY_SERVICE);
    ReflectionDump.testForSingleSim(tm);
    ReflectionDump.testForDualSim(tm);
    
    start = (Button)findViewById(R.id.button1);
    stop  = (Button)findViewById(R.id.button2);
    
    start.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        Log.e("DataMonitor", "Starting");
        startMonitorDataUsage();
      }
    });

    stop.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        Log.e("DataMonitor", "Stopping");
        stopMonitorDataUsage();
        MubbleUtil.writeToFile("AppWiseDataUsed.txt", allData.toString());
      }
    });
    
  public void startMonitorDataUsage() {
    
    AlarmManager am = (AlarmManager)
      getSystemService(Context.ALARM_SERVICE);
    Intent intentSeldom = new Intent(EventReceiver.ACTION_LOG_DATA_USAGE, null,
      getApplicationContext(), EventReceiver.class);
    PendingIntent pIntent = PendingIntent.getBroadcast(
      getApplicationContext(), 0, intentSeldom, 0);
    am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),
      1000, pIntent);
  
  }
  
  
  public void stopMonitorDataUsage() {
   
    AlarmManager am = (AlarmManager)
      getSystemService(Context.ALARM_SERVICE);
    Intent intentSeldom = new Intent(EventReceiver.ACTION_LOG_DATA_USAGE, null,
      getApplicationContext(), EventReceiver.class);
    PendingIntent pIntent = PendingIntent.getBroadcast(
      getApplicationContext(), 0, intentSeldom, 0);
    am.cancel(pIntent);
      
  }
    
    String encodedHash = Uri.encode("#");
    String ussd = "*121" + encodedHash;
    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + ussd));
    // this seems to be working for samsung as everyone says but couldn't test
    intent.putExtra("simSlot", 0);
    intent.putExtra("com.android.phone.extra.slot", 0);
    startActivity(intent);
    
    
    MubbleSQLiteHelper dbHelper;
    try {
      dbHelper = MubbleSQLiteHelper.getInstance(this.
      createPackageContext("in.mubble.billbytwo", Context.CONTEXT_INCLUDE_CODE));
      SQLiteDatabase database = dbHelper.getWritableDatabase();
      Cursor c = database.rawQuery("SELECT name FROM sqlite_master WHERE type='table';", null);  
      c.moveToFirst();
      while(!c.isAfterLast()) {
        Log.i("tableName: ", c.getString(0));
        c.moveToNext();
      }
      Log.i("Number of tables", "" + c.getCount());
    } catch (NameNotFoundException e1) {}
    
    // For dumping the table
     TableDump.dumpTable(Uri.parse("content://telephony/carriers"));
   
    // For dumping the current NetworkInterface Objects
    try {
      for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
        NetworkInterface intf = en.nextElement();
        
        if(intf.isUp()) {
          //ReflectionDump.testForSingleSim(intf);
        
          for(Enumeration<InetAddress> inets = intf.getInetAddresses(); inets.hasMoreElements();){
            InetAddress inet = inets.nextElement();
            Log.i("hello", "how are you" + inet.toString());
            //ReflectionDump.testForSingleSim(inet);
          }
        Log.e("aa gaya", "tha");
        for(Enumeration<NetworkInterface> inets = intf.getSubInterfaces(); inets.hasMoreElements();){
          Log.e("andar", "tha");
          NetworkInterface inet = inets.nextElement();
          ReflectionDump.testForSingleSim(inet);
        }
        
        Log.e("aa gaya", "tha");
        for(InterfaceAddress inet : intf.getInterfaceAddresses()){
          Log.e("andar", "tha");
          ReflectionDump.testForSingleSim(inet);
        }
        
          //ReflectionDump.testForDualSim(intf);
        }
      }  
    
     Object ob = MainActivity.context.getSystemService("phone_msim");
     ReflectionDump.testForDualSim(ob);
    
    TelephonyManager tm = (TelephonyManager)getApplicationContext()
          .getSystemService(Context.TELEPHONY_SERVICE);
    ReflectionDump.testForSingleSim(tm);
    
    
    initSpreadDoubleSim();
    TableDump.dumpTable(Uri.parse("content://sms/"));
    } catch(Exception e) {
      Log.e("MainActivity", e.getMessage());
      Toast.makeText(getApplicationContext(), "Exception in main activity" + e.getMessage(),
        Toast.LENGTH_LONG).show();
    }
    
    
  private void initSpreadDoubleSim () {
    StringBuilder sb = new StringBuilder();
    try {
      Class <?> c = Class.forName("com.android.internal.telephony.PhoneFactory");
      Method m = c.getMethod ("getServiceName", String.class, int.class);
      String spreadTmService = (String)m.invoke(c, Context.TELEPHONY_SERVICE, 1);

      TelephonyManager tm = (TelephonyManager)getApplicationContext().getSystemService (Context.TELEPHONY_SERVICE);
      imsi_1 = tm.getSubscriberId ();
      imei_1 = tm.getDeviceId ();
      phoneType_1 = tm.getPhoneType ();
      TelephonyManager tm1 = (TelephonyManager)getApplicationContext().getSystemService (spreadTmService);
        imsi_2 = tm1.getSubscriberId ();
        imei_2 = tm1.getDeviceId ();
        phoneType_2 = tm1.getPhoneType ();
        if (TextUtils.isEmpty (imsi_1) && (! TextUtils.isEmpty (imsi_2))) {
          defaultImsi = imsi_2;
        }
        if (TextUtils.isEmpty (imsi_2) && (! TextUtils.isEmpty (imsi_1))) {
         defaultImsi = imsi_1;
        }
      sb.append("Sim1 data state: " + tm.getDataState());
      sb.append("Sim2 data state: " + tm1.getDataState());

    } catch (Exception e) {
      sb.append("Phone is not dual sim: " + e.getMessage());
      MubbleUtil.writeToFile("SpreadTrum.txt", sb.toString());
      return;
    }
    sb.append("Phone is dual sim");
    MubbleUtil.writeToFile("SpreadTrum.txt", sb.toString());
  }*/