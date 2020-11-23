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
    String SESSION_Jabatan = "session_jabatan";

    public SessionManagement(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveSession(String NRPNIK,String id, String name, String jabatan){
        editor.putString(SESSION_KEY,NRPNIK).commit();
        editor.putString(SESSION_ID,id).commit();
        editor.putString(SESSION_NAME,name).commit();
        editor.putString(SESSION_Jabatan,jabatan).commit();
        System.out.println("di session management "+ sharedPreferences.getString(SESSION_Jabatan,"") + SESSION_KEY + SESSION_ID + SESSION_NAME);
    }

    public String getSession(){
        return sharedPreferences.getString(SESSION_KEY,"false");
    }

    public String getId(){return sharedPreferences.getString(SESSION_ID,"false");}

    public String getName(){return sharedPreferences.getString(SESSION_NAME,"false");}

    public String getJabatan(){return sharedPreferences.getString(SESSION_Jabatan,"false");}


    public void clearSession(){
        editor.putString(SESSION_KEY,"false").commit();
    }

}
