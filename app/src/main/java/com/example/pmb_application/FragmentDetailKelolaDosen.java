package com.example.pmb_application;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pmb_application.databinding.FragmentDetailKelolaDosenBinding;
import com.example.pmb_application.databinding.FragmentKelolaDosenBinding;
import com.example.pmb_application.entity.Dosen;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FragmentDetailKelolaDosen extends Fragment {

    private FragmentDetailKelolaDosenBinding binding;
    private ArrayList<String> spinList;
    private ArrayAdapter<String> spinAdapter;

    int id;
    String URL = VariabelGlobal.link_ip + "api/lecturer/";
    String gender="";

    public ArrayList<String> getSpinList() {
        if(spinList == null){
            spinList = new ArrayList<>();
            spinList.add("Dekan");
            spinList.add("Wakil Dekan");
            spinList.add("Dosen");
        }
        return spinList;
    }

    public ArrayAdapter<String> getSpinAdapter() {
        if(spinAdapter == null){
            spinAdapter = new ArrayAdapter<String>(getActivity().getBaseContext(),android.R.layout.simple_spinner_item, getSpinList());
            spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        }
        return spinAdapter;
    }


    @Override
    public void onStart() {
        super.onStart();
        if(getArguments() != null && getArguments().containsKey("Dosen")){
            Dosen dosen = getArguments().getParcelable("Dosen");
            if (dosen!=null){
                binding.txtNIPUbahDosen.setText(dosen.getNip());
                binding.txtNamaUbahDosen.setText(dosen.getName());
                binding.txtPasswordUbahDosen.setText(dosen.getPassword());
                binding.txtEmailUbahDosen.setText(dosen.getEmail());
                binding.JabatanSebelumnya.setText(dosen.getJabatan());
                gender = dosen.getGender();
                if(gender.equals("Perempuan")){
                    binding.rbPerempuan.setChecked(true);
                    binding.rbLakiLaki.setChecked(false);
                }else{
                    binding.rbLakiLaki.setChecked(true);
                    binding.rbPerempuan.setChecked(false);
                }
                int status = dosen.getStatus();
                if(status == 0){
                    binding.rbTidakAktif.setChecked(true);
                    binding.rbAktif.setChecked(false);
                }else{
                    binding.rbAktif.setChecked(true);
                    binding.rbTidakAktif.setChecked(false);
                }
                id = dosen.getId();
                URL = VariabelGlobal.link_ip + "api/lecturer/"+id;
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
        binding = FragmentDetailKelolaDosenBinding.inflate(inflater,container,false);
        binding.btnUbahDosen.setOnClickListener(v -> updateDosen());
        binding.spinJabatan.setAdapter(getSpinAdapter());
        // Inflate the layout for this fragment
        return binding.getRoot();
    }


    private void updateDosen() {
        System.out.println(URL);

        StringRequest stringRequest = new StringRequest(Request.Method.PUT, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            if(object.get("status").equals("Success")){
                                System.out.println("object");
                                System.out.println(object);
                                Toast.makeText(getActivity(),"Dosen Berhasil Ditambahkan",Toast.LENGTH_LONG).show();
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
                        Toast.makeText(getActivity(),error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String gender = "Laki-Laki";
                if(binding.rbPerempuan.isChecked()){
                    gender = "Perempuan";
                }
                String stat = "1";
                if(binding.rbTidakAktif.isChecked()){
                    stat = "0";
                }
                Map<String,String> map = new HashMap<String,String>();
                map.put("nip",binding.txtNIPUbahDosen.getText().toString().trim());
                map.put("name",binding.txtNamaUbahDosen.getText().toString().trim());
                map.put("password",binding.txtPasswordUbahDosen.getText().toString().trim());
                map.put("email",binding.txtEmailUbahDosen.getText().toString().trim());
                map.put("status",stat);
                map.put("gender",gender);
                map.put("jabatan",binding.spinJabatan.getSelectedItem().toString());
                System.out.println(map);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(stringRequest);
    }
}