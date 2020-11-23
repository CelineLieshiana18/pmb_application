package com.example.pmb_application.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pmb_application.VariabelGlobal;
import com.example.pmb_application.adapter.ForumAdapter;
import com.example.pmb_application.databinding.FragmentForumDosenBinding;
import com.example.pmb_application.databinding.FragmentForumMhsBinding;
import com.example.pmb_application.entity.Forum;
import com.example.pmb_application.entity.WSResponseForum;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class FragmentForumDosen extends Fragment implements ForumAdapter.ItemClickListener{
    private FragmentForumDosenBinding binding;
    private ForumAdapter forumAdapter;
    String URL = VariabelGlobal.link_ip + "api/forums/";

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
        binding = FragmentForumDosenBinding.inflate(inflater, container, false);
        initComponents();
        return binding.getRoot();
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

    @Override
    public void itemClicked(Forum forum) {

    }


    private void  initComponents(){
        binding.rvDataDaftarForumDosen.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvDataDaftarForumDosen.setAdapter(getForumAdapter());
        binding.srLayoutDaftarForumDosen.setOnRefreshListener(()->{
            binding.srLayoutDaftarForumDosen.setRefreshing(false);
            loadForumData();
        });
    }
}