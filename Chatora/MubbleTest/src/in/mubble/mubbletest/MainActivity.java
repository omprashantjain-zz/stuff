package in.mubble.mubbletest;

import in.mubble.callsmstojson.R;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class MainActivity extends ActionBarActivity {

  public static Context context;
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    context = getApplicationContext();
    
    // For dumping the table
    // TableDump.dumpTable(Uri.parse("content://telephony/carriers"));
   
    // For dumping the current NetworkInterface Objects
    try {
      for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
        NetworkInterface intf = en.nextElement();
        
        if(intf.isUp()) {
          //ReflectionDump.testForSingleSim(intf);
        
          for(Enumeration<InetAddress> inets = intf.getInetAddresses(); inets.hasMoreElements();){
            InetAddress inet = inets.nextElement();
            Log.i("hello", "how are you");
            Log.i("hello", "how are you" + inet.toString());
            //ReflectionDump.testForSingleSim(inet);
          }
        /*Log.e("aa gaya", "tha");
        for(Enumeration<NetworkInterface> inets = intf.getSubInterfaces(); inets.hasMoreElements();){
          Log.e("andar", "tha");
          NetworkInterface inet = inets.nextElement();
          ReflectionDump.testForSingleSim(inet);
        }*/
        	
        /*Log.e("aa gaya", "tha");
        for(InterfaceAddress inet : intf.getInterfaceAddresses()){
          Log.e("andar", "tha");
          ReflectionDump.testForSingleSim(inet);
        }
        */
          //ReflectionDump.testForDualSim(intf);
        }
      }  
    } catch(Exception e){e.printStackTrace();}
    
  
  }  
}