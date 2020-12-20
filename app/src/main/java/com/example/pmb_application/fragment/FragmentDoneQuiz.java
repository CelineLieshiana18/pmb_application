package com.example.pmb_application.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pmb_application.R;
import com.example.pmb_application.SessionManagement;
import com.example.pmb_application.databinding.FragmentDoneQuizBinding;

public class FragmentDoneQuiz extends Fragment {
    private FragmentDoneQuizBinding binding;
    SessionManagement sessionManagement;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDoneQuizBinding.inflate(inflater,container,false);
        binding.btnBackToCT.setOnClickListener(v -> {
            Fragment fragment = new FragmentDoneQuiz();
            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout,fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        sessionManagement = new SessionManagement(getContext().getApplicationContext());
        if(getArguments() != null && getArguments().containsKey("Score")){
            Integer score = getArguments().getInt("Score");
            binding.tvScorePG.setText(String.valueOf(score));
            if(sessionManagement.getJumlahSoalIsian() == -1){
                binding.tvKataIsian.setVisibility(View.GONE);
            }
        }
    }
}