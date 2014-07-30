package in.mubble.mubbletest;

import in.mubble.util.core.ULog;

import java.util.HashMap;

import android.annotation.SuppressLint;

@SuppressLint("UseSparseArrays")
@SuppressWarnings("unused")
public class GSONTest {

  private HashMap<Integer, B> myMap;
  private String extra;
  
  private GSONTest() {
    ULog.v("GSONTest", "default constructor GSONTEST was called");
  }
  
  public GSONTest(String extra) {
    myMap      = new HashMap<Integer, B>(); 
    this.extra = extra;
    myMap.put(1, new B(7));
    ULog.v("GSONTest", "parameter constructor GSONTEST was called");
  }

  private static class B {
    int child;
    private B() {
      ULog.v("B", "default constructor B was called");
    }
    
    private B(int child) {
      ULog.v("B", "parameter constructor B was called");
      this.child = child;  
    }
  }
  
  public String toString() {
    return this.myMap.size() + ": " + this.extra + ", " + this.myMap.get(1).child;
  }
  
}
