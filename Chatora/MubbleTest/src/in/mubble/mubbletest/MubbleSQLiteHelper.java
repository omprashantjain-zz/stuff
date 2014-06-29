/*This is the class that is responsible for the presence and managing the database
 * this will also provide us the database object whenever we want to do some operations.*/

package in.mubble.mubbletest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MubbleSQLiteHelper extends SQLiteOpenHelper {

  private static MubbleSQLiteHelper uHelper;
  private static final String DATABASE_NAME = "billbytwo.db";
  private static final int DATABASE_VERSION = 1;

  public static MubbleSQLiteHelper getInstance(Context context) {
    if(uHelper == null) {
      uHelper = new MubbleSQLiteHelper(context);
    }
    return uHelper;
  }
  
  private MubbleSQLiteHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase database) {
  Log.w(MubbleSQLiteHelper.class.getName(), "creating");
		      
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    
    Log.w(MubbleSQLiteHelper.class.getName(),
      "Upgrading database from version " + oldVersion + " to "
      + newVersion + ", which will destroy all old data");
    onCreate(db);
    
  }

} 