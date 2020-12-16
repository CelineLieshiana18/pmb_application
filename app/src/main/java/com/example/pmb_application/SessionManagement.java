package com.example.pmb_application;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.pmb_application.entity.SoalCTIsian;
import com.example.pmb_application.entity.SoalCTPilihanGanda;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SessionManagement {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String SHARED_PREF_NAME = "session";
    String SESSION_KEY = "session_user";//nrp atau nik
    String SESSION_ID = "session_id";//id
    String SESSION_NAME = "session_name";
    String SESSION_Jabatan = "session_jabatan";
    String SOAL_ISIAN = "soal_isian";
    String SOAL_PG = "soal_pg";
    String NO_PG = "no_pg";
    String NO_ISIAN = "no_isian";

    public SessionManagement(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveSession(String NRPNIK, String id, String name, String jabatan){
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

    public void setSoalIsian(ArrayList<SoalCTIsian> soalIsian){
        Gson gson = new Gson();
        String jsonString = gson.toJson(soalIsian);
        editor.putString(SOAL_ISIAN,jsonString).commit();
        editor.putInt(NO_ISIAN,1).commit();
    }

    public void setSoalPg(ArrayList<SoalCTPilihanGanda> soalPg){
        Gson gson = new Gson();
        String jsonString = gson.toJson(soalPg);
        editor.putString(SOAL_PG,jsonString).commit();
        editor.putInt(NO_PG,1).commit();
    }

    public Integer getNoPg(){return sharedPreferences.getInt(NO_PG,-1);}
    public Integer getNoIsian(){return sharedPreferences.getInt(NO_ISIAN,-1);}

    // CT
    public void backPg(){
        int newnumber = sharedPreferences.getInt(NO_PG,-1) - 1;
        editor.putInt(NO_PG,newnumber).commit();
    }

    public void nextPg(){
        int newnumber = sharedPreferences.getInt(NO_PG,-1) + 1;
        editor.putInt(NO_PG,newnumber).commit();
    }

    public void nextIsian(){
        int newnumber = sharedPreferences.getInt(NO_ISIAN,-1) + 1;
        editor.putInt(NO_ISIAN,newnumber).commit();
    }

    public void backIsian(){
        int newnumber = sharedPreferences.getInt(NO_ISIAN,-1) - 1;
        editor.putInt(NO_ISIAN,newnumber).commit();
    }

    public SoalCTIsian getSoalCTIsian(){
        String jsonString = sharedPreferences.getString(SOAL_ISIAN,"");

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<SoalCTIsian>>() {}.getType();
        List<SoalCTIsian> list = gson.fromJson(jsonString,type);
        return list.get(sharedPreferences.getInt(NO_ISIAN,-1));
    }

    public SoalCTPilihanGanda getSoalCTPg(){
        String jsonString = sharedPreferences.getString(SOAL_PG,"");

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<SoalCTPilihanGanda>>() {}.getType();
        List<SoalCTPilihanGanda> list = gson.fromJson(jsonString,type);
        return list.get(sharedPreferences.getInt(NO_PG,-1));
    }
}
