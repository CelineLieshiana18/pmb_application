package com.example.pmb_application;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.pmb_application.databinding.ActivityMainAdminBinding;
import com.example.pmb_application.fragment.FragmentHomeDosenPanitia;
import com.example.pmb_application.fragment.FragmentKelolaPenggunaDosenPanitia;
import com.example.pmb_application.fragment.FragmentKelolaTahunAjaran;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivityAdmin extends AppCompatActivity {
    private ActivityMainAdminBinding binding;
    private Toolbar toolbar;
    Spinner spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SessionManagement sessionManagement = new SessionManagement(MainActivityAdmin.this);
        binding = ActivityMainAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        toolbar = findViewById(R.id.newAppBar);
        setSupportActionBar(toolbar);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationViewAdmin);
        bottomNavigationView.setOnNavigationItemSelectedListener(listener);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_admin, new FragmentHomeDosenPanitia()).commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menuappbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case  R.id.logout:
                Intent intent = new Intent(MainActivityAdmin.this, LoginActivityMahasiswaPanitia.class);
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
//                    SessionManagement sessionManagement = new SessionManagement(MainActivityAdmin.this);
//                    System.out.println("jabatan user : "+sessionManagement.getJabatan());
                    switch (item.getItemId()){
                        case R.id.nav_homeDosenPanitia:
                            selected = new FragmentHomeDosenPanitia();
                            break;
                        case R.id.nav_kelolaPenggunaDosenPanitia:
                            selected = new FragmentKelolaPenggunaDosenPanitia();
                            break;
                        case R.id.nav_kelolaTahunAjaran:
                            selected = new FragmentKelolaTahunAjaran();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_admin,
                            selected).commit();
                    return true;
                }

            };
}