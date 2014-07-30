package in.mubble.mubbletest;

import in.mubble.callsmstojson.R;
import in.mubble.util.MU;

import org.json.JSONArray;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
public class MainActivity extends Activity {

  public static Context context;
  Button start, stop;
  TextView text;
  
  public static JSONArray allData = new JSONArray();
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    context = getApplicationContext();
    text = (TextView) findViewById(R.id.textView1);
    MU.init(getApplicationContext());

    ReflectionDump.testForSingleSim(getSystemService(Context.TELEPHONY_SERVICE));
  }

}


/*
      StringBuilder sb = new StringBuilder("DualSimGuru\n");
    
    sb.append("\nDualSim: "          + USimGuru.isDeviceDualSim());

    sb.append("\nRoaming: "          + USimGuru.isRoaming(USimSlot.SINGLE));
    sb.append("\nRoaming1: "         + USimGuru.isRoaming(USimSlot.FIRST));
    sb.append("\nRoaming2: "         + USimGuru.isRoaming(USimSlot.SECOND));
    
    sb.append("\nSimSerial: "        + USimGuru.getSerial(USimSlot.SINGLE));
    sb.append("\nSimSerialNumber1: " + USimGuru.getSerial(USimSlot.FIRST));
    sb.append("\nSimSerialNumber2: " + USimGuru.getSerial(USimSlot.SECOND));
    
    sb.append("\nDataState: "        + USimGuru.getDataState(USimSlot.SINGLE));
    sb.append("\nDataState1: "       + USimGuru.getDataState(USimSlot.FIRST));
    sb.append("\nDataState2: "       + USimGuru.getDataState(USimSlot.SECOND));
     
    sb.append("\nDataActiveInSim: "  + USimGuru.getDataActiveSimSlot());
    
    sb.append("\nIMEI: "             + USimGuru.getImei(USimSlot.SINGLE));
    sb.append("\nIMEI 1: "           + USimGuru.getImei(USimSlot.FIRST));
    sb.append("\nIMEI 2: "           + USimGuru.getImei(USimSlot.SECOND));
    
    sb.append("\nRoaming: "          + USimGuru.isRoaming(USimSlot.SINGLE));
    sb.append("\nRoaming1: "         + USimGuru.isRoaming(USimSlot.FIRST));
    sb.append("\nRoaming2: "         + USimGuru.isRoaming(USimSlot.SECOND));


    sb.append("\nNetworkOpr: "       + USimGuru.getNetworkOpr(USimSlot.SINGLE));
    sb.append("\nNetworkOpr1: "      + USimGuru.getNetworkOpr(USimSlot.FIRST));
    sb.append("\nNetworkOpr2: "      + USimGuru.getNetworkOpr(USimSlot.SECOND));

    sb.append("\nSimOperator: "      + USimGuru.getSimOpr(USimSlot.SINGLE));
    sb.append("\nSimOperator1: "     + USimGuru.getSimOpr(USimSlot.FIRST));
    sb.append("\nSimOperator2: "     + USimGuru.getSimOpr(USimSlot.SECOND));
    
    text.setText(sb.toString());  
    
    TableDump.dumpTable(android.provider.CallULog.Calls.CONTENT_URI);
  
    ULog.v("hah", "a:" + USimGuru.isDeviceDualSim());
    
    ReflectionDump.testForSingleSim(USimGuru.getTelephonyManager());
    ReflectionDump.testForDualSim(USimGuru.getTelephonyManager());
    StringBuilder sb = new StringBuilder("DualSimGuru\n");

    
    text.setText(sb.toString());
  public void initQualcommDoubleSim () {
   try {
   Class <?> cx = Class.forName ("android.telephony.MSimTelephonyManager");
   Object obj = MU.context.getSystemService("phone_msim");
   
   Method mx = cx.getMethod ("getDeviceId", int.class);

   ULog.v ("Objects", "" + cx + ":" + obj);
   String serial1 = (String) mx.invoke (obj, 0),
          serial2 = (String) mx.invoke (obj, 1);
   ULog.v ("SerialNumbers", serial1 + ", " + serial2);
   
   
   } catch (Exception e) {
     ULog.v("SerialNumbers ", e.getMessage());
     e.printStackTrace();
   }
 } 
 
 ULog.v("This is the stupid text", UMisc.toMD5("This is the stupid text"));
  
    UCipher cipher = new UCipher("123456789");
    String encrypted = cipher.encrypt("{png: \"this is it\"}");
    String decrypted = cipher.decrypt(encrypted);

  GSONTest test = new GSONTest("hahaha");
    Gson gson     = new Gson();

    String json     = gson.toJson(test);
    ULog.v("json", json);
    GSONTest result = gson.fromJson(json, GSONTest.class);
    ULog.v("result", result.toString());
  
  text = (TextView) findViewById(R.id.textView1);
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
   ULog.v("Exception", e.getMessage());
   e.printStackTrace();
 } catch (InstantiationException e) {
   ULog.v("Exception", e.getMessage());
   e.printStackTrace();
 } catch (IllegalAccessException e) {
   ULog.v("Exception", e.getMessage());
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
        ULog.v("DataMonitor", "Starting");
        startMonitorDataUsage();
      }
    });

    stop.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        ULog.v("DataMonitor", "Stopping");
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
        ULog.i("tableName: ", c.getString(0));
        c.moveToNext();
      }
      ULog.i("Number of tables", "" + c.getCount());
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
            ULog.i("hello", "how are you" + inet.toString());
            //ReflectionDump.testForSingleSim(inet);
          }
        ULog.v("aa gaya", "tha");
        for(Enumeration<NetworkInterface> inets = intf.getSubInterfaces(); inets.hasMoreElements();){
          ULog.v("andar", "tha");
          NetworkInterface inet = inets.nextElement();
          ReflectionDump.testForSingleSim(inet);
        }
        
        ULog.v("aa gaya", "tha");
        for(InterfaceAddress inet : intf.getInterfaceAddresses()){
          ULog.v("andar", "tha");
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
      ULog.v("MainActivity", e.getMessage());
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