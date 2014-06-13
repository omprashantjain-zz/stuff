/* ????
 * optimize the grid methods by making proper chains as lot of code is repeated
 * in overloaded methods.
 */

package com.woodbug.chatora;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.GridLayout;
import android.support.v7.widget.GridLayout.Spec;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.TextView;

public class WoodbugUtil {
  
  public static class Grid {
  
    public static TextView addTextView(Activity activity, GridLayout grid,
      int width, Spec row, Spec col) {
    
      TextView textView = new TextView(activity);
      GridLayout.LayoutParams textViewLayout = 
        new GridLayout.LayoutParams(row, col);
      textViewLayout.width = width;
      textView.setLayoutParams(textViewLayout);
      textView.setTextColor(Color.BLACK);
      grid.addView(textView, textViewLayout);      
      return textView;
    
    }
  
    public static TextView addTextView(Activity activity, GridLayout grid,
      int width, int height, Spec row, Spec col, String message) {
    
      TextView textView = addTextView
        (activity, grid, width, row, col);
      textView.setTextColor(Color.WHITE);
      textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
      textView.setText(message);
      return textView;
    
    }
  }
  
    
  public static int getScreenWidth(Activity activity) {

    // getting screen size
    DisplayMetrics displaymetrics = new DisplayMetrics();
    activity.getWindowManager().getDefaultDisplay()
      .getMetrics(displaymetrics);
    return (int)(displaymetrics.widthPixels) - 20;
      
  }
  
  public static int getScreenHeight(Activity activity) {

    // getting screen size
    DisplayMetrics displaymetrics = new DisplayMetrics();
    activity.getWindowManager().getDefaultDisplay()
      .getMetrics(displaymetrics);
    return (int)(displaymetrics.heightPixels * 0.9);
    
  }  
  
  public static int pxToDp(Context context, int px) {
    final float scale = context.getResources()
      .getDisplayMetrics().density;
    return (int) (px * scale + 0.5f);
  }

}