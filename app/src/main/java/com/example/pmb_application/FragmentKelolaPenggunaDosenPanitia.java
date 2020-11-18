package com.example.pmb_application;

import android.app.DownloadManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;


import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pmb_application.adapter.SectionPagerAdapter;
import com.example.pmb_application.databinding.ActivityMainDosenPanitiaBinding;
import com.example.pmb_application.databinding.FragmentKelolaPenggunaDosenPanitiaBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class FragmentKelolaPenggunaDosenPanitia extends Fragment {

    private MainActivityDosenPanitia mainActivityDosenPanitia;
    private FragmentKelolaPenggunaDosenPanitiaBinding binding;
    View myFragment;
    ViewPager viewPager;
    TabLayout tabLayout;

    Spinner spinner;
    String[] jurusan = {"Teknik Informatika","Sistem Informasi"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentKelolaPenggunaDosenPanitiaBinding.inflate(getLayoutInflater());
//        setSpinnerData();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mainActivityDosenPanitia = new MainActivityDosenPanitia();

        myFragment =  inflater.inflate(R.layout.fragment_kelola_pengguna_dosen_panitia, container, false);
        viewPager = myFragment.findViewById(R.id.viewPagerKelolaPengguna);
        tabLayout = myFragment.findViewById(R.id.tabLayoutKelolaPengguna);
        // Inflate the layout for this fragment
        return myFragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setUpViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setUpViewPager(ViewPager viewPager) {
        SectionPagerAdapter sectionPagerAdapter = new SectionPagerAdapter(getChildFragmentManager());
        sectionPagerAdapter.addFragment(new FragmentKelolaMahasiswa(),"Data Mahasiswa");
        sectionPagerAdapter.addFragment(new FragmentKelolaPanitia(),"Data Panitia");
        sectionPagerAdapter.addFragment(new FragmentKelolaDosen(),"Data Dosen");
        viewPager.setAdapter(sectionPagerAdapter);
    }

//    public void setSpinnerData(){
//        spinner = binding.spinJurusan;
//        ArrayAdapter aa = new ArrayAdapter(getActivity(),android.R.layout.simple_dropdown_item_1line, jurusan);
//        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(aa);
//    }


}