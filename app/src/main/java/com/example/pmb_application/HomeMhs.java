package com.example.pmb_application;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pmb_application.databinding.ActivityMainMahasiswaBinding;
import com.example.pmb_application.databinding.FragmentHomeMhsBinding;

import java.util.Objects;

public class HomeMhs extends Fragment {
    private FragmentHomeMhsBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeMhsBinding.inflate(inflater,container,false);
        binding.btnPengumuman.setOnClickListener(v -> {
            Fragment fragment = new pengumumanMhs();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        });
        return inflater.inflate(R.layout.fragment_home_mhs, container, false);
    }
}