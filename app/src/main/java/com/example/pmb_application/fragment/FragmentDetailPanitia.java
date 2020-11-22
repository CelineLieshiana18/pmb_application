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
import com.example.pmb_application.databinding.FragmentDetailPanitiaBinding;
import com.example.pmb_application.entity.Student;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FragmentDetailPanitia extends Fragment {
    private FragmentDetailPanitiaBinding binding;
    private FragmentKelolaPenggunaDosenPanitia kelolaPanitia;
    String URL = VariabelGlobal.link_ip + "api/students/";
    String pwd;
    String gender;
    int id;


    @Override
    public void onStart() {
        super.onStart();
        if(getArguments() != null && getArguments().containsKey("Panitia")){
            Student student = getArguments().getParcelable("Panitia");
            if (student!=null){
                binding.txtNrp.setText(student.getNrp());
                binding.txtNama.setText(student.getName());
                binding.txtPassword.setText(student.getPassword());
                binding.txtEmail.setText(student.getEmail());
                pwd = student.getPassword();
                gender = student.getGender();
                if(gender.equals("Perempuan")){
                    binding.rbPerempuan.setChecked(true);
                    binding.rbLakiLaki.setChecked(false);
                }else{
                    binding.rbLakiLaki.setChecked(true);
                    binding.rbPerempuan.setChecked(false);
                }
                id = student.getId();
                URL = VariabelGlobal.link_ip + "api/students/"+id;
            }
        }
    }


    public FragmentKelolaPenggunaDosenPanitia getKelolaPanitia() {
        if(kelolaPanitia == null){
            kelolaPanitia = new FragmentKelolaPenggunaDosenPanitia();
        }
        return kelolaPanitia;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDetailPanitiaBinding.inflate(inflater,container,false);

        binding.btnUbahDetailPanitia.setOnClickListener(v -> {
            if(!binding.txtNrp.getText().equals("") || !binding.txtNama.getText().equals("") || !binding.txtEmail.getText().equals("") || !binding.txtPassword.getText().equals("")){
                updatePanitia();
            } else{
                Toast.makeText(getActivity(),"Semua data wajib diisi",Toast.LENGTH_LONG).show();
            }
        });
        return binding.getRoot();
    }

    private void updatePanitia() {
        System.out.println(URL);

        StringRequest stringRequest = new StringRequest(Request.Method.PUT, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            if(object.get("status").equals("Success")){
                                Toast.makeText(getActivity(),"Panitia Berhasil Diubah",Toast.LENGTH_LONG).show();
                                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                                transaction.replace(R.id.frame_layout_dosen_panitia,getKelolaPanitia());
                                transaction.addToBackStack(null);
                                transaction.commit();
                            } else{
                                Toast.makeText(getActivity(),"Panitia Gagal Diubah",Toast.LENGTH_LONG).show();
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
                Map<String,String> map = new HashMap<String,String>();
                map.put("nrp",binding.txtNrp.getText().toString().trim());
                map.put("name",binding.txtNama.getText().toString().trim());
                if(!binding.txtPassword.equals(pwd)){
                    map.put("password",binding.txtPassword.getText().toString().trim());
                }else{
                    map.put("password","-1");
                }
                map.put("email",binding.txtEmail.getText().toString().trim());
                map.put("gender",gender);
                System.out.println("MAP Update Panitia: ");
                System.out.println(map);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(stringRequest);
    }
}