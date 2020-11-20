package com.example.pmb_application;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManagement {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String SHARED_PREF_NAME = "session";
    String SESSION_KEY = "session_user";//nrp atau nik
    String SESSION_ID = "session_id";//id
    String SESSION_NAME = "session_name";

    public SessionManagement(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveSession(String NRPNIK,String id, String name){
        editor.putString(SESSION_KEY,NRPNIK).commit();
        editor.putString(SESSION_ID,id);
        editor.putString(SESSION_NAME,name);
    }

    public String getSession(){
        return sharedPreferences.getString(SESSION_KEY,"false");
    }

    public String getId(){return sharedPreferences.getString(SESSION_ID,"false");}

    public String getName(){return sharedPreferences.getString(SESSION_NAME,"false");}

    public void clearSession(){
        editor.putString(SESSION_KEY,"false").commit();
    }

}
