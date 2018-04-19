package com.example.maximus09.spfsupply.util;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class Preference {

    private final String pref_file = "preference.dat";
    private final String pref_token = "pref_token";

    private Context context;

    public Preference(Context context) {
        this.context = context;
    }

    public Preference setToken(String set){
        SharedPreferences sp = context.getSharedPreferences(pref_file, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(pref_token, set);
        editor.apply();
        return this;
    }

    public String getToken(){
        SharedPreferences sp = context.getSharedPreferences(pref_file, Activity.MODE_PRIVATE);
        return sp.getString(pref_token, null);
    }


}
