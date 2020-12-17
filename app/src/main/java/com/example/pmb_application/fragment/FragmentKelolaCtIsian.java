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
import com.example.pmb_application.adapter.SoalCTIsianAdapter;
import com.example.pmb_application.databinding.FragmentKelolaCtIsianBinding;
import com.example.pmb_application.entity.SoalCTIsian;
import com.example.pmb_application.entity.WSResponseSoalCTIsian;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FragmentKelolaCtIsian extends Fragment implements SoalCTIsianAdapter.ItemClickListener {
    private FragmentKelolaCtIsianBinding binding;
    private SoalCTIsianAdapter soalCTIsianAdapter;
    private FragmentGetOneSoalIsian soalIsian;
    int id;
    String URLGET = VariabelGlobal.link_ip + "api/soalIsians/getAllSoalIsianByIdCT/";
    String URL = VariabelGlobal.link_ip + "api/soalIsians/";

    public FragmentGetOneSoalIsian getSoalIsian() {
        if(soalIsian == null){
            soalIsian = new FragmentGetOneSoalIsian();
        }
        return soalIsian;
    }

    public SoalCTIsianAdapter getSoalCTIsianAdapter() {
        if(soalCTIsianAdapter == null){
            soalCTIsianAdapter = new SoalCTIsianAdapter(this);
        }
        return soalCTIsianAdapter;
    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle bundle = this.getArguments();
        System.out.println("send idCT : "+bundle.getInt("idCT"));
        id = bundle.getInt("idCT");
        URLGET = URLGET + id;
        loadSoalData();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentKelolaCtIsianBinding.inflate(inflater,container,false);
        initComponents();
        return binding.getRoot();
    }


    private void loadSoalData() {
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        Uri uri = Uri.parse(URLGET).buildUpon().build();
        StringRequest request = new StringRequest(Request.Method.GET, uri.toString(), response -> {
            try {
                JSONObject object = new JSONObject(response);
                Gson gson = new Gson();
                System.out.println("kegiatan");
                System.out.println(object);
                WSResponseSoalCTIsian weatherResponse = gson.fromJson(object.toString(), WSResponseSoalCTIsian.class);
                getSoalCTIsianAdapter().changeData(weatherResponse.getData());
                Toast.makeText(getActivity(), "berhasil", Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            Toast.makeText(getActivity(),"error", Toast.LENGTH_SHORT).show();
            error.printStackTrace();
        });
        queue.add(request);
    }


    private void addSoalIsian() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            if(object.get("status").equals("Success")){
                                clearField();
                                Toast.makeText(getActivity(),"Soal Isian Berhasil Ditambahkan",Toast.LENGTH_LONG).show();
                                loadSoalData();
                            } else{
                                Toast.makeText(getActivity(),"Soal Isian Gagal Ditambahkan",Toast.LENGTH_LONG).show();
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
                map.put("number",binding.txtNomor.getText().toString().trim());
                map.put("question",binding.txtSoal.getText().toString().trim());
                map.put("score",binding.txtScore.getText().toString().trim());
                map.put("cts_id",String.valueOf(id));
                System.out.println("CT Isian");
                System.out.println(map);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void clearField() {
        binding.txtNomor.setText("");
        binding.txtScore.setText("");
        binding.txtSoal.setText("");
    }


    private void  initComponents(){
        binding.rvDataKelolaCtIsian.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvDataKelolaCtIsian.setAdapter(getSoalCTIsianAdapter());
        binding.srLayoutKelolaCtIsian.setOnRefreshListener(()->{
            binding.srLayoutKelolaCtIsian.setRefreshing(false);
            loadSoalData();
        });
        binding.btnTambahCTIsian.setOnClickListener(v -> {
            if(binding.txtNomor.getText() == null || binding.txtSoal.getText() == null || binding.txtScore.getText() == null){
                Toast.makeText(getActivity(),"Gagal ditambahkan, Semua data harus terisi",Toast.LENGTH_LONG).show();
            } else{
                addSoalIsian();
            }
        });
    }

    @Override
    public void itemClicked(SoalCTIsian soalCTIsian) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("Soal",soalCTIsian);
        getSoalIsian().setArguments(bundle);

        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout_dosen_panitia,getSoalIsian());
        transaction.addToBackStack(null);
        transaction.commit();
    }
}