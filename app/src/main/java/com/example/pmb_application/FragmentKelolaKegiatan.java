package com.example.pmb_application;

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
import com.example.pmb_application.adapter.KegiatanAdapter;
import com.example.pmb_application.databinding.FragmentKelolaKegiatanBinding;
import com.example.pmb_application.entity.Kegiatan;
import com.example.pmb_application.entity.WSResponseKegiatan;
import com.example.pmb_application.entity.WSResponsePengumuman;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FragmentKelolaKegiatan extends Fragment implements KegiatanAdapter.ItemClickListener{
    private FragmentKelolaKegiatanBinding binding;
    private KegiatanAdapter kegiatanAdapter;
    private FragmentDetailKegiatan detailKegiatan;
    String URL = VariabelGlobal.link_ip + "api/activities/";

    public FragmentDetailKegiatan getDetailKegiatan() {
        if(detailKegiatan == null){
            detailKegiatan = new FragmentDetailKegiatan();
        }
        return detailKegiatan;
    }

    public KegiatanAdapter getKegiatanAdapter() {
        if(kegiatanAdapter == null){
            kegiatanAdapter = new KegiatanAdapter(this);
        }
        return kegiatanAdapter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        loadKegiatanData();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentKelolaKegiatanBinding.inflate(inflater, container, false);
        initComponents();
        return binding.getRoot();
    }

    private void clearField() {
        binding.txtIsiKegiatan.setText("");
        binding.txtJamAkhirKegiatan.setText("");
        binding.txtJamMulaiKegiatan.setText("");
        binding.txtTanggalKegiatan.setText("");
        binding.txtPICKegiatan.setText("");
        binding.txtTempatKegiatan.setText("");
    }

    private void  initComponents(){
        binding.btnTambahKegiatan.setOnClickListener(v -> addKegiatan());
        binding.rvDataKegiatan.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvDataKegiatan.setAdapter(getKegiatanAdapter());
        binding.srLayoutKegiatan.setOnRefreshListener(()->{
            binding.srLayoutKegiatan.setRefreshing(false);
            loadKegiatanData();
        });
    }

    private void loadKegiatanData() {
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        Uri uri = Uri.parse(URL).buildUpon().build();
        StringRequest request = new StringRequest(Request.Method.GET, uri.toString(), response -> {
            try {
                JSONObject object = new JSONObject(response);
                Gson gson = new Gson();
                WSResponseKegiatan weatherResponse = gson.fromJson(object.toString(), WSResponseKegiatan.class);
                getKegiatanAdapter().changeData(weatherResponse.getData());
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

    private void addKegiatan() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            if(object.get("status").equals("Success")){
                                clearField();
                                Toast.makeText(getActivity(),"Pengumuman Berhasil Ditambahkan",Toast.LENGTH_LONG).show();
                                loadKegiatanData();
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
                map.put("date",binding.txtTanggalKegiatan.getText().toString().trim());
                map.put("start",binding.txtJamMulaiKegiatan.getText().toString().trim());
                map.put("end",binding.txtJamAkhirKegiatan.getText().toString().trim());
                map.put("description",binding.txtIsiKegiatan.getText().toString().trim());
                map.put("place",binding.txtTempatKegiatan.getText().toString().trim());
                map.put("pic",binding.txtPICKegiatan.getText().toString().trim());
                map.put("years_id","1");//harusnya nantidi backend aja
                System.out.println(map);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(stringRequest);
    }

    @Override
    public void itemClicked(Kegiatan kegiatan) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("Kegiatan",kegiatan);
        getDetailKegiatan().setArguments(bundle);

        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout_dosen_panitia,getDetailKegiatan());
        transaction.addToBackStack(null);
        transaction.commit();
    }
}