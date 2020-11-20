package com.example.pmb_application;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pmb_application.adapter.PengumumanAdapter;
import com.example.pmb_application.databinding.FragmentKelolaPengumumanDosenPanitiaBinding;
import com.example.pmb_application.entity.Pengumuman;
import com.example.pmb_application.entity.WSResponseDosen;
import com.example.pmb_application.entity.WSResponsePengumuman;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FragmentKelolaPengumumanDosenPanitia extends Fragment implements PengumumanAdapter.ItemClickListener{
    private FragmentKelolaPengumumanDosenPanitiaBinding binding;
    private PengumumanAdapter pengumumanAdapter;
    String URL = VariabelGlobal.link_ip + "api/announcement/";

    public PengumumanAdapter getPengumumanAdapter() {
        if(pengumumanAdapter == null){
            pengumumanAdapter = new PengumumanAdapter(this);
        }
        return pengumumanAdapter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadPengumumanData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentKelolaPengumumanDosenPanitiaBinding.inflate(inflater, container, false);
        initComponents();
        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    private void  initComponents(){
        binding.btnTambahPengumuman.setOnClickListener(v -> addPengumuman());
        binding.rvDataPengumuman.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvDataPengumuman.setAdapter(getPengumumanAdapter());
        binding.srLayoutPengumuman.setOnRefreshListener(()->{
            binding.srLayoutPengumuman.setRefreshing(false);
            loadPengumumanData();
        });
    }

    private void loadPengumumanData() {
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        Uri uri = Uri.parse(URL).buildUpon().build();
        StringRequest request = new StringRequest(Request.Method.GET, uri.toString(), response -> {
            try {
                JSONObject object = new JSONObject(response);
                Gson gson = new Gson();
                WSResponsePengumuman weatherResponse = gson.fromJson(object.toString(), WSResponsePengumuman.class);
                getPengumumanAdapter().changeData(weatherResponse.getData());
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

    private void addPengumuman() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            if(object.get("status").equals("Success")){
                                clearField();
                                Toast.makeText(getActivity(),"Pengumuman Berhasil Ditambahkan",Toast.LENGTH_LONG).show();
                                loadPengumumanData();
                            } else{
                                Toast.makeText(getActivity(),"Pengumuman Gagal Ditambahkan",Toast.LENGTH_LONG).show();
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
                Map<String,String> map = new HashMap<String,String>();
                map.put("date",binding.txtTanggalPengumuman.getText().toString().trim());
                map.put("description",binding.txtIsiPengumuman.getText().toString().trim());
                SessionManagement sessionManagement = new SessionManagement(getActivity().getBaseContext());
                map.put("students_id","1");
                System.out.println(map);
//                map.put("students_id",sessionManagement.getId());
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(stringRequest);
    }


    private void clearField() {
        binding.txtIsiPengumuman.setText("");
        binding.txtTanggalPengumuman.setText("");
    }

    @Override
    public void itemClicked(Pengumuman pengumuman) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("Pengumuman",pengumuman);
//        getDetailDosen().setArguments(bundle);

//        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.frame_layout_dosen_panitia,getDetailDosen());
//        transaction.addToBackStack(null);
//        transaction.commit();
    }
}