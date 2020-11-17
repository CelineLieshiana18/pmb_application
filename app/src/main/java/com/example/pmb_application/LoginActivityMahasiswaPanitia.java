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
import com.example.pmb_application.databinding.ActivityLoginMahasiswaPanitiaBinding;

import java.util.HashMap;
import java.util.Map;

public class LoginActivityMahasiswaPanitia extends AppCompatActivity {
    private ActivityLoginMahasiswaPanitiaBinding binding;


    public static final String LOGIN_URL = "http://192.168.100.6:8090/login/";

    public static final String KEY_NRP="nrp";
    public static final String KEY_PASSWORD="password";

    private String nrp;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginMahasiswaPanitiaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnMasuk.setOnClickListener(view -> {
            if (binding.txtNrp.getText().toString().isEmpty() || binding.txtPassword.getText().toString().isEmpty())
            {
                Toast.makeText(LoginActivityMahasiswaPanitia.this,R.string.field_kosong_mhs_messsage, Toast.LENGTH_SHORT).show();
            }
            else {
                userLogin();
            }
        });

        binding.linkToLoginDosen.setOnClickListener(view -> {
            System.out.println("Clicked");
            Intent intent = new Intent(LoginActivityMahasiswaPanitia.this, LoginActivityDosen.class);
            startActivity(intent);

        });
    }

    private void userLogin() {
        nrp = binding.txtNrp.getText().toString().trim();
        password = binding.txtPassword.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equals("success")){
                            openProfile();
                        }else{
                            Toast.makeText(LoginActivityMahasiswaPanitia.this,response,Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivityMahasiswaPanitia.this,error.toString(),Toast.LENGTH_LONG ).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put(KEY_NRP,nrp);
                map.put(KEY_PASSWORD,password);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    private void openProfile(){
        Intent intent = new Intent(this, MainActivityMahasiswa.class);
//        intent.putExtra(K, username);
        startActivity(intent);
    }
}