package in.mubble.mubbletest;

import in.mubble.util.core.ULog;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.ComponentName;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.net.TrafficStats;
import android.os.Environment;

public class MubbleUtil {

  public static void writeToFile(String sFileName, String text) {
    try {
      File root = new File(Environment.getExternalStorageDirectory(), "MubbleTest");
      if (!root.exists()) {
          root.mkdirs();
      }
      File gpxfile = new File(root, sFileName);
      FileWriter writer = new FileWriter(gpxfile);
      writer.append(text);
      writer.flush();
      writer.close();
      ULog.v("Write to file", sFileName + " : " + text);
    }
    catch(IOException e) {
      e.printStackTrace();
    }
  }
  
  public static JSONObject getInstalledApplicationsInfo() {
    
    JSONObject installedApplications = new JSONObject();
    PackageManager packageManager = MainActivity.context.getPackageManager();
    List<ApplicationInfo> packageList = new ArrayList<ApplicationInfo>();
    try {
      packageList.add(packageManager.getApplicationInfo("com.facebook.katana", 0));
      packageList.add(packageManager.getApplicationInfo("com.google.android.youtube", 0));
      packageList.add(packageManager.getApplicationInfo("com.google.android.apps.maps", 0));
      packageList.add(packageManager.getApplicationInfo("flipboard.app", 0));
    } catch (NameNotFoundException e1) { e1.printStackTrace();}
    
    try {
		installedApplications.put("TS", System.currentTimeMillis());
		JSONObject temp = new JSONObject();
		temp.put("DataDownload", TrafficStats.getTotalRxBytes());
		temp.put("DataUpload", TrafficStats.getTotalTxBytes());
		installedApplications.put("Total", temp);
	} catch (JSONException e1) {
		e1.printStackTrace();
	}
    
    for (ApplicationInfo applicationInfo: packageList) {
      try {
      
        long dataDownload = TrafficStats.getUidRxBytes(applicationInfo.uid),
             dataUpload   = TrafficStats.getUidTxBytes(applicationInfo.uid);
        if (dataDownload > TrafficStats.UNSUPPORTED && dataUpload > TrafficStats.UNSUPPORTED) {
        
          JSONObject app     = new JSONObject();
          
          app.put("AppName", MubbleUtil.getApplicationName(applicationInfo));
          app.put("DataDownload", dataDownload);
          app.put("DataUpload", dataUpload);
          
          installedApplications.put(applicationInfo.packageName, app);
          
        }
  
      } catch (JSONException e) {
        ULog.v("Device::getInstalledApplicationsInfo",
          e.getClass().getName() + ":" + e.getMessage());
      }
    }
    
    return installedApplications;
  
  }
  
  public static boolean isThirdParty(ApplicationInfo info) {

    boolean
      system  = (info.flags & ApplicationInfo.FLAG_SYSTEM) == 1,
      systemUpdated = (info.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP)
                        == 1;
    return !(system || systemUpdated);
    
  }

  public static String getApplicationName(ApplicationInfo info) {

    PackageManager pm = MainActivity.context.getPackageManager();
    String appName = null;

    try {
      appName = pm.getApplicationLabel(info).toString();
    } catch (Resources.NotFoundException e) {
      appName = info.packageName;
    }

    return appName;

  }

  public static String getApplicationName(String packageName) {

    PackageManager pm = MainActivity.context.getPackageManager();
    ApplicationInfo appInfo = null;

    try {
      appInfo = pm.getApplicationInfo(packageName,
      PackageManager.GET_META_DATA);
    } catch (NameNotFoundException e) {
      ULog.v("MubbleUtil::getApplicationName", e.getClass().getName()
        + ":" + e.getMessage());
    }

    if (appInfo != null) {
      return getApplicationName(appInfo);
    }

    return packageName;

  }

  public static String getApplicationName(RunningAppProcessInfo info) {
    return getApplicationName(info.processName);
  }

  public static String getApplicationName(ComponentName cName) {
    return getApplicationName(cName.getPackageName());
  }

}