package in.mubble.mubbletest;

import android.annotation.TargetApi;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
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
        
        int type = cursor.getType(i);
        switch(type) {
          case Cursor.FIELD_TYPE_BLOB: 
            sb.append(cursor.getBlob(i) + ",");
            break;

          case Cursor.FIELD_TYPE_FLOAT: 
            sb.append(cursor.getFloat(i) + ",");
            break;

          case Cursor.FIELD_TYPE_INTEGER: 
            sb.append(cursor.getInt(i) + ",");
            break;

          case Cursor.FIELD_TYPE_STRING:   
            sb.append(cursor.getString(i) + ",");
            break;

          default: 
            sb.append(",");
            break;
        }
        
      }
      sb.append("\n");
      cursor.moveToNext();
    }
    
    MubbleUtil.writeToFile(file, sb.toString());
  }
}
