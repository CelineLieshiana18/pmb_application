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
import com.example.pmb_application.databinding.FragmentGetOneSoalPilihanGandaBinding;
import com.example.pmb_application.entity.SoalCTPilihanGanda;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FragmentGetOneSoalPilihanGanda extends Fragment {
    private FragmentGetOneSoalPilihanGandaBinding binding;
    String URLGET = VariabelGlobal.link_ip + "api/soalPgs/";
    int id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGetOneSoalPilihanGandaBinding.inflate(inflater,container,false);
        binding.btnHapusSoalPG.setOnClickListener(v -> {
            deleteData(id);
        });
        binding.btnUbahSoalPG.setOnClickListener(v -> {
            if(binding.txtNomor.getText().equals("")|| binding.txtSoal.getText().equals("") || binding.txtPilihanA.getText().equals("") || binding.txtPilihanB.getText().equals("")|| binding.txtPilihanC.getText().equals("") || binding.txtPilihanD.getText().equals("") || binding.txtPilihanE.getText().equals("") || binding.rgKey.getCheckedRadioButtonId() != -1){
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
                                Toast.makeText(getActivity(),"Soal Pilihan Ganda Berhasil Diubah",Toast.LENGTH_LONG).show();
                                Bundle data = new Bundle();
                                data.putInt("idCT",id);
                                FragmentKelolaCtPilihanGanda fragment = new FragmentKelolaCtPilihanGanda();
                                fragment.setArguments(data);
                                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_dosen_panitia,fragment).commit();
                            } else{
                                Toast.makeText(getActivity(),"Soal Pilihan Ganda Gagal Diubah",Toast.LENGTH_LONG).show();
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
                map.put("A",binding.txtPilihanA.getText().toString().trim());
                map.put("B",binding.txtPilihanB.getText().toString().trim());
                map.put("C",binding.txtPilihanC.getText().toString().trim());
                map.put("D",binding.txtPilihanD.getText().toString().trim());
                map.put("E",binding.txtPilihanE.getText().toString().trim());
                if(binding.rbKunciA.isChecked()){
                    map.put("key","A");
                } else if(binding.rbKunciB.isChecked()){
                    map.put("key","B");
                } else if(binding.rbKunciC.isChecked()){
                    map.put("key","C");
                } else if(binding.rbKunciD.isChecked()){
                    map.put("key","D");
                } else if(binding.rbKunciE.isChecked()){
                    map.put("key","E");
                }
                map.put("cts_id",String.valueOf(id));
                System.out.println("CT PG");
                System.out.println(map);
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
            SoalCTPilihanGanda pg = getArguments().getParcelable("Soal");
            if (pg!=null){
                binding.txtNomor.setText(pg.getNumber());
                binding.txtSoal.setText(pg.getQuestion());
                binding.txtPilihanA.setText(pg.getA());
                binding.txtPilihanB.setText(pg.getB());
                binding.txtPilihanC.setText(pg.getC());
                binding.txtPilihanD.setText(pg.getD());
                binding.txtPilihanE.setText(pg.getE());
                if(pg.getKey().equals("A")){
                    binding.rbKunciA.setChecked(true);
                } else if(pg.getKey().equals("B")){
                    binding.rbKunciB.setChecked(true);
                } else if(pg.getKey().equals("C")){
                    binding.rbKunciC.setChecked(true);
                } else if(pg.getKey().equals("D")){
                    binding.rbKunciD.setChecked(true);
                } else if(pg.getKey().equals("E")){
                    binding.rbKunciE.setChecked(true);
                }
                id = pg.getId();
                URLGET = URLGET+id;
            }
        }
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
                                FragmentKelolaCtPilihanGanda fragment = new FragmentKelolaCtPilihanGanda();
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


}