package com.example.pmb_application.fragment;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pmb_application.MainActivityDosenPanitia;
import com.example.pmb_application.R;
import com.example.pmb_application.SessionManagement;
import com.example.pmb_application.VariabelGlobal;
import com.example.pmb_application.adapter.SoalCTIsianAdapter;
import com.example.pmb_application.adapter.SoalCTPilihanGandaAdapter;
import com.example.pmb_application.databinding.FragmentIkutiCtPgMhsBinding;
import com.example.pmb_application.databinding.FragmentKelolaCtIsianBinding;
import com.example.pmb_application.entity.Dosen;
import com.example.pmb_application.entity.SoalCTIsian;
import com.example.pmb_application.entity.SoalCTPilihanGanda;
import com.example.pmb_application.entity.WSResponseSoalCTIsian;
import com.example.pmb_application.entity.WSResponseSoalCTPilihanGanda;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FragmentIkutiCTPgMhs extends Fragment {
    private FragmentIkutiCtPgMhsBinding binding;
    int id;
    //belom
    String URLGET = VariabelGlobal.link_ip + "api/soalIsians/getAllSoalIsianByIdCT/";
    String URL = VariabelGlobal.link_ip + "api/soalIsians/";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentIkutiCtPgMhsBinding.inflate(inflater,container,false);
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

    private void loadSoalDataPG() {
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        Uri uri = Uri.parse(URLGET).buildUpon().build();
        StringRequest request = new StringRequest(Request.Method.GET, uri.toString(), response -> {
            try {
                JSONObject object = new JSONObject(response);
                Gson gson = new Gson();
//                System.out.println("kegiatan");
//                System.out.println(object);
                WSResponseSoalCTPilihanGanda weatherResponse = gson.fromJson(object.toString(), WSResponseSoalCTPilihanGanda.class);
                //simpen di session
                SessionManagement sessionManagement = new SessionManagement(getContext().getApplicationContext());
                ArrayList<SoalCTPilihanGanda> pilihanGandas = new ArrayList<SoalCTPilihanGanda>(weatherResponse.getData());
                sessionManagement.setSoalPg(pilihanGandas);
                //end
                Toast.makeText(getActivity(), "berhasil", Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            Toast.makeText(getActivity(),"error", Toast.LENGTH_SHORT).show();
            error.printStackTrace();
        });
        queue.add(request);
    }


    private void loadSoalDataIsian() {
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        Uri uri = Uri.parse(URLGET).buildUpon().build();
        StringRequest request = new StringRequest(Request.Method.GET, uri.toString(), response -> {
            try {
                JSONObject object = new JSONObject(response);
                Gson gson = new Gson();
//                System.out.println("kegiatan");
//                System.out.println(object);
                WSResponseSoalCTIsian weatherResponse = gson.fromJson(object.toString(), WSResponseSoalCTIsian.class);
                //simpen di session
                SessionManagement sessionManagement = new SessionManagement(getContext().getApplicationContext());
                ArrayList<SoalCTIsian> isians = new ArrayList<SoalCTIsian>(weatherResponse.getData());
                sessionManagement.setSoalIsian(isians);
                //end
                Toast.makeText(getActivity(), "berhasil", Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            Toast.makeText(getActivity(),"error", Toast.LENGTH_SHORT).show();
            error.printStackTrace();
        });
        queue.add(request);
    }

}