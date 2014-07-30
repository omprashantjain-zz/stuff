package in.mubble.mubbletest;

import in.mubble.util.core.ULog;

import java.lang.reflect.Method;

public class ReflectionDump {

  public static void testForSingleSim(Object object) {
    StringBuilder sb = new StringBuilder("SingleSim" + " Methods for " + object.getClass().getName());
    
    Method[] methods = object.getClass().getDeclaredMethods();
    Object obPhone;
   
    for(Method m: methods) {
      String methodName = m.getName();
      try {    
        Method gemini = object.getClass()
          .getMethod(methodName);
      
        obPhone = gemini.invoke(object);
        if(obPhone != null) {
          sb.append("\n" +  m.getName() + " : " 
              + (obPhone == null ? "null" : obPhone.toString()));
        }
    
      } catch (Exception e) {
        //e.printStackTrace();
        //throw new GeminiMethodNotFoundException("hello");
      }  
    }
    MubbleUtil.writeToFile("SingleSim.txt", sb.toString());  
  }
  
  public static void testForDualSim(Object object) {
    StringBuilder sb = new StringBuilder("DualSim" + " Methods for " + object.getClass().getName());
  
    Method[] methods = object.getClass().getDeclaredMethods();
    Class<?>[] parameter = new Class[1];
    parameter[0] = int.class;
    Object obPhone;
    Object[] obParameter = new Object[1];
    
    for(Method m: methods) {
      String methodName = m.getName();
      try {
        Method gemini;
    	try {
          gemini = object.getClass()
            .getMethod(methodName + "Gemini", parameter);
    	} catch (NoSuchMethodException e) {
          //sb.append("\nError " + e.getMessage());
    	  gemini =  object.getClass()
            .getMethod(methodName, parameter);	
    	}
    	
        obParameter[0] = 0;
        obPhone = gemini.invoke(object, obParameter);
        if(obPhone != null) {
          sb.append("\n" +  m.getName() + " with argument 0" + ":" 
            + (obPhone == null ? "null" : obPhone.toString())); 
        }
      
        obParameter[0] = 1;
        obPhone = gemini.invoke(object, obParameter);
        if(obPhone != null) {
          sb.append("\n" + m.getName() + " with argument 1" + ":" 
            + (obPhone == null ? "null" : obPhone.toString()));  
        }
    
      } catch (Exception e) {
        e.printStackTrace();
        //sb.append("\nError " + e.getMessage());
        //throw new GeminiMethodNotFoundException("hello");
      }
      
    }
    ULog.i("Reached end", "aha");
    MubbleUtil.writeToFile("DualSim.txt", sb.toString());
  }
}