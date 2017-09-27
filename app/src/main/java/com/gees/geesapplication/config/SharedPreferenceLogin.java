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

    public void save(Context context, String apiToken, String name, int roleId, String roleName, int company) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE); //1
        editor = settings.edit();

        editor.putBoolean(LOGGEDIN_SHARED_PREF, true);
        editor.putString("apiToken", apiToken);
        editor.putString("name", name);
        editor.putInt("roleId", roleId);
        editor.putString("roleName", roleName);
        editor.putInt("company", company);

        editor.commit();
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
}
