package com.woodbug.chatora;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.GridLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class RestaurantAdapter extends ArrayAdapter<Venue> {

  Context context;
  Activity activity;
  List<Venue> venues;
  
  public RestaurantAdapter(Activity activity,
    final List<Venue> venues) {
    
    super(activity.getApplicationContext(), R.layout.venue_item, venues);
    this.activity = activity;
    this.context  = activity.getApplicationContext();
    this.venues = venues;
  }
  
  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    LayoutInflater inflater = (LayoutInflater) context
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View rowView = inflater.inflate(R.layout.venue_item, parent, false);

    int screenWidth = WoodbugUtil.getScreenWidth(this.activity)
                      - WoodbugUtil.pxToDp(activity, 6);
    
    // current Venue object
    Venue venue = venues.get(position);
	final int COLUMNS  = 2,
	          ROWS     = 2,
	          DIVISION = 3;
	  
    // setting the layout
    GridLayout grid = (GridLayout)rowView.findViewById(R.id.GridVenue);
    grid.setColumnCount(COLUMNS);
    grid.setRowCount(ROWS);
    
    // Adding the Contact Name and Message
    WoodbugUtil.Grid.addTextView(this.activity, grid, 
      2 * screenWidth / DIVISION, screenWidth / DIVISION, GridLayout.spec(0),
      GridLayout.spec(0), venue.getName());
      
    /*name.setOnClickListener(new OnClickListener() {  
      @Override
      public void onClick(View v) {
        Log.i("Clicked", "Name");
      }
    });*/
  
    // Adding the Contact Name and Message
    WoodbugUtil.Grid.addTextView(this.activity, grid, 
      screenWidth / DIVISION, screenWidth / DIVISION, GridLayout.spec(0),
      GridLayout.spec(1), venue.getCategory());
  
    // Adding the Contact Name and Message
    WoodbugUtil.Grid.addTextView(this.activity, grid, 
      2 * screenWidth / DIVISION, screenWidth / DIVISION, GridLayout.spec(1),
      GridLayout.spec(0), venue.getDistance());
  
    // Adding the Contact Name and Message
    WoodbugUtil.Grid.addTextView(this.activity, grid, 
      screenWidth / DIVISION, screenWidth / DIVISION, GridLayout.spec(1),
      GridLayout.spec(1), venue.getPeopleHere());
  
    return rowView;
  }

}