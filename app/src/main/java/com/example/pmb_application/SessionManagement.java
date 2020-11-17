package com.example.pmb_application;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManagement {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String SHARED_PREF_NAME = "session";
    String SESSION_KEY = "session_user";

    public SessionManagement(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveSession(String NRPNIK){
        editor.putString(SESSION_KEY,NRPNIK).commit();
    }

    public String getSession(){
        return sharedPreferences.getString(SESSION_KEY,"false");
    }


    public void clearSession(){
        editor.putString(SESSION_KEY,"false").commit();
    }

}
