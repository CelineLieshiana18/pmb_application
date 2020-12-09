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
import com.example.pmb_application.adapter.SoalCTPilihanGandaAdapter;
import com.example.pmb_application.databinding.FragmentKelolaCtPilihanGandaBinding;
import com.example.pmb_application.entity.SoalCTPilihanGanda;
import com.example.pmb_application.entity.WSResponseSoalCTPilihanGanda;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FragmentKelolaCtPilihanGanda extends Fragment implements SoalCTPilihanGandaAdapter.ItemClickListener{
    private FragmentKelolaCtPilihanGandaBinding binding;
    private SoalCTPilihanGandaAdapter soalCTPilihanGandaAdapter;
    private FragmentGetOneSoalPilihanGanda pilihanGanda;
    int id;
    String URLGET = VariabelGlobal.link_ip + "api/soalPgs/getAllSoalPgsByIdCT/";
    String URL = VariabelGlobal.link_ip + "api/soalPgs/";

    public FragmentGetOneSoalPilihanGanda getPilihanGanda() {
        if(pilihanGanda == null){
            pilihanGanda = new FragmentGetOneSoalPilihanGanda();
        }
        return pilihanGanda;
    }

    public SoalCTPilihanGandaAdapter getSoalCTPilihanGandaAdapter() {
        if(soalCTPilihanGandaAdapter == null){
            soalCTPilihanGandaAdapter = new SoalCTPilihanGandaAdapter(this);
        }
        return soalCTPilihanGandaAdapter;
    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle bundle = this.getArguments();
        assert bundle != null;
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
        binding = FragmentKelolaCtPilihanGandaBinding.inflate(inflater,container,false);
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
                WSResponseSoalCTPilihanGanda weatherResponse = gson.fromJson(object.toString(), WSResponseSoalCTPilihanGanda.class);
                getSoalCTPilihanGandaAdapter().changeData(weatherResponse.getData());
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
        binding.rvDataKelolaCtPg.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvDataKelolaCtPg.setAdapter(getSoalCTPilihanGandaAdapter());
        binding.srLayoutKelolaCtPg.setOnRefreshListener(()->{
            binding.srLayoutKelolaCtPg.setRefreshing(false);
            loadSoalData();
        });
        binding.btnTambahSoalPG.setOnClickListener(v -> {
            if(binding.txtNomor.getText().equals("")|| binding.txtSoal.getText().equals("") || binding.txtPilihanA.getText().equals("") || binding.txtPilihanB.getText().equals("")|| binding.txtPilihanC.getText().equals("") || binding.txtPilihanD.getText().equals("") || binding.txtPilihanE.getText().equals("")){
                Toast.makeText(getActivity(),"Gagal ditambahkan, Semua data harus terisi",Toast.LENGTH_LONG).show();
            } else{
                addSoalPG();
            }
        });
    }


    private void addSoalPG() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            if(object.get("status").equals("Success")){
                                clearField();
                                Toast.makeText(getActivity(),"Soal Pilihan Ganda Berhasil Ditambahkan",Toast.LENGTH_LONG).show();
                                loadSoalData();
                            } else{
                                Toast.makeText(getActivity(),"Soal Pilihan Ganda Gagal Ditambahkan",Toast.LENGTH_LONG).show();
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
                map.put("A",binding.txtPilihanA.getText().toString().trim());
                map.put("B",binding.txtPilihanB.getText().toString().trim());
                map.put("C",binding.txtPilihanC.getText().toString().trim());
                map.put("D",binding.txtPilihanD.getText().toString().trim());
                map.put("E",binding.txtPilihanE.getText().toString().trim());
                if(binding.rbKunciA.isChecked()){
                    map.put("key","A");
                } else if(binding.rbKunciB.isChecked()){
                    map.put("key","B");
                } else if(binding.rbKunciC.isChecked()){
                    map.put("key","C");
                } else if(binding.rbKunciD.isChecked()){
                    map.put("key","D");
                } else if(binding.rbKunciE.isChecked()){
                    map.put("key","E");
                }
                map.put("cts_id",String.valueOf(id));
                System.out.println("CT PG");
                System.out.println(map);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void clearField() {
        binding.txtNomor.setText("");
        binding.txtPilihanA.setText("");
        binding.txtPilihanB.setText("");
        binding.txtPilihanC.setText("");
        binding.txtPilihanD.setText("");
        binding.txtPilihanE.setText("");
        binding.txtSoal.setText("");
    }


    @Override
    public void itemClicked(SoalCTPilihanGanda soalCTPilihanGanda) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("Soal",soalCTPilihanGanda);
        getPilihanGanda().setArguments(bundle);

        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout_dosen_panitia,getPilihanGanda());
        transaction.addToBackStack(null);
        transaction.commit();
    }
}