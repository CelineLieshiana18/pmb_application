package com.example.pmb_application.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pmb_application.R;
import com.example.pmb_application.VariabelGlobal;
import com.example.pmb_application.adapter.TahunAjaranAdapter;
import com.example.pmb_application.databinding.FragmentKelolaTahunAjaranBinding;
import com.example.pmb_application.entity.TahunAjaran;
import com.example.pmb_application.entity.WSResponseTahun;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FragmentKelolaTahunAjaran extends Fragment implements TahunAjaranAdapter.ItemClickListener{
    private FragmentKelolaTahunAjaranBinding binding;
    private TahunAjaranAdapter tahunAjaranAdapter;
    private FragmentDetailTahun detailTahun;
//    private FragmentDetailKelolaDosen detailDosen;
    String URL = VariabelGlobal.link_ip + "api/years/";

    public FragmentDetailTahun getDetailTahun() {
        if(detailTahun == null){
            detailTahun = new FragmentDetailTahun();
        }
        return detailTahun;
    }

    public TahunAjaranAdapter getTahunAjaranAdapter() {
        if(tahunAjaranAdapter == null){
            tahunAjaranAdapter = new TahunAjaranAdapter(this);
        }
        return tahunAjaranAdapter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadTahunData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentKelolaTahunAjaranBinding.inflate(inflater,container,false);
        initComponents();
        return binding.getRoot();
    }

    private void loadTahunData(){
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        Uri uri = Uri.parse(URL).buildUpon().build();
        System.out.println(uri);
        StringRequest request = new StringRequest(Request.Method.GET, uri.toString(), response -> {
            try {
                JSONObject object = new JSONObject(response);
                Gson gson = new Gson();
                WSResponseTahun weatherResponse = gson.fromJson(object.toString(), WSResponseTahun.class);
                getTahunAjaranAdapter().changeData(weatherResponse.getData());
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


    private void addTahun() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            if(object.get("status").equals("Success")){
                                clearField();
                                Toast.makeText(getActivity(),"Tahun Ajaran Berhasil Ditambahkan",Toast.LENGTH_LONG).show();
                                loadTahunData();
                            } else{
                                Toast.makeText(getActivity(),"Tahun Ajaran Gagal Ditambahkan",Toast.LENGTH_LONG).show();
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
                String status = "Tidak Aktif";
                if(binding.rbAktif.isChecked()){
                    status = "Aktif";
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

    private void  initComponents(){
        binding.btnTambahTahun.setOnClickListener(v -> addTahun());
        binding.rvDataKelolaTahunAjaran.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvDataKelolaTahunAjaran.setAdapter(getTahunAjaranAdapter());
        binding.srLayoutKelolaTahunAjaran.setOnRefreshListener(()->{
            binding.srLayoutKelolaTahunAjaran.setRefreshing(false);
            loadTahunData();
        });
    }

    private void clearField() {
        binding.txtNamaTahun.setText("");
        binding.rbAktif.setChecked(false);
        binding.rbTidakAktif.setChecked(false);
    }

    @Override
    public void itemClicked(TahunAjaran tahunAjaran) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("Tahun",tahunAjaran);
        getDetailTahun().setArguments(bundle);

        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout_admin,getDetailTahun());
        transaction.addToBackStack(null);
        transaction.commit();
    }
}