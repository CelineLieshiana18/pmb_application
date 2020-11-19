package com.example.pmb_application;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
import com.example.pmb_application.adapter.DosenAdapter;
import com.example.pmb_application.databinding.FragmentKelolaDosenBinding;
import com.example.pmb_application.entity.Dosen;
import com.example.pmb_application.entity.WSResponseDosen;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentKelolaDosen extends Fragment implements DosenAdapter.ItemClickListener{

    private ArrayList<String> spinList;
    private ArrayAdapter<String> spinAdapter;
    private DosenAdapter dosenAdapter;
    private FragmentKelolaDosenBinding binding;
    private FragmentDetailKelolaDosen detailDosen;
    @BindView(R.id.sr_layout_kelola_dosen)
    SwipeRefreshLayout srLayout;
    @BindView(R.id.rv_data_kelola_dosen)
    RecyclerView rvData;
    String URL = VariabelGlobal.link_ip + "api/lecturer/";



    public ArrayList<String> getSpinList() {
        if(spinList == null){
            spinList = new ArrayList<>();
            spinList.add("Dekan");
            spinList.add("Wakil Dekan");
            spinList.add("Dosen");
        }
        return spinList;
    }

    public ArrayAdapter<String> getSpinAdapter() {
        if(spinAdapter == null){
            spinAdapter = new ArrayAdapter<String>(getActivity().getBaseContext(),android.R.layout.simple_spinner_item, getSpinList());
            spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        }
        return spinAdapter;
    }

    public FragmentDetailKelolaDosen getDetailDosen() {
        if(detailDosen == null){
            detailDosen= new FragmentDetailKelolaDosen();
        }
        return detailDosen;
    }

    public DosenAdapter getDosenAdapter() {
        if (dosenAdapter == null){
            dosenAdapter = new DosenAdapter(this);
        }
        return dosenAdapter;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        loadDosenData();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentKelolaDosenBinding.inflate(inflater,container,false);
        initComponents();
        return binding.getRoot();
    }

    @Override
    public void itemClicked(Dosen dosen) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("Dosen",dosen);
        getDetailDosen().setArguments(bundle);

        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout_dosen_panitia,getDetailDosen());
        transaction.addToBackStack(null);
        transaction.commit();
    }


    private void loadDosenData(){
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        Uri uri = Uri.parse(URL).buildUpon().build();
        System.out.println(uri);
        StringRequest request = new StringRequest(Request.Method.GET, uri.toString(), response -> {
            try {
                JSONObject object = new JSONObject(response);
                Gson gson = new Gson();
                WSResponseDosen weatherResponse = gson.fromJson(object.toString(), WSResponseDosen.class);
                getDosenAdapter().changeData(weatherResponse.getData());
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


    private void addDosen() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            if(object.get("status").equals("Success")){
                                System.out.println("object");
                                System.out.println(object);
                                clearField();
                                Toast.makeText(getActivity(),"Dosen Berhasil Ditambahkan",Toast.LENGTH_LONG).show();
                                loadDosenData();
                            } else{
                                Toast.makeText(getActivity(),"Tidak Berhasil Ditambahkan",Toast.LENGTH_LONG).show();
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
                map.put("nip",binding.txtNik.getText().toString().trim());
                map.put("name",binding.txtNama.getText().toString().trim());
                map.put("password",binding.txtPassword.getText().toString().trim());
                map.put("email",binding.txtEmail.getText().toString().trim());
                map.put("status","1");
                map.put("genre",gender);
                map.put("jabatan",binding.spinJabatan.getSelectedItem().toString());
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void  initComponents(){
        binding.spinJabatan.setAdapter(getSpinAdapter());
        binding.btnTambahDosen.setOnClickListener(v -> addDosen());
        binding.rvDataKelolaDosen.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvDataKelolaDosen.setAdapter(getDosenAdapter());
        binding.srLayoutKelolaDosen.setOnRefreshListener(()->{
            binding.srLayoutKelolaDosen.setRefreshing(false);
            loadDosenData();
        });
    }

    private void clearField() {
        binding.txtEmail.setText("");
        binding.txtPassword.setText("");
        binding.txtNama.setText("");
        binding.txtNik.setText("");
        binding.spinJabatan.setSelection(0);
        binding.rbPerempuan.setChecked(false);
        binding.rbLakiLaki.setChecked(false);
    }
}