package com.example.pmb_application.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pmb_application.VariabelGlobal;
import com.example.pmb_application.adapter.KegiatanAdapter;
import com.example.pmb_application.databinding.FragmentKegiatanDosenBinding;
import com.example.pmb_application.databinding.FragmentKegiatanMhsBinding;
import com.example.pmb_application.entity.Kegiatan;
import com.example.pmb_application.entity.WSResponseKegiatan;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class FragmentKegiatanDosen extends Fragment implements KegiatanAdapter.ItemClickListener{
    private FragmentKegiatanDosenBinding binding;

    String URL = VariabelGlobal.link_ip + "api/activities/";
    private KegiatanAdapter kegiatanAdapter;


    public KegiatanAdapter getKegiatanAdapter() {
        if(kegiatanAdapter == null){
            kegiatanAdapter = new KegiatanAdapter(this);
        }
        return kegiatanAdapter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadKegiatanData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentKegiatanDosenBinding.inflate(inflater, container, false);
        initComponents();
        return binding.getRoot();
    }

    private void loadKegiatanData() {
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        Uri uri = Uri.parse(URL).buildUpon().build();
        StringRequest request = new StringRequest(Request.Method.GET, uri.toString(), response -> {
            try {
                JSONObject object = new JSONObject(response);
                Gson gson = new Gson();
                System.out.println("kegiatan");
                System.out.println(object);
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

    private void  initComponents(){
        binding.rvDataHomeDosen.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvDataHomeDosen.setAdapter(getKegiatanAdapter());
        binding.srLayoutHomeDosen.setOnRefreshListener(()->{
            binding.srLayoutHomeDosen.setRefreshing(false);
            loadKegiatanData();
        });
    }

    @Override
    public void itemClicked(Kegiatan kegiatan) {

    }
}
