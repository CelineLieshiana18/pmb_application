package com.example.pmb_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pmb_application.fragment.FragmentDenah;
import com.example.pmb_application.fragment.FragmentCtMhs;
import com.example.pmb_application.fragment.FragmentDaftarPenggunaMhs;
import com.example.pmb_application.fragment.FragmentForumMhs;
import com.example.pmb_application.fragment.FragmentHomeMhs;
import com.example.pmb_application.databinding.ActivityMainMahasiswaBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivityMahasiswa extends AppCompatActivity {
    private ActivityMainMahasiswaBinding binding;
    private Toolbar toolbar;
    String URL = VariabelGlobal.link_ip + "api/lecturers/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadMhsData();

        binding = ActivityMainMahasiswaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        toolbar = findViewById(R.id.newAppBar);
        setSupportActionBar(toolbar);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(listener);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new FragmentHomeMhs()).commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menuappbar,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull  MenuItem item) {
        switch (item.getItemId()){
            case  R.id.logout:
                Intent intent = new Intent(MainActivityMahasiswa.this,LoginActivityMahasiswaPanitia.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener listener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selected = null;
                    switch (item.getItemId()){
                        case R.id.nav_homeMhs:
                            selected = new FragmentHomeMhs();
                            break;
                        case R.id.nav_daftarPenggunaMhs:
                            selected = new FragmentDaftarPenggunaMhs();
                            break;
                        case R.id.nav_ctMhs:
                            selected = new FragmentCtMhs();
                            break;
                        case R.id.nav_forumMhs:
                            selected = new FragmentForumMhs();
                            break;
                        case R.id.nav_augmentedRealityMhs:
                            selected = new FragmentDenah();
                            break;

                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,
                            selected).commit();
                    return true;
                }

            };



    private void loadMhsData(){
        RequestQueue queue = Volley.newRequestQueue(this);
        Uri uri = Uri.parse(URL).buildUpon().build();
//        System.out.println(uri);
        StringRequest request = new StringRequest(Request.Method.GET, uri.toString(), response -> {
            try {
                JSONObject object = new JSONObject(response);
//                Gson gson = new Gson();
                System.out.println("data api:");
                System.out.println(object);
//                WSResponse weatherResponse = gson.fromJson(object.toString(), WSResponse.class);
//                getArticleAdapter().changeData(weatherResponse.getArticles());
                Toast.makeText(this, "berhasil",Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            Toast.makeText(this,"error", Toast.LENGTH_SHORT).show();
            error.printStackTrace();
        });
        queue.add(request);
    }


    private void loginMhs(String nrp, String password){
        RequestQueue queue = Volley.newRequestQueue(this);
        Uri uri = Uri.parse("http://192.168.100.6:8090/api/lecturer/").buildUpon().build();
//        System.out.println(uri);
        StringRequest request = new StringRequest(Request.Method.GET, uri.toString(), response -> {
            try {
                JSONObject object = new JSONObject(response);
//                Gson gson = new Gson();
                System.out.println("data api:");
                System.out.println(object);
//                WSResponse weatherResponse = gson.fromJson(object.toString(), WSResponse.class);
//                getArticleAdapter().changeData(weatherResponse.getArticles());
                Toast.makeText(this, "berhasil",Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            Toast.makeText(this,"error", Toast.LENGTH_SHORT).show();
            error.printStackTrace();
        });
        queue.add(request);
    }
}