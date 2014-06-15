package com.woodbug.chatora;

import org.json.JSONException;
import org.json.JSONObject;

public class CompleteVenue {

  private String name, location, checkins, contact, rating, likes, hereNow, hours,
    special, price, beenHere;
  private boolean verified, dislike;
  
  public CompleteVenue(JSONObject venue) {
    try {
        
  	  StringBuilder loc = new StringBuilder();
      if(venue.getJSONObject("location").has("address"))
        loc.append(venue.getJSONObject("location").getString("address"));
      if(venue.getJSONObject("location").has("crossStreet"))
        loc.append(" " + venue.getJSONObject("location").getString("crossStreet"));
      if(venue.getJSONObject("location").has("city"))
        loc.append(" " + venue.getJSONObject("location").getString("city"));
      if(venue.getJSONObject("location").has("postalCode"))
        loc.append(" " + venue.getJSONObject("location").getString("postalCode"));
      if(venue.getJSONObject("location").has("state"))
        loc.append(" " + venue.getJSONObject("location").getString("state"));
      if(venue.getJSONObject("location").has("country"))
        loc.append(" " + venue.getJSONObject("location").getString("country"));
      if(venue.getJSONObject("location").has("state"))
        loc.append(" " + venue.getJSONObject("location").getString("state"));
      this.location = loc.toString();
        
      if(venue.has("name"))
        this.name       = venue.getString("name");
      if(venue.has("verified"))
        this.verified = venue.getBoolean("verified");
      if(venue.getJSONObject("stats").has("checkinsCount"))
        this.checkins = venue.getJSONObject("stats").getString("checkinsCount");
      if(venue.getJSONObject("likes").has("summary"))
        this.likes    = venue.getJSONObject("likes").getString("summary");
      if(venue.has("rating"))
        this.rating   = venue.getString("rating");
      if(venue.getJSONObject("contact").has("phone"))
        this.contact  = venue.getJSONObject("contact").getString("phone");
      if(venue.has("dislike"))
        this.dislike  = venue.getBoolean("dislike");
      if(venue.getJSONObject("hereNow").has("summary"))
        this.hereNow  = venue.getJSONObject("hereNow").getString("summary");
      if(venue.getJSONObject("hours").has("status"))
        this.hours    = venue.getJSONObject("hours").getString("status");
            
    } catch (JSONException e) {
      e.printStackTrace();
  	}
  }

  public String getName() {
    return name;
  }

  public String getLocation() {
    return location;
  }

  public String getCheckins() {
    return checkins;
  }

  public boolean getVerified() {
    return verified;
  }

  public String getHereNow() {
    return hereNow;
  }

  public String getContact() {
    return contact;
  }

  public String getSpecial() {
    return special;
  }

  public String getHours() {
    return hours;
  }

  public String getPrice() {
    return price;
  }

  public String getRating() {
    return rating;
  }

  public String getBeenHere() {
    return beenHere;
  }

  public String getLikes() {
    return likes;
  }

  public boolean getDislike() {
    return dislike;
  }

}