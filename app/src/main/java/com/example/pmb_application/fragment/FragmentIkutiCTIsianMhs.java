package com.example.pmb_application.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pmb_application.R;
import com.example.pmb_application.SessionManagement;
import com.example.pmb_application.VariabelGlobal;
import com.example.pmb_application.databinding.FragmentIkutiCtIsianMhsBinding;
import com.example.pmb_application.databinding.FragmentIkutiCtPgMhsBinding;
import com.example.pmb_application.entity.SoalCTIsian;
import com.example.pmb_application.entity.SoalCTPilihanGanda;

import java.util.ArrayList;

public class FragmentIkutiCTIsianMhs extends Fragment {
    private FragmentIkutiCtIsianMhsBinding binding;
    private FragmentIkutiCTPgMhs pilihanganda;
    SessionManagement sessionManagement;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        sessionManagement = new SessionManagement(getContext().getApplicationContext());
        super.onCreate(savedInstanceState);

    }

    public FragmentIkutiCTPgMhs getPilihanganda() {
        if(pilihanganda == null){
            pilihanganda = new FragmentIkutiCTPgMhs();
        }
        return pilihanganda;
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
//            if(sessionManagement.getNoPg().equals(1)){
//                binding.btnBack.setEnabled(false);
//            }else{
            int currnumber = sessionManagement.getNoIsian();
            sessionManagement.setNoIsian(currnumber+1);
            if(sessionManagement.getNoIsian().equals(sessionManagement.getJumlahSoalIsian())){
                binding.btnNext.setEnabled(false);
            }

            ArrayList<SoalCTIsian> isians = new ArrayList<SoalCTIsian>(sessionManagement.getSoalCTIsian());
            SoalCTIsian soalCTIsian = new SoalCTIsian(isians.get(sessionManagement.getNoIsian()-1));
            binding.tvNoSoalIsian.setText(String.valueOf(soalCTIsian.getNumber()+sessionManagement.getNoPg()));
            binding.tvSoalCtIsianMhs.setText(soalCTIsian.getQuestion());
        });
        binding.btnBack.setOnClickListener(v -> {
            if(sessionManagement.getNoIsian().equals(1)){
                // no awal langsung ke pg
                if(sessionManagement.getJumlahSoalPg() > 0){
                    // kalo ada back to pg
                    Bundle bundle = new Bundle();
                    bundle.putString("ket","dari Isian");
                    getPilihanganda().setArguments(bundle);

                    sessionManagement.setKeteranganSoal("pilihanganda");
                    FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_layout,getPilihanganda());
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            }else{
                int currnumber = sessionManagement.getNoIsian();
                sessionManagement.setNoIsian(currnumber-1);

                ArrayList<SoalCTIsian> isians = new ArrayList<SoalCTIsian>(sessionManagement.getSoalCTIsian());
                SoalCTIsian soalCTIsian = new SoalCTIsian(isians.get(sessionManagement.getNoIsian()-1));
                binding.tvNoSoalIsian.setText(String.valueOf(soalCTIsian.getNumber()+sessionManagement.getNoPg()));
                binding.tvSoalCtIsianMhs.setText(soalCTIsian.getQuestion());

                // soal terakhir dan isian ga ada
                if(sessionManagement.getJumlahSoalIsian() < 1 && sessionManagement.getNoPg().equals(sessionManagement.getJumlahSoalPg())){
                    // kalo soal isian ga ada langsung di disabled button nextnya
                    binding.btnNext.setEnabled(false);
                }
            }
        });
        binding.btnSubmit.setOnClickListener(v -> {
//            if(binding.txtNomor.getText() == null || binding.txtSoal.getText() == null || binding.txtScore.getText() == null){
//                Toast.makeText(getActivity(),"Gagal ditambahkan, Semua data harus terisi",Toast.LENGTH_LONG).show();
//            } else{
//                addSoalIsian();
//            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        ArrayList<SoalCTIsian> isians = new ArrayList<SoalCTIsian>(sessionManagement.getSoalCTIsian());
        SoalCTIsian soalCTIsian = new SoalCTIsian(isians.get(sessionManagement.getNoIsian()-1));
        binding.tvNoSoalIsian.setText(String.valueOf(soalCTIsian.getNumber()+sessionManagement.getNoPg()));
        binding.tvSoalCtIsianMhs.setText(soalCTIsian.getQuestion());
        if(sessionManagement.getNoIsian().equals(sessionManagement.getJumlahSoalIsian())){
            binding.btnNext.setEnabled(false);
        }
    }
}