package com.gees.geesapplication.config;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by SERVER on 11/09/2017.
 */

public class SharedPreferenceLogin {

    public static final String PREFS_NAME = "LOGIN_PREFS";
    public static final String PREFS_KEY = "AOP_PREFS_String";
    public static String LOGGEDIN_SHARED_PREF = "loggedin";

    public SharedPreferenceLogin(){
        super();
    }

    public void save(Context context, String apiToken, String name) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE); //1
        editor = settings.edit(); //2

        editor.putBoolean(LOGGEDIN_SHARED_PREF, true);
        editor.putString("apiToken", apiToken); //3
        editor.putString("name", name);
        /*editor.putString("email", email);
        editor.putString("imagePath", imagePath);
        editor.putInt("schoolId",schoolId);
        editor.putInt("classId",classId);
        editor.putString("deviceId", deviceId);*/

        editor.commit(); //4
    }

    public SharedPreferences getValue(Context context) {
        SharedPreferences settings;

        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return settings;
    }

    public void clearSharedPreference(Context context) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();

        editor.clear();
        editor.putBoolean(LOGGEDIN_SHARED_PREF, false);
        editor.commit();
    }

    public void removeValue(Context context) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();

        editor.remove(PREFS_KEY);
        editor.commit();
    }
}
