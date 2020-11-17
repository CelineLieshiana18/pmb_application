package com.example.pmb_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivityDosen extends AppCompatActivity {
    private ActivityLoginDosenBinding binding;


    public static final String LOGIN_URL = "http://192.168.100.6:8090/api/lecturer/login/";

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

        StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            if(object.get("status").equals("Success")){
                                openProfile();
                            } else{
                                Toast.makeText(LoginActivityDosen.this,"NIK dan Nama Tidak Ditemukan",Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(LoginActivityDosen.this,e.toString(),Toast.LENGTH_LONG).show();
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
                System.out.println(map);
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