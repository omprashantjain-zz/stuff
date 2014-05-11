package com.woodbug.chatora.data;

import android.content.Context;
import android.content.SharedPreferences;

public class PersistantData {

  SharedPreferences pref;
  final String
    PREFS_NAME       = "chatora",
    DISPLAY_NAME     = "display_name",
    GENDER           = "gender",
    AGE              = "age",
    HOUSEHOLD_INCOME = "household_income";

  public PersistantData(Context context) {
    pref = context.getSharedPreferences(PREFS_NAME, 0);
  }
  
  public void setDisplayName(String name) {
    pref.edit().putString(DISPLAY_NAME, name).commit();
  }
  
  public String getDisplayName() {
    return pref.getString(DISPLAY_NAME, "Guest");
  }

  public void setGender(String gender) {
    pref.edit().putString(GENDER, gender).commit();
  }
  
  public String getGender() {
    return pref.getString(GENDER, "other");
  }

  public void setAge(int age) {
    pref.edit().putInt(AGE, age).commit();
  }
  
  public int getAge() {
    return pref.getInt(AGE, 0);
  }

  public void setHHI(String hhi) {
    pref.edit().putString(HOUSEHOLD_INCOME, hhi).commit();
  }
  
  public String getHHI() {
    return pref.getString(HOUSEHOLD_INCOME, "other");
  }
  
}
