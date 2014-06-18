package in.mubble.mubbletest;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import android.os.Environment;
import android.util.Log;

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
      Log.e("Write to file", sFileName + " : " + text);
    }
    catch(IOException e) {
      e.printStackTrace();
    }
  }
}