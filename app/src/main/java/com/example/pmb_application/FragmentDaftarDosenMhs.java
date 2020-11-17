package com.example.pmb_application;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pmb_application.adapter.DosenAdapter;
import com.example.pmb_application.entity.Dosen;

public class FragmentDaftarDosenMhs extends Fragment implements DosenAdapter.ItemClickListener{

    public FragmentDaftarDosenMhs() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_daftar_dosen_mhs, container, false);
    }

    @Override
    public void itemClicked(Dosen dosen) {

    }
}