package com.example.pmb_application.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pmb_application.R;
import com.example.pmb_application.databinding.FragmentDenahBinding;

public class FragmentDenah extends Fragment {
    private FragmentDenahBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDenahBinding.inflate(inflater,container,false);
        binding.btnDenah.setOnClickListener(v-> {
//            Intent intent = new Intent(getActivity(), UnityPlayerActivity.class);
//            startActivity(intent);
        });

        return binding.getRoot();
    }
}