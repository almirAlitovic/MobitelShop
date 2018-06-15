package com.example.entreprenur.mobileshop.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtil {

    public static final String USER_SHARED_PREFERENCES = "UserSharedPreferences";
    public static final String USER_NAME = "UserName";
    public static final String USER_ID = "UserId";

    private SharedPreferences userSharedPreferences = null;

    public SharedPreferencesUtil(Context context) {
        userSharedPreferences = context.getSharedPreferences(USER_SHARED_PREFERENCES, Context.MODE_PRIVATE);
    }


    public void setUserName(String name) {
        if (userSharedPreferences != null) {
            userSharedPreferences.edit().putString(USER_NAME, name).apply();
        }
    }

    public String getUserName() {
        if (userSharedPreferences != null) {
            return userSharedPreferences.getString(USER_NAME, AppConstants.EMPTY_STRING);
        }
        return  AppConstants.EMPTY_STRING;
    }

    public void setUserId(String id) {
        if (userSharedPreferences != null) {
            userSharedPreferences.edit().putString(USER_ID, id).apply();
        }
    }

    public String getUserID() {
        if (userSharedPreferences != null) {
            return userSharedPreferences.getString(USER_ID, "");
        }
        return  AppConstants.EMPTY_STRING;
    }
}
