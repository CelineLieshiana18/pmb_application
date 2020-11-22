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
import com.example.pmb_application.adapter.CTAdapterDosenPanitia;
import com.example.pmb_application.databinding.FragmentKelolaCtDosenPanitiaBinding;
import com.example.pmb_application.entity.CT;
import com.example.pmb_application.entity.WSResponseCT;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FragmentKelolaCTDosenPanitia extends Fragment implements CTAdapterDosenPanitia.ItemClickListener{
    private FragmentKelolaCtDosenPanitiaBinding binding;
    private CTAdapterDosenPanitia ctAdapter;
    private FragmentDetailCT detailCT;
    String URL = VariabelGlobal.link_ip + "api/cts/";

    public FragmentDetailCT getDetailCT() {
        if (detailCT ==null){
            detailCT = new FragmentDetailCT();
        }
        return detailCT;
    }

    public CTAdapterDosenPanitia getCtAdapter() {
        if(ctAdapter == null){
            ctAdapter = new CTAdapterDosenPanitia(this);
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
        binding = FragmentKelolaCtDosenPanitiaBinding.inflate(inflater,container,false);
        initComponents();
        // Inflate the layout for this fragment
        return binding.getRoot();
    }


    @Override
    public void itemClicked(CT ct) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("CT",ct);
        getDetailCT().setArguments(bundle);

        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout_dosen_panitia,getDetailCT());
        transaction.addToBackStack(null);
        transaction.commit();

    }


    private void  initComponents(){
        binding.btnTambahCT.setOnClickListener(v -> addCT());
        binding.rvDataKelolaCt.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvDataKelolaCt.setAdapter(getCtAdapter());
        binding.srLayoutKelolaCt.setOnRefreshListener(()->{
            binding.srLayoutKelolaCt.setRefreshing(false);
            loadCTData();
        });
    }

    private void clearField() {
        binding.txtDeskripsi.setText("");
        binding.txtDurasi.setText("");
        binding.txtJamMulaiCT.setText("");
        binding.txtJamSelesaiCT.setText("");
        binding.txtNamaCT.setText("");
        binding.txtTanggalCT.setText("");
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

    private void addCT() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            if(object.get("status").equals("Success")){
                                clearField();
                                Toast.makeText(getActivity(),"Computational Thinking Berhasil Ditambahkan",Toast.LENGTH_LONG).show();
                                loadCTData();
                            } else{
                                Toast.makeText(getActivity(),"Computational Thinking Gagal Ditambahkan",Toast.LENGTH_LONG).show();
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
                map.put("description",binding.txtDeskripsi.getText().toString().trim());
                map.put("date",binding.txtTanggalCT.getText().toString().trim());
                map.put("start",binding.txtJamMulaiCT.getText().toString().trim());
                map.put("end",binding.txtJamSelesaiCT.getText().toString().trim());
                map.put("duration",binding.txtDurasi.getText().toString().trim());
                map.put("name",binding.txtNamaCT.getText().toString().trim());
                System.out.println(map);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(stringRequest);
    }

}