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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pmb_application.R;
import com.example.pmb_application.VariabelGlobal;
import com.example.pmb_application.adapter.CTAdapterMhs;
import com.example.pmb_application.databinding.FragmentCtMhsBinding;
import com.example.pmb_application.entity.CT;
import com.example.pmb_application.entity.WSResponseCT;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class FragmentCtMhs extends Fragment implements CTAdapterMhs.ItemClickListener{
    private FragmentCtMhsBinding binding;
    private FragmentIkutiCTPgMhs ikutiCT;

    String URL = VariabelGlobal.link_ip + "api/cts/";
    private CTAdapterMhs ctAdapter;

    public FragmentIkutiCTPgMhs getIkutiCT() {
        if(ikutiCT == null){
            ikutiCT = new FragmentIkutiCTPgMhs();
        }
        return ikutiCT;
    }

    public CTAdapterMhs getCtAdapter() {
        if(ctAdapter == null){
            ctAdapter = new CTAdapterMhs(this);
        }
        return ctAdapter;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadCTData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCtMhsBinding.inflate(inflater,container,false);
        initComponents();
        return binding.getRoot();
    }


    private void  initComponents(){
        binding.rvDataDaftarPanitiaMhs.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvDataDaftarPanitiaMhs.setAdapter(getCtAdapter());
        binding.srLayoutDaftarPanitiaMhs.setOnRefreshListener(()->{
            binding.srLayoutDaftarPanitiaMhs.setRefreshing(false);
            loadCTData();
        });
    }

    private void loadCTData() {
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        Uri uri = Uri.parse(URL).buildUpon().build();
        System.out.println(uri);
        StringRequest request = new StringRequest(Request.Method.GET, uri.toString(), response -> {
            try {
                JSONObject object = new JSONObject(response);
                Gson gson = new Gson();
                WSResponseCT weatherResponse = gson.fromJson(object.toString(), WSResponseCT.class);
                getCtAdapter().changeData(weatherResponse.getData());
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

    @Override
    public void itemClicked(CT ct) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("CT",ct);
        getIkutiCT().setArguments(bundle);

        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout,getIkutiCT());
        transaction.addToBackStack(null);
        transaction.commit();
    }
}