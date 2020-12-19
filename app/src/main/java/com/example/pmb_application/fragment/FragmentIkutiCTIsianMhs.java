package com.example.pmb_application.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pmb_application.R;
import com.example.pmb_application.SessionManagement;
import com.example.pmb_application.VariabelGlobal;
import com.example.pmb_application.databinding.FragmentIkutiCtIsianMhsBinding;
import com.example.pmb_application.databinding.FragmentIkutiCtPgMhsBinding;
import com.example.pmb_application.entity.SoalCTIsian;
import com.example.pmb_application.entity.SoalCTPilihanGanda;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FragmentIkutiCTIsianMhs extends Fragment {
    private FragmentIkutiCtIsianMhsBinding binding;
    private FragmentIkutiCTPgMhs pilihanganda;
    SessionManagement sessionManagement;
    String URLADDCTPG = VariabelGlobal.link_ip + "api/soalPgs/storeAnswerPG";
    String URLADDCTIsian = VariabelGlobal.link_ip + "api/soalPgs/storeAnswerIsian";

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
            binding.btnBack.setText("Soal Sebelumnya");
            int currnumber = sessionManagement.getNoIsian();
            sessionManagement.setNoIsian(currnumber+1);
            if(sessionManagement.getNoIsian().equals(sessionManagement.getJumlahSoalIsian())){
                binding.btnNext.setEnabled(false);
                binding.btnKumpulkan.setVisibility(View.VISIBLE);
            }

            ArrayList<SoalCTIsian> isians = new ArrayList<SoalCTIsian>(sessionManagement.getSoalCTIsian());
            SoalCTIsian soalCTIsian = new SoalCTIsian(isians.get(sessionManagement.getNoIsian()-1));
            binding.tvNoSoalIsian.setText(String.valueOf(soalCTIsian.getNumber()+sessionManagement.getNoPg()));
            binding.tvSoalCtIsianMhs.setText(soalCTIsian.getQuestion());

            String jwb = soalCTIsian.getJawaban();
            if(jwb != null){
                binding.tvJawabanIsian.setText(jwb);
            }else{
                binding.tvJawabanIsian.setText("");
            }
        });
        binding.btnBack.setOnClickListener(v -> {
            binding.btnKumpulkan.setVisibility(View.GONE);
            binding.btnNext.setEnabled(true);
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

                // soal pertama
                if(sessionManagement.getJumlahSoalPg() >= 0 && sessionManagement.getNoIsian() <= 1){
                    // kalo soal pg ada
                    binding.btnBack.setText("Soal Pilihan Ganda");
                }
                String jwb = soalCTIsian.getJawaban();
                if(jwb != null){
                    binding.tvJawabanIsian.setText(jwb);
                } else{
                    binding.tvJawabanIsian.setText("");
                }
            }
        });
        binding.btnSubmit.setOnClickListener(v -> {
            if(!binding.tvJawabanIsian.getText().equals("")){
                ArrayList<SoalCTIsian> isians = new ArrayList<SoalCTIsian>(sessionManagement.getSoalCTIsian());
                SoalCTIsian soalCTIsian = new SoalCTIsian(isians.get(sessionManagement.getNoIsian()-1));
                soalCTIsian.setJawaban(String.valueOf(binding.tvJawabanIsian.getText()).trim());
                soalCTIsian.setUser_id(sessionManagement.getId());
                isians.set(sessionManagement.getNoIsian()-1,soalCTIsian);
                sessionManagement.setSoalIsian(isians);
                String toastmessage = "Jawaban telah diubah";
                Toast.makeText(getActivity(), toastmessage, Toast.LENGTH_SHORT).show();
            }
        });
        binding.btnKumpulkan.setOnClickListener(v -> {
            addCTPG();
            addCTIsian();
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        System.out.println("soal isian : "+sessionManagement.getSoalCTIsian());
        ArrayList<SoalCTIsian> isians = new ArrayList<SoalCTIsian>(sessionManagement.getSoalCTIsian());
        SoalCTIsian soalCTIsian = new SoalCTIsian(isians.get(sessionManagement.getNoIsian()-1));
        binding.tvNoSoalIsian.setText(String.valueOf(soalCTIsian.getNumber()+sessionManagement.getNoPg()));
        binding.tvSoalCtIsianMhs.setText(soalCTIsian.getQuestion());
        binding.btnKumpulkan.setVisibility(View.GONE);
        if(sessionManagement.getNoIsian().equals(sessionManagement.getJumlahSoalIsian())){
            binding.btnNext.setEnabled(false);
        }
        String jwb = soalCTIsian.getJawaban();
        if(jwb != null){
            binding.tvJawabanIsian.setText(jwb);
        } else{
            binding.tvJawabanIsian.setText("");
        }
    }

    private void addCTPG() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLADDCTPG,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            if(object.get("status").equals("Success")){
                                Toast.makeText(getActivity(),"Jawaban CT Berhasil Ditambahkan",Toast.LENGTH_LONG).show();
                            } else{
                                Toast.makeText(getActivity(),"Tidak Berhasil Ditambahkan",Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(getActivity(),"masuk catch",Toast.LENGTH_LONG).show();
                        }
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(),"Gagal Ditambahkan",Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                ArrayList<SoalCTPilihanGanda> list = new ArrayList<SoalCTPilihanGanda>(sessionManagement.getSoalCTPg());
                Object[] objArray = list.toArray();
                Gson gson = new Gson();
                String jsonString = gson.toJson(objArray);

                Map<String,String> map = new HashMap<String,String>();
                map.put("data",jsonString);
                System.out.println("Soal CT Isian"+map);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(stringRequest);
    }


    private void addCTIsian() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLADDCTIsian,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            if(object.get("status").equals("Success")){
                                Toast.makeText(getActivity(),"Jawaban CT Berhasil Ditambahkan",Toast.LENGTH_LONG).show();
                            } else{
                                Toast.makeText(getActivity(),"Tidak Berhasil Ditambahkan",Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(getActivity(),"masuk catch",Toast.LENGTH_LONG).show();
                        }
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(),"Gagal Ditambahkan",Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                ArrayList<SoalCTIsian> list = new ArrayList<SoalCTIsian>(sessionManagement.getSoalCTIsian());
                Gson gson = new Gson();
                String jsonString = gson.toJson(list);

                Map<String,String> map = new HashMap<String,String>();
                map.put("data",jsonString);
                System.out.println("Soal CT PG : "+map);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(stringRequest);
    }
}