package com.example.pmb_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import com.example.pmb_application.databinding.ActivityMainMahasiswaBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivityMahasiswa extends AppCompatActivity {
    private ActivityMainMahasiswaBinding binding;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
                            selected = new FragmentAugmentedRealityMhs();
                            break;

                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,
                            selected).commit();
                    return true;
                }

            };
}