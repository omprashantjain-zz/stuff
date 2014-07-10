package in.mubble.mubbletest;

import java.lang.reflect.Method;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;

public class DualSimGuru {

  private static TelephonyManager normalTM, spreadTrumTM;
  
  public static boolean isDeviceDualSim() {
    return getSerialSim2() != null;
  }

  public static int getDataActiveSimId() {
    if(getDataStateSim1()) return 0;
    else if(getDataStateSim2()) return 1;
    else return -1;
  }
  
  public static String getNetworkSim1() {
    return getTelephonyManager().getNetworkOperator();
  }
  
  public static String getSerialSim1() {
    return getTelephonyManager().getSimSerialNumber();
  }

  public static String getOperatorSim1() {
    return getTelephonyManager().getSimOperator();
  }
  
  public static String getNetworkSim2() {
    Object result = executeForSecondSim(getTelephonyManager(), "getNetworkOperator");
    
    return result != null ? result.toString() : null;
  }

  public static String getSerialSim2() {
    Object result = executeForSecondSim(getTelephonyManager(), "getSimSerialNumber");
    
    return result != null ? result.toString() : null;
  }

  public static String getOperatorSim2() {
    Object result = executeForSecondSim(getTelephonyManager(), "getSimOperator");
    
    return result != null ? result.toString() : null;
  }

  /***************************** Private Methods ****************************/
  private static boolean getDataStateSim1() {
    
    if(isDeviceDualSim()) {
      Object result = executeForFirstSim(getTelephonyManager(), "getDataState");
      
      if(result != null) Log.e("FirstState", ""+result.toString());
      return (result != null && ((Integer)result == 1 || (Integer)result == 2));
  
    } else {
     int state = getTelephonyManager().getDataState();
     return (state == 1 || state == 2);
    }
    
  }
  
  private static boolean getDataStateSim2() {
    Object result = executeForSecondSim(getTelephonyManager(), "getDataState");
    
    if(result != null) Log.e("SecondState", ""+result.toString());
    return (result != null && ((Integer)result == 1 || (Integer)result == 2));
  }
  
  /**************************** Helper Methods ****************************/
  private static Object executeForFirstSim(Object object, String method) {
    return executeForReflection(object, method, 0);
  }
  
  private static Object executeForSecondSim(Object object, String method) {
    return executeForReflection(object, method, 1);
  }
  
  private static Object executeForReflection(Object object, String method, int sim) {

    Class<?>[] parameter = new Class[]  {int.class};
    Object[] obParameter = new Object[] {sim};
    
    try {
      Method gemini;
      try {
        gemini = object.getClass().getMethod(method + "Gemini", parameter);
      } catch (NoSuchMethodException e) {
        try {
          gemini =  object.getClass().getMethod(method + "Ds", parameter);
        } catch (NoSuchMethodException e1) {
          gemini =  object.getClass().getMethod(method, parameter);
        }
      }
    
      return gemini.invoke(object, obParameter);

    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
    
  }  

  private static TelephonyManager getTelephonyManager() {
    TelephonyManager spreadTrum = getSpreadTrumTM();
    
    if(spreadTrum != null) return spreadTrum;
    else return getNormalTM();
      
  }
  
  private static TelephonyManager getNormalTM() {
    if(normalTM == null) normalTM = 
     (TelephonyManager)MainActivity.context.getSystemService(Context.TELEPHONY_SERVICE);
    
    return normalTM;  
  }

  private static TelephonyManager getSpreadTrumTM() {

    if(spreadTrumTM == null) {
      try {
        Class <?> c = Class.forName("com.android.internal.telephony.PhoneFactory");
        Method m = c.getMethod ("getServiceName", String.class, int.class);
        String spreadTmService = (String)m.invoke(c, Context.TELEPHONY_SERVICE, 1);

        spreadTrumTM = (TelephonyManager)MainActivity.context.getSystemService(spreadTmService);
      } catch(Exception e) {
        e.printStackTrace();
      }
    }
    
    return spreadTrumTM;  
  }

}