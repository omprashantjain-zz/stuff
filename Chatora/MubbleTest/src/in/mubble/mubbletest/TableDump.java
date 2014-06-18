package in.mubble.mubbletest;

import android.database.Cursor;
import android.net.Uri;

public class TableDump {

  private static String file = "TableDump.csv";
  public static void dumpTable(Uri uri) {
    StringBuilder sb = new StringBuilder("Table dump of " + uri.getPath() + ": \n");
    
    Cursor cursor = MainActivity.context.getContentResolver()
      .query(uri, null, null, null, null);
    
    cursor.moveToFirst();
    int noOfColumns = cursor.getColumnCount();
    for(int i = 0; i < noOfColumns ; i++) {
      sb.append(cursor.getColumnName(i) + ",");
    }
    sb.append("\n");
    
    while(!cursor.isAfterLast()) {
      for(int i = 0; i < noOfColumns; i++) {
        sb.append(cursor.getBlob(i) + ",");
      }
      sb.append("\n");
      cursor.moveToNext();
    }
    
    MubbleUtil.writeToFile(file, sb.toString());
  }
}
