package com.example.pmb_application.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pmb_application.R;
import com.example.pmb_application.VariabelGlobal;
import com.example.pmb_application.databinding.FragmentDetailTahunBinding;
import com.example.pmb_application.entity.TahunAjaran;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FragmentDetailTahun extends Fragment {
    private FragmentDetailTahunBinding binding;
    private FragmentKelolaTahunAjaran kelolaTahunAjaran;
    int id;
    String URL;

    public FragmentKelolaTahunAjaran getKelolaTahunAjaran() {
        if(kelolaTahunAjaran == null){
            kelolaTahunAjaran = new FragmentKelolaTahunAjaran();
        }
        return kelolaTahunAjaran;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDetailTahunBinding.inflate(inflater,container,false);
        binding.btnUbahTahun.setOnClickListener(v -> updateTahun());
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        if(getArguments() != null && getArguments().containsKey("Tahun")){
            TahunAjaran tahunAjaran = getArguments().getParcelable("Tahun");
            if (tahunAjaran!=null){
                binding.txtNamaTahun.setText(tahunAjaran.getName());
                if(tahunAjaran.getStatus() == 1){
                    binding.rbAktif.setChecked(true);
                    binding.rbTidakAktif.setChecked(false);
                } else{
                    binding.rbAktif.setChecked(false);
                    binding.rbTidakAktif.setChecked(true);
                }
                id = tahunAjaran.getId();
                URL = VariabelGlobal.link_ip + "api/years/"+id;
            }
        }
    }


    private void updateTahun() {
        System.out.println(URL);

        StringRequest stringRequest = new StringRequest(Request.Method.PUT, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            if(object.get("status").equals("Success")){

                                Toast.makeText(getActivity(),"Tahun Ajaran Berhasil Diubah",Toast.LENGTH_LONG).show();
                                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                                transaction.replace(R.id.frame_layout_admin,getKelolaTahunAjaran());
                                transaction.addToBackStack(null);
                                transaction.commit();
                            } else{
                                Toast.makeText(getActivity(),"Tahun Ajaran Gagal Diubah",Toast.LENGTH_LONG).show();
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
                String status = "0";
                if(binding.rbAktif.isChecked()){
                    status = "1";
                }
                Map<String,String> map = new HashMap<String,String>();
                map.put("name",binding.txtNamaTahun.getText().toString().trim());
                map.put("status",status);
                System.out.println("Tahun");
                System.out.println(map);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(stringRequest);

    }
}