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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pmb_application.R;
import com.example.pmb_application.VariabelGlobal;
import com.example.pmb_application.adapter.ForumAdapter;
import com.example.pmb_application.databinding.FragmentForumMhsBinding;
import com.example.pmb_application.entity.Forum;
import com.example.pmb_application.entity.WSResponseForum;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class FragmentForumMhs extends Fragment implements ForumAdapter.ItemClickListener{
    private FragmentForumMhsBinding binding;
    private ForumAdapter forumAdapter;
    String URL = VariabelGlobal.link_ip + "api/forums/";
    private FragmentDetailComment detailComment;

    public FragmentDetailComment getDetailComment() {
        if(detailComment == null){
            detailComment = new FragmentDetailComment();
        }
        return detailComment;
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
        binding = FragmentForumMhsBinding.inflate(inflater, container, false);
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
        Bundle bundle = new Bundle();
        bundle.putParcelable("Forum",forum);
        getDetailComment().setArguments(bundle);

        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout,getDetailComment());
        transaction.addToBackStack(null);
        transaction.commit();

    }


    private void  initComponents(){
        binding.rvDataDaftarForumMhs.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvDataDaftarForumMhs.setAdapter(getForumAdapter());
        binding.srLayoutDaftarForumMhs.setOnRefreshListener(()->{
            binding.srLayoutDaftarForumMhs.setRefreshing(false);
            loadForumData();
        });
    }
}