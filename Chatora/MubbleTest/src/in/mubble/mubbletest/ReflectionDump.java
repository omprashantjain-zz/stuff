package in.mubble.mubbletest;

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
    Class<?> clazz;
    
    for(Method m: methods) {
      String methodName = m.getName();
      try {    
        Method gemini = object.getClass()
          .getMethod(methodName + "Gemini", parameter);
      
        obParameter[0] = 0;
        obPhone = gemini.invoke(object, obParameter);
        clazz = obPhone.getClass();    
        if(obPhone != null) {
          sb.append("\n" + clazz.getName() + "." + m.getName() + " with argument 0" + ":" 
            + (obPhone == null ? "null" : obPhone.toString())); 
        }
      
        obParameter[0] = 1;
        obPhone = gemini.invoke(object, obParameter);
        clazz = obPhone.getClass();    
        if(obPhone != null) {
          sb.append("\n" + clazz.getName() + "." + m.getName() + " with argument 1" + ":" 
            + (obPhone == null ? "null" : obPhone.toString()));  
        }
    
      } catch (Exception e) {
        //e.printStackTrace();
        //throw new GeminiMethodNotFoundException("hello");
      }
      
    }
    MubbleUtil.writeToFile("DualSim.txt", sb.toString());
  }
}