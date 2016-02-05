package com.hotgirl.zeptomobile.hotgirl.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.gson.Gson;

public class SharedPreference {

    public static final String PREFS_NAME = "AOP_PREFS";
    public static final String PREFS_STATE="AOP_BTN";
    public static final String PREFS_FAV_URL="PREFS_FAV_URL";

    public static final String PREFS_KEY = "AOP_PREFS_String";
    public static final String PREFS_KEY_INT = "AOP_PREFS_KEY_INT";
    public static final String PREF_BTN_KEY = "AOP_PREFS_BTN";
    public static final String PREF_URL_KEY = "PREFS_URL_KEY";

    public SharedPreference() {
        super();
    }

    public void save(Context context, String text) {
        SharedPreferences settings;
        Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE); //1
        editor = settings.edit();
        editor.putString(PREFS_KEY,text);
        editor.commit();

    }

    public String getValue(Context context) {
        SharedPreferences settings;
        String text;

        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        text = settings.getString(PREFS_KEY, null);

        return text;
    }


    public void clearSharedPreference(Context context) {
        SharedPreferences settings;
        Editor editor;

        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();

        editor.clear();
        editor.commit();
    }

    public void removeValue(Context context) {
        SharedPreferences settings;
        Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();

        editor.remove(PREFS_KEY);
        editor.commit();
    }


    public void saveFavourite(FragmentActivity activity, String favouriteUrl) {
        SharedPreferences settings;
        Editor editor;

        settings = activity.getSharedPreferences(PREFS_FAV_URL, Context.MODE_PRIVATE); //1
        editor = settings.edit(); //2
        editor.putString(PREF_URL_KEY, favouriteUrl); //3
        editor.commit(); //4
    }



}

