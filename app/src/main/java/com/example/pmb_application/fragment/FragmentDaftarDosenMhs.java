package com.example.pmb_application.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pmb_application.R;
import com.example.pmb_application.VariabelGlobal;
import com.example.pmb_application.adapter.DosenAdapter;
import com.example.pmb_application.databinding.AlertDialogBinding;
import com.example.pmb_application.databinding.FragmentDaftarDosenMhsBinding;
import com.example.pmb_application.entity.Dosen;
import com.example.pmb_application.entity.WSResponseDosen;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class FragmentDaftarDosenMhs extends Fragment implements DosenAdapter.ItemClickListener{

    private FragmentDaftarDosenMhsBinding binding;
    private AlertDialogBinding binding2;

    String URL = VariabelGlobal.link_ip + "api/lecturers/";
    private DosenAdapter dosenAdapter;


    public DosenAdapter getDosenAdapter() {
        if (dosenAdapter == null){
            dosenAdapter = new DosenAdapter(this);
        }
        return dosenAdapter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadDosenData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDaftarDosenMhsBinding.inflate(inflater, container, false);
        initComponents();
        return binding.getRoot();
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

    @Override
    public void itemClicked(Dosen dosen) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        binding2 = AlertDialogBinding.inflate(getLayoutInflater());
        binding2.tvNamaDosen.setText(dosen.getName());
        binding2.tvNikDosen.setText(dosen.getNip());
        binding2.tvEmailDosen.setText(dosen.getEmail());
        binding2.tvGenderDosen.setText(dosen.getGender());
        builder.setView(binding2.getRoot()).setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create();
        builder.show();
    }


    private void  initComponents(){
        binding.rvDataDaftarDosenMhs.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvDataDaftarDosenMhs.setAdapter(getDosenAdapter());
        binding.srLayoutDaftarDosenMhs.setOnRefreshListener(()->{
            binding.srLayoutDaftarDosenMhs.setRefreshing(false);
            loadDosenData();
        });
    }
}