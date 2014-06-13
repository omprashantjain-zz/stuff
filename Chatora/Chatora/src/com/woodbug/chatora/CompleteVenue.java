package com.woodbug.chatora;

import org.json.JSONObject;

public class CompleteVenue extends Venue {

  private String location, stats,
  verified, hereNow, contact, special, hours, price,
  rating, beenHere, likes, like, dislike;

  public CompleteVenue(JSONObject venue) {
    super(venue);
  }

  public String getLocation() {
    return location;
  }

  public String getStats() {
    return stats;
  }

  public String getVerified() {
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

  public String getLike() {
    return like;
  }

  public String getDislike() {
    return dislike;
  }

}