package com.woodbug.chatora;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Venue {

  private int distance;
  private String peopleHere;
  private String name;
  private String category;

  public Venue(JSONObject venue) {
    try {
      this.name       = venue.getString("name");
      this.distance   = venue.getJSONObject("location").getInt("distance");
      this.peopleHere = venue.getJSONObject("hereNow").getString("summary");
      
      StringBuilder sb = new StringBuilder();
      JSONArray categories = venue.getJSONArray("categories");
      for(int i = 0; i< categories.length(); i++) {
        JSONObject cat = categories.getJSONObject(i);
        sb.append(cat.getString("shortName") + ",");
      }
      
      if(sb.length() > 0) this.category = sb.substring(0, sb.length() - 1);
    } catch (JSONException e) {
      e.printStackTrace();
	}  
  }
  
  public String getDistance() {
    return distance + "m away";
  }

  public String getPeopleHere() {
    return peopleHere;
  }

  public String getName() {
    return name;
  }

  public String getCategory() {
    return category;
  }
  
}
