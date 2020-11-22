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
import com.example.pmb_application.adapter.ForumAdapter;
import com.example.pmb_application.databinding.FragmentKelolaForumDosenPanitiaBinding;
import com.example.pmb_application.entity.Forum;
import com.example.pmb_application.entity.WSResponseForum;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FragmentKelolaForumDosenPanitia extends Fragment implements ForumAdapter.ItemClickListener{
    private FragmentKelolaForumDosenPanitiaBinding binding;
    private ForumAdapter forumAdapter;
    private FragmentDetailForum detailForum;
    String URL = VariabelGlobal.link_ip + "api/forums/";

    public FragmentDetailForum getDetailForum() {
        if(detailForum == null){
            detailForum = new FragmentDetailForum();
        }
        return detailForum;
    }

    public ForumAdapter getForumAdapter() {
        if(forumAdapter == null){
            forumAdapter = new ForumAdapter(this);
        }
        return forumAdapter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadForumData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentKelolaForumDosenPanitiaBinding.inflate(inflater,container,false);
        initComponents();
        return binding.getRoot();
    }

    private void clearField() {
        binding.txtIsiForum.setText("");
        binding.txtNamaForum.setText("");
        binding.txtTanggalForum.setText("");
    }

    private void  initComponents(){
        binding.btnTambahForum.setOnClickListener(v -> addForum());
        binding.rvDataForum.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvDataForum.setAdapter(getForumAdapter());
        binding.srLayoutForum.setOnRefreshListener(()->{
            binding.srLayoutForum.setRefreshing(false);
            loadForumData();
        });
    }

    private void loadForumData() {
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        Uri uri = Uri.parse(URL).buildUpon().build();
        StringRequest request = new StringRequest(Request.Method.GET, uri.toString(), response -> {
            try {
                JSONObject object = new JSONObject(response);
                Gson gson = new Gson();
                WSResponseForum weatherResponse = gson.fromJson(object.toString(), WSResponseForum.class);
                getForumAdapter().changeData(weatherResponse.getData());
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

    private void addForum() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            if(object.get("status").equals("Success")){
                                clearField();
                                Toast.makeText(getActivity(),"Forum Berhasil Ditambahkan",Toast.LENGTH_LONG).show();
                                loadForumData();
                            } else{
                                Toast.makeText(getActivity(),"Forum Gagal Ditambahkan",Toast.LENGTH_LONG).show();
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
                map.put("date",binding.txtTanggalForum.getText().toString().trim());
                map.put("name",binding.txtNamaForum.getText().toString().trim());
                map.put("description",binding.txtIsiForum.getText().toString().trim());
                System.out.println(map);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(stringRequest);

    }

    @Override
    public void itemClicked(Forum forum) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("Forum",forum);
        getDetailForum().setArguments(bundle);

        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout_dosen_panitia,getDetailForum());
        transaction.addToBackStack(null);
        transaction.commit();
    }
}