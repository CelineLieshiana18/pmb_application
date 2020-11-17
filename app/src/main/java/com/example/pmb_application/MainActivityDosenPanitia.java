package com.example.pmb_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pmb_application.adapter.DosenAdapter;
import com.example.pmb_application.databinding.ActivityMainDosenPanitiaBinding;
import com.example.pmb_application.entity.WSResponse;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivityDosenPanitia extends AppCompatActivity {
    private ActivityMainDosenPanitiaBinding binding;
    private Toolbar toolbar;
    Spinner spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SessionManagement sessionManagement = new SessionManagement(MainActivityDosenPanitia.this);
        binding = ActivityMainDosenPanitiaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        toolbar = findViewById(R.id.newAppBar);
        setSupportActionBar(toolbar);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationViewDosenPanitia);
        bottomNavigationView.setOnNavigationItemSelectedListener(listener);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_dosen_panitia, new FragmentHomeDosenPanitia()).commit();

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
                Intent intent = new Intent(MainActivityDosenPanitia.this,LoginActivityMahasiswaPanitia.class);
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
                        case R.id.nav_homeDosenPanitia:
                            selected = new FragmentHomeDosenPanitia();
                            break;
                        case R.id.nav_kelolaPenggunaDosenPanitia:
                            selected = new FragmentKelolaPenggunaDosenPanitia();
                            break;
                        case R.id.nav_kelolaCTDosenPanitia:
                            selected = new FragmentKelolaCTDosenPanitia();
                            break;
                        case R.id.nav_kelolaForumPengumumanKegiatanDosenPanitia:
                            selected = new FragmentKelolaKegiatanPengumumanForum();
                            break;
                        case R.id.nav_augmentedRealityDosenPanitia:
                            selected = new FragmentAugmentedRealityDosenPanitia();
                            break;

                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_dosen_panitia,
                            selected).commit();
                    return true;
                }

            };

}