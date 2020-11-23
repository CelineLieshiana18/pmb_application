package com.example.pmb_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pmb_application.databinding.ActivityLoginDosenBinding;
import com.example.pmb_application.databinding.ActivityLoginMahasiswaPanitiaBinding;
import com.example.pmb_application.entity.Dosen;
import com.example.pmb_application.entity.WSResponseDosen;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LoginActivityDosen extends AppCompatActivity {
    private ActivityLoginDosenBinding binding;

    String URL =  VariabelGlobal.link_ip + "api/lecturers/login/";

    public static final String KEY_NIP="nip";
    public static final String KEY_PASSWORD="password";

    private String nip;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginDosenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnMasuk.setOnClickListener(view -> {
            if (binding.txtNik.getText().toString().isEmpty() || binding.txtPassword.getText().toString().isEmpty())
            {
                Toast.makeText(LoginActivityDosen.this,R.string.field_kosong_mhs_messsage, Toast.LENGTH_SHORT).show();
            }
            else {
                userLogin();
            }
        });


        binding.linkToLoginMhsPanitia.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivityDosen.this, LoginActivityMahasiswaPanitia.class);
            startActivity(intent);

        });
    }


    private void userLogin() {
        nip = binding.txtNik.getText().toString().trim();
        password = binding.txtPassword.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            if(object.get("status").equals("Success")){
                                Gson gson = new Gson();
                                WSResponseDosen weatherResponse = gson.fromJson(object.toString(), WSResponseDosen.class);
                                Dosen dosen = new Dosen(weatherResponse.getData().get(0));
                                SessionManagement sessionManagement = new SessionManagement(LoginActivityDosen.this);
                                System.out.println("nama user : "+ sessionManagement.getName() + sessionManagement.getJabatan()+ sessionManagement.getSession()+ sessionManagement.getId());
                                sessionManagement.saveSession(dosen.getNip(),String.valueOf(dosen.getId()),dosen.getName(), dosen.getJabatan());
                                System.out.println(sessionManagement.getSession());
                                openProfile();
                            } else{
                                Toast.makeText(LoginActivityDosen.this,"NIK dan Nama Tidak Ditemukan",Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(LoginActivityDosen.this,"masuk catch",Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivityDosen.this,"NIK dan Nama Tidak Ditemukan",Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put(KEY_NIP,nip);
                map.put(KEY_PASSWORD,password);
//                SessionManagement sessionManagement = new SessionManagement(LoginActivityDosen.this);
//                sessionManagement.saveSession(nip);
//                System.out.println(sessionManagement.getSession());
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    private void openProfile(){
        Intent intent = new Intent(this, MainActivityDosenPanitia.class);
//        intent.putExtra(K, username);
        startActivity(intent);
    }
}