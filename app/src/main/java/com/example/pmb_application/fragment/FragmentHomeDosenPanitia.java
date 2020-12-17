package com.example.pmb_application.fragment;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

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
import com.example.pmb_application.databinding.FragmentHomeDosenPanitiaBinding;
import com.example.pmb_application.entity.Dosen;
import com.example.pmb_application.entity.Student;
import com.example.pmb_application.entity.WSResponseDosen;
import com.example.pmb_application.entity.WSResponseLoginStudent;
import com.example.pmb_application.entity.WSResponseMhs;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FragmentHomeDosenPanitia extends Fragment {
    private FragmentHomeDosenPanitiaBinding binding;
    String URLDosen = VariabelGlobal.link_ip + "api/lecturers/";
    String URLPanitia = VariabelGlobal.link_ip + "api/students/getPanitia/";
    String URLMhs = VariabelGlobal.link_ip + "api/students/getMahasiswa/";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        binding = FragmentHomeDosenPanitiaBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        SessionManagement sessionManagement = new SessionManagement(getContext().getApplicationContext());
        binding.txtawaldosen.setText(sessionManagement.getSession());
        binding.tvNumberDosen.setText("15");
        loadDosenData();
        loadMhsData();
        loadPanitiaData();
        System.out.println("masuk sini kok");
        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    private void loadDosenData(){
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        Uri uri = Uri.parse(URLDosen).buildUpon().build();
        System.out.println(uri);
        StringRequest request = new StringRequest(Request.Method.GET, uri.toString(), response -> {
            try {
                JSONObject object = new JSONObject(response);
                Gson gson = new Gson();
                WSResponseDosen weatherResponse = gson.fromJson(object.toString(), WSResponseDosen.class);
                ArrayList<Dosen> dosens = new ArrayList<Dosen>(weatherResponse.getData());
                binding.tvNumberDosen.setText(String.valueOf(dosens.size()));
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


    private void loadPanitiaData() {
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        Uri uri = Uri.parse(URLPanitia).buildUpon().build();
        System.out.println(uri);
        StringRequest request = new StringRequest(Request.Method.GET, uri.toString(), response -> {
            try {
                JSONObject object = new JSONObject(response);
                Gson gson = new Gson();
                WSResponseMhs weatherResponse = gson.fromJson(object.toString(), WSResponseMhs.class);

                ArrayList<Student> students = new ArrayList<Student>(weatherResponse.getData());
                binding.tvNumberPanitia.setText(String.valueOf(students.size()));

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


    private void loadMhsData() {
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        Uri uri = Uri.parse(URLMhs).buildUpon().build();
        System.out.println(uri);
        StringRequest request = new StringRequest(Request.Method.GET, uri.toString(), response -> {
            try {
                JSONObject object = new JSONObject(response);
                Gson gson = new Gson();
                WSResponseLoginStudent weatherResponse = gson.fromJson(object.toString(), WSResponseLoginStudent.class);
                ArrayList<Student> students = new ArrayList<Student>(weatherResponse.getData());
                binding.tvNumberMhs.setText(String.valueOf(students.size()));
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

}