package com.example.pmb_application;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pmb_application.adapter.PengumumanAdapter;
import com.example.pmb_application.databinding.FragmentKelolaPengumumanDosenPanitiaBinding;
import com.example.pmb_application.databinding.FragmentPengumumanMhsBinding;
import com.example.pmb_application.entity.Pengumuman;
import com.example.pmb_application.entity.WSResponsePengumuman;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class FragmentPengumumanMhs extends Fragment implements PengumumanAdapter.ItemClickListener{

    private FragmentPengumumanMhsBinding binding;
    private PengumumanAdapter pengumumanAdapter;
    String URL = VariabelGlobal.link_ip + "api/announcements/";

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
        binding = FragmentPengumumanMhsBinding.inflate(inflater, container, false);
        initComponents();
        return binding.getRoot();
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

    private void  initComponents(){
        binding.rvDataPengumumanMhs.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvDataPengumumanMhs.setAdapter(getPengumumanAdapter());
        binding.srLayoutPengumumanMhs.setOnRefreshListener(()->{
            binding.srLayoutPengumumanMhs.setRefreshing(false);
            loadPengumumanData();
        });
    }

    @Override
    public void itemClicked(Pengumuman pengumuman) {

    }
}