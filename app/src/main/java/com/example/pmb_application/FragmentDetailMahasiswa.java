package com.example.pmb_application;

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
import com.example.pmb_application.databinding.FragmentDetailMahasiswaBinding;
import com.example.pmb_application.entity.Dosen;
import com.example.pmb_application.entity.Student;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FragmentDetailMahasiswa extends Fragment {
    private FragmentKelolaMahasiswa kelolaMahasiswa;
    private FragmentDetailMahasiswaBinding binding;

    int id;
    String URL = VariabelGlobal.link_ip + "api/students/";
    String gender="";
    String pwd;

    public FragmentKelolaMahasiswa getKelolaMahasiswa() {
        if(kelolaMahasiswa == null){
            kelolaMahasiswa = new FragmentKelolaMahasiswa();
        }
        return kelolaMahasiswa;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(getArguments() != null && getArguments().containsKey("Mahasiswa")){
            Student student = getArguments().getParcelable("Mahasiswa");
            if (student!=null){
                binding.txtNrp.setText(student.getNrp());
                binding.txtNamaDetailMhs.setText(student.getName());
                binding.txtPasswordDetailMhs.setText(student.getPassword());
                binding.txtEmailDetailMhs.setText(student.getEmail());
                pwd = student.getPassword();
                gender = student.getGender();
                if(gender.equals("Perempuan")){
                    binding.rbPerempuanDetailMhs.setChecked(true);
                    binding.rbLakiLakiDetailMhs.setChecked(false);
                }else{
                    binding.rbLakiLakiDetailMhs.setChecked(true);
                    binding.rbPerempuanDetailMhs.setChecked(false);
                }
                id = student.getId();
                URL = VariabelGlobal.link_ip + "api/students/"+id;
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
        binding = FragmentDetailMahasiswaBinding.inflate(inflater,container,false);
        binding.btnUbahDetailMhs.setOnClickListener(v -> {
            if(!binding.txtNrp.getText().equals("") || !binding.txtNamaDetailMhs.getText().equals("") || !binding.txtEmailDetailMhs.getText().equals("") || !binding.txtPasswordDetailMhs.getText().equals("")){
                updateMahasiswa();
            } else{
                Toast.makeText(getActivity(),"Semua data wajib diisi",Toast.LENGTH_LONG).show();
            }
        });
        return binding.getRoot();
    }


    private void updateMahasiswa() {
        System.out.println(URL);

        StringRequest stringRequest = new StringRequest(Request.Method.PUT, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            if(object.get("status").equals("Success")){
                                Toast.makeText(getActivity(),"Mahasiswa Berhasil Diubah",Toast.LENGTH_LONG).show();
                                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                                transaction.replace(R.id.frame_layout_dosen_panitia,getKelolaMahasiswa());
                                transaction.addToBackStack(null);
                                transaction.commit();
                            } else{
                                Toast.makeText(getActivity(),"Mahasiswa Gagal Diubah",Toast.LENGTH_LONG).show();
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
                if(binding.rbPerempuanDetailMhs.isChecked()){
                    gender = "Perempuan";
                }
                Map<String,String> map = new HashMap<String,String>();
                map.put("nrp",binding.txtNrp.getText().toString().trim());
                map.put("name",binding.txtNamaDetailMhs.getText().toString().trim());
                if(!binding.txtPasswordDetailMhs.equals(pwd)){
                    map.put("password",binding.txtPasswordDetailMhs.getText().toString().trim());
                }else{
                    map.put("password","-1");
                }
                map.put("email",binding.txtEmailDetailMhs.getText().toString().trim());
                map.put("gender",gender);
                System.out.println("MAP Update Mahasiswa: ");
                System.out.println(map);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(stringRequest);
    }
}