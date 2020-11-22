package com.example.pmb_application.fragment;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

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
import com.example.pmb_application.adapter.MhsAdapter;
import com.example.pmb_application.databinding.FragmentKelolaMahasiswaBinding;
import com.example.pmb_application.entity.Student;
import com.example.pmb_application.entity.WSResponseLoginStudent;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FragmentKelolaMahasiswa extends Fragment implements MhsAdapter.ItemClickListener{
    private FragmentKelolaMahasiswaBinding binding;
    private FragmentDetailMahasiswa detailMahasiswa;
    private MhsAdapter mahasiswaAdapter;
    String URLGET = VariabelGlobal.link_ip + "api/students/getMahasiswa/";
    String URL = VariabelGlobal.link_ip + "api/students/";

    public FragmentDetailMahasiswa getDetailPanitia() {
        if (detailMahasiswa == null){
            detailMahasiswa = new FragmentDetailMahasiswa();
        }
        return detailMahasiswa;
    }

    public MhsAdapter getMahasiswaAdapter() {
        if(mahasiswaAdapter == null){
            mahasiswaAdapter = new MhsAdapter(this);
        }
        return mahasiswaAdapter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadMhsData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentKelolaMahasiswaBinding.inflate(inflater,container,false);
        initComponents();
        return binding.getRoot();
    }

    private void  initComponents(){
        binding.btnTambah.setOnClickListener(v -> addMhs());
        binding.rvDataKelolaMahasiswa.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvDataKelolaMahasiswa.setAdapter(getMahasiswaAdapter());
        binding.srLayoutKelolaMahasiswa.setOnRefreshListener(()->{
            binding.srLayoutKelolaMahasiswa.setRefreshing(false);
            loadMhsData();
        });
    }

    private void loadMhsData() {
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        Uri uri = Uri.parse(URLGET).buildUpon().build();
        System.out.println(uri);
        StringRequest request = new StringRequest(Request.Method.GET, uri.toString(), response -> {
            try {
                JSONObject object = new JSONObject(response);
                Gson gson = new Gson();
                WSResponseLoginStudent weatherResponse = gson.fromJson(object.toString(), WSResponseLoginStudent.class);
                getMahasiswaAdapter().changeData(weatherResponse.getData());
                Toast.makeText(getActivity(), "berhasil",Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            Toast.makeText(getActivity(),"error", Toast.LENGTH_SHORT).show();
            error.printStackTrace();
        });
        queue.add(request);
    }

    private void addMhs() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            if(object.get("status").equals("Success")){
                                clearField();
                                Toast.makeText(getActivity(),"Mahasiswa Berhasil Ditambahkan",Toast.LENGTH_LONG).show();
                                loadMhsData();
                            } else{
                                Toast.makeText(getActivity(),"Mahasiswa Gagal Ditambahkan",Toast.LENGTH_LONG).show();
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
                String gender = "Laki-Laki";
                if(binding.rbPerempuan.isChecked()){
                    gender = "Perempuan";
                }
                Map<String,String> map = new HashMap<String,String>();
                map.put("nrp",binding.txtNrp.getText().toString().trim());
                map.put("name",binding.txtNama.getText().toString().trim());
                map.put("password",binding.txtPassword.getText().toString().trim());
                map.put("email",binding.txtEmail.getText().toString().trim());
                map.put("gender",gender);
                System.out.println("MAP Add Student: ");
                System.out.println(map);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void clearField() {
        binding.txtEmail.setText("");
        binding.txtPassword.setText("");
        binding.txtNama.setText("");
        binding.txtNrp.setText("");
        binding.rbPerempuan.setChecked(false);
        binding.rbLakiLaki.setChecked(false);
    }

    @Override
    public void itemClicked(Student student) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("Mahasiswa",student);
        getDetailPanitia().setArguments(bundle);

        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout_dosen_panitia,getDetailPanitia());
        transaction.addToBackStack(null);
        transaction.commit();

    }
}