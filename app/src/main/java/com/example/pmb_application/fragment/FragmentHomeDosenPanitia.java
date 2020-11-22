package com.example.pmb_application.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pmb_application.R;
import com.example.pmb_application.SessionManagement;
import com.example.pmb_application.databinding.FragmentHomeDosenPanitiaBinding;

public class FragmentHomeDosenPanitia extends Fragment {
    private FragmentHomeDosenPanitiaBinding binding;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        binding = FragmentHomeDosenPanitiaBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        SessionManagement sessionManagement = new SessionManagement(getContext().getApplicationContext());
        binding.txtawaldosen.setText(sessionManagement.getSession());
        binding.tvNumberDosen.setText("15");
        System.out.println("masuk sini kok");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_dosen_panitia, container, false);
    }
}