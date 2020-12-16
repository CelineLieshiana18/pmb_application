package com.example.pmb_application.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pmb_application.R;
import com.example.pmb_application.SessionManagement;
import com.example.pmb_application.VariabelGlobal;
import com.example.pmb_application.databinding.FragmentIkutiCtIsianMhsBinding;

public class FragmentIkutiCTIsianMhs extends Fragment {
    private FragmentIkutiCtIsianMhsBinding binding;
    int id;
    //belom
    String URLGET = VariabelGlobal.link_ip + "api/soalIsians/getAllSoalIsianByIdCT/";
    String URL = VariabelGlobal.link_ip + "api/soalIsians/";
    SessionManagement sessionManagement;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        sessionManagement = new SessionManagement(getContext().getApplicationContext());
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentIkutiCtIsianMhsBinding.inflate(inflater,container,false);
        initComponents();
        return binding.getRoot();
    }


    private void initComponents() {
        binding.btnNext.setOnClickListener(v -> {
//            if(binding.txtNomor.getText() == null || binding.txtSoal.getText() == null || binding.txtScore.getText() == null){
//                Toast.makeText(getActivity(),"Gagal ditambahkan, Semua data harus terisi",Toast.LENGTH_LONG).show();
//            } else{
//                addSoalIsian();
//            }
        });
        binding.btnBack.setOnClickListener(v -> {
//            if(binding.txtNomor.getText() == null || binding.txtSoal.getText() == null || binding.txtScore.getText() == null){
//                Toast.makeText(getActivity(),"Gagal ditambahkan, Semua data harus terisi",Toast.LENGTH_LONG).show();
//            } else{
//                addSoalIsian();
//            }
        });
        binding.btnSubmit.setOnClickListener(v -> {
//            if(binding.txtNomor.getText() == null || binding.txtSoal.getText() == null || binding.txtScore.getText() == null){
//                Toast.makeText(getActivity(),"Gagal ditambahkan, Semua data harus terisi",Toast.LENGTH_LONG).show();
//            } else{
//                addSoalIsian();
//            }
        });
    }
}