package com.example.pmb_application.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pmb_application.R;
import com.example.pmb_application.VariabelGlobal;
import com.example.pmb_application.databinding.FragmentGetOneSoalIsianBinding;
import com.example.pmb_application.entity.SoalCTIsian;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FragmentGetOneSoalIsian extends Fragment {
    private FragmentGetOneSoalIsianBinding binding;
    String URLGET = VariabelGlobal.link_ip + "soalPgs/getAllSoalPgsByIdCT/";
    String URL = VariabelGlobal.link_ip + "api/soalPgs/";
    int id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentGetOneSoalIsianBinding.inflate(inflater,container,false);
        binding.btnHapusCTIsian.setOnClickListener(v -> {
            deleteData(id);
        });
        binding.btnUbahCTIsian.setOnClickListener(v -> {
            if(binding.txtNomor.getText() == null || binding.txtSoal.getText() == null || binding.txtScore.getText() == null){
                Toast.makeText(getActivity(),"Gagal ditambahkan, Semua data harus terisi",Toast.LENGTH_LONG).show();
            } else{
                updateSoal();
            }
        });
        return binding.getRoot();
    }


    private void updateSoal() {
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, URLGET,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            if(object.get("status").equals("Success")){
                                Toast.makeText(getActivity(),"Soal Isian Berhasil Diubah",Toast.LENGTH_LONG).show();
                                Bundle data = new Bundle();
                                data.putInt("idCT",id);
                                FragmentKelolaCtIsian fragment = new FragmentKelolaCtIsian();
                                fragment.setArguments(data);
                                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_dosen_panitia,fragment).commit();
                            } else{
                                Toast.makeText(getActivity(),"Soal Isian Gagal Diubah",Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(getActivity(),"masuk catch",Toast.LENGTH_LONG).show();
                        }
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(),error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put("number",binding.txtNomor.getText().toString().trim());
                map.put("question",binding.txtSoal.getText().toString().trim());
                map.put("score",binding.txtScore.getText().toString().trim());
                map.put("cts_id",String.valueOf(id));
                System.out.println("CT Isian");
                System.out.println(map);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(stringRequest);
    }


    private void deleteData(int idsoal) {
        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, URLGET,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            if(object.get("status").equals("Success")){
                                Toast.makeText(getActivity(),"Soal Pilihan Ganda Berhasil Dihapus",Toast.LENGTH_LONG).show();
                                Bundle data = new Bundle();
                                data.putInt("idCT",id);
                                FragmentKelolaCtIsian fragment = new FragmentKelolaCtIsian();
                                fragment.setArguments(data);
                                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_dosen_panitia,fragment).commit();
                            } else{
                                Toast.makeText(getActivity(),"Soal Pilihan Ganda Gagal Dihapus",Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(getActivity(),"masuk catch",Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(),"Gagal Dihapus",Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put("id",String.valueOf(idsoal));
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(stringRequest);
    }


    @Override
    public void onStart() {
        super.onStart();
        if(getArguments() != null && getArguments().containsKey("Soal")){
            SoalCTIsian isian = getArguments().getParcelable("Soal");
            if (isian!=null){
                binding.txtNomor.setText(isian.getNumber());
                binding.txtSoal.setText(isian.getQuestion());
                binding.txtScore.setText(isian.getScore());
                id = isian.getId();
                URLGET = URLGET+id;
            }
        }
    }




}