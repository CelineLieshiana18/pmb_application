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
import com.example.pmb_application.VariabelGlobal;
import com.example.pmb_application.databinding.FragmentDetailCtBinding;
import com.example.pmb_application.entity.CT;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FragmentDetailCT extends Fragment {
    private FragmentDetailCtBinding binding;
    private FragmentKelolaCTDosenPanitia kelolaCT;
    private FragmentKelolaCtIsian ctIsian;
    private FragmentKelolaCtPilihanGanda ctPilihanGanda;
    Bundle bundle;
    String URL = VariabelGlobal.link_ip + "api/cts/";
    int id;

    public FragmentKelolaCtIsian getCtIsian() {
        if(ctIsian == null){
            ctIsian = new FragmentKelolaCtIsian();
        }
        return ctIsian;
    }

    public FragmentKelolaCtPilihanGanda getCtPilihanGanda() {
        if(ctPilihanGanda == null){
            ctPilihanGanda = new FragmentKelolaCtPilihanGanda();
        }
        return ctPilihanGanda;
    }

    public FragmentKelolaCTDosenPanitia getKelolaCT() {
        if (kelolaCT == null){
            kelolaCT = new FragmentKelolaCTDosenPanitia();
        }
        return kelolaCT;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(getArguments() != null && getArguments().containsKey("CT")){
            CT ct = getArguments().getParcelable("CT");
            if (ct!=null){
                binding.txtTanggalCT.setText(ct.getDate());
                binding.txtNamaCT.setText(ct.getName());
                binding.txtJamSelesaiCT.setText(ct.getEnd());
                binding.txtJamMulaiCT.setText(ct.getStart());
                binding.txtDurasi.setText(ct.getDuration());
                binding.txtDeskripsi.setText(ct.getDescription());
                id = ct.getId();
                System.out.println("sending idCT : "+id);
                URL = VariabelGlobal.link_ip + "api/cts/"+id;
                Bundle bundle = new Bundle();
                bundle.putInt("idCT",id);
                binding.btnTambahSoalIsian.setOnClickListener(v -> {
                    getCtIsian().setArguments(bundle);
                    FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_layout_dosen_panitia,getCtIsian());
                    transaction.addToBackStack(null);
                    transaction.commit();
                });
                binding.btnTambahSoalPG.setOnClickListener(v -> {
                    getCtPilihanGanda().setArguments(bundle);
                    FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_layout_dosen_panitia,getCtPilihanGanda());
                    transaction.addToBackStack(null);
                    transaction.commit();
                });
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDetailCtBinding.inflate(inflater,container,false);
        binding.btnUbahCT.setOnClickListener(v -> {
            if(!binding.txtDeskripsi.getText().equals("") || !binding.txtDurasi.getText().equals("") || !binding.txtJamMulaiCT.getText().equals("") || !binding.txtJamSelesaiCT.getText().equals("") || !binding.txtNamaCT.getText().equals("") || !binding.txtTanggalCT.getText().equals("")){
                updateCT();
            } else{
                Toast.makeText(getActivity(),"Semua data wajib diisi",Toast.LENGTH_LONG).show();
            }
        });
        return binding.getRoot();
    }

    private void updateCT() {
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            if(object.get("status").equals("Success")){
                                Toast.makeText(getActivity(),"Computational Thinking Berhasil Diubah",Toast.LENGTH_LONG).show();
                                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                                transaction.replace(R.id.frame_layout_dosen_panitia,getKelolaCT());
                                transaction.addToBackStack(null);
                                transaction.commit();
                            } else{
                                Toast.makeText(getActivity(),"Computational Thinking Gagal Diubah",Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(getActivity(),"masuk catch",Toast.LENGTH_LONG).show();
                        }
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(),"volley error",Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put("description",binding.txtDeskripsi.getText().toString().trim());
                map.put("date",binding.txtTanggalCT.getText().toString().trim());
                map.put("start",binding.txtJamMulaiCT.getText().toString().trim());
                map.put("end",binding.txtJamSelesaiCT.getText().toString().trim());
                map.put("duration",binding.txtDurasi.getText().toString().trim());
                map.put("name",binding.txtNamaCT.getText().toString().trim());
                System.out.println(map);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(stringRequest);
    }
}