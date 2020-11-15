package com.example.pmb_application;

import android.app.DownloadManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;


import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pmb_application.databinding.ActivityMainDosenPanitiaBinding;
import com.example.pmb_application.databinding.FragmentKelolaPenggunaDosenPanitiaBinding;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class kelolaPenggunaDosenPanitia extends Fragment {

    private MainActivityDosenPanitia mainActivityDosenPanitia;
    private FragmentKelolaPenggunaDosenPanitiaBinding binding;

    Spinner spinner;
    String[] jurusan = {"Teknik Informatika","Sistem Informasi"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSpinnerData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mainActivityDosenPanitia = new MainActivityDosenPanitia();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_kelola_pengguna_dosen_panitia, container, false);
    }

    public void setSpinnerData(){
        spinner = binding.spinJurusan;
        ArrayAdapter aa = new ArrayAdapter(getActivity(),android.R.layout.simple_dropdown_item_1line, jurusan);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(aa);
    }


}