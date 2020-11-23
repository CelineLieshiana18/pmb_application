package com.example.pmb_application.fragment;

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
import com.example.pmb_application.R;
import com.example.pmb_application.SessionManagement;
import com.example.pmb_application.VariabelGlobal;
import com.example.pmb_application.adapter.MhsAdapter;
import com.example.pmb_application.databinding.FragmentDaftarTemanMhsBinding;
import com.example.pmb_application.entity.Student;
import com.example.pmb_application.entity.WSResponseLoginStudent;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class FragmentDaftarTemanMhs extends Fragment implements MhsAdapter.ItemClickListener{
    private FragmentDaftarTemanMhsBinding binding;
    String URLGET = VariabelGlobal.link_ip + "api/students/getMahasiswa/";
    private MhsAdapter mahasiswaAdapter;

    public MhsAdapter getMahasiswaAdapter() {
        if(mahasiswaAdapter == null){
            mahasiswaAdapter = new MhsAdapter(this);
        }
        return mahasiswaAdapter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadMhsData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDaftarTemanMhsBinding.inflate(inflater,container,false);
        initComponents();
        return binding.getRoot();
    }


    private void  initComponents(){
        SessionManagement sessionManagement = new SessionManagement(getActivity().getApplicationContext());
        if(sessionManagement.getJabatan().equals("dosen")){
            binding.tvJudulDaftarTeman.setText("Daftar Mahasiswa Baru");
        }
        binding.rvDataDaftarTemanMhs.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvDataDaftarTemanMhs.setAdapter(getMahasiswaAdapter());
        binding.srLayoutDaftarTemanMhs.setOnRefreshListener(()->{
            binding.srLayoutDaftarTemanMhs.setRefreshing(false);
            loadMhsData();
        });
    }

    private void loadMhsData() {
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        Uri uri = Uri.parse(URLGET).buildUpon().build();
        System.out.println(uri);
        StringRequest request = new StringRequest(Request.Method.GET, uri.toString(), response -> {
            try {
                JSONObject object = new JSONObject(response);
                Gson gson = new Gson();
                WSResponseLoginStudent weatherResponse = gson.fromJson(object.toString(), WSResponseLoginStudent.class);
                getMahasiswaAdapter().changeData(weatherResponse.getData());
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
    public void itemClicked(Student student) {

    }
}