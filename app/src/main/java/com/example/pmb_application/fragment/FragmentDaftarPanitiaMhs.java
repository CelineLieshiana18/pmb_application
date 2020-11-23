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
import com.example.pmb_application.VariabelGlobal;
import com.example.pmb_application.adapter.PanitiaAdapter;
import com.example.pmb_application.databinding.FragmentDaftarPanitiaMhsBinding;
import com.example.pmb_application.databinding.FragmentKelolaPanitiaBinding;
import com.example.pmb_application.entity.Student;
import com.example.pmb_application.entity.WSResponseMhs;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class FragmentDaftarPanitiaMhs extends Fragment implements PanitiaAdapter.ItemClickListener{
    private FragmentDaftarPanitiaMhsBinding binding;
    private PanitiaAdapter panitiaAdapter;
    String URLGET = VariabelGlobal.link_ip + "api/students/getPanitia/";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadPanitiaData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDaftarPanitiaMhsBinding.inflate(inflater,container,false);
        initComponents();
        return binding.getRoot();
    }

    private void  initComponents(){
        binding.rvDataDaftarPanitiaMhs.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvDataDaftarPanitiaMhs.setAdapter(getPanitiaAdapter());
        binding.srLayoutDaftarPanitiaMhs.setOnRefreshListener(()->{
            binding.srLayoutDaftarPanitiaMhs.setRefreshing(false);
            loadPanitiaData();
        });
    }

    public PanitiaAdapter getPanitiaAdapter() {
        if (panitiaAdapter == null){
            panitiaAdapter = new PanitiaAdapter(this);
        }
        return panitiaAdapter;
    }


    private void loadPanitiaData() {
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        Uri uri = Uri.parse(URLGET).buildUpon().build();
        System.out.println(uri);
        StringRequest request = new StringRequest(Request.Method.GET, uri.toString(), response -> {
            try {
                JSONObject object = new JSONObject(response);
                Gson gson = new Gson();
                WSResponseMhs weatherResponse = gson.fromJson(object.toString(), WSResponseMhs.class);
                getPanitiaAdapter().changeData(weatherResponse.getData());
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