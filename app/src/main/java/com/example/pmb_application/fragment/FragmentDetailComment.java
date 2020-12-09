package com.example.pmb_application.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pmb_application.SessionManagement;
import com.example.pmb_application.VariabelGlobal;
import com.example.pmb_application.adapter.CommentAdapter;
import com.example.pmb_application.databinding.FragmentDetailCommentBinding;
import com.example.pmb_application.entity.Comment;
import com.example.pmb_application.entity.Forum;
import com.example.pmb_application.entity.WSResponseComment;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class FragmentDetailComment extends Fragment implements CommentAdapter.ItemClickListener{
    private FragmentDetailCommentBinding binding;
    private CommentAdapter commentAdapter;
    int id;
    String URLGET = VariabelGlobal.link_ip + "api/comments/getAllCommentByIdForum/";
    String URLADD = VariabelGlobal.link_ip + "api/comments/";


    @Override
    public void onStart() {
        super.onStart();
        System.out.println("Forum"+ getArguments().containsKey("Forum"));
        if(getArguments() != null && getArguments().containsKey("Forum")){
            Forum forum = getArguments().getParcelable("Forum");
            if (forum!=null){
                String targetFormat = "dd MMM yyyy";
                String currentFormat = "yyyy-MM-dd";
                String sourceDate = forum.getDate();
                System.out.println("Source Date :"+sourceDate);
                String timezone = "Asia/Jakarta";
                DateFormat srcDf = new SimpleDateFormat(currentFormat);
                srcDf.setTimeZone(TimeZone.getTimeZone(timezone));
                DateFormat destDf = new SimpleDateFormat(targetFormat);
                try {
                    Date date = srcDf.parse(sourceDate);
                    String targetDate = destDf.format(date);
                    System.out.println("Target Date :"+targetDate);
                    binding.txtTanggalForum.setText(targetDate);
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }

                binding.txtNamaForum.setText(forum.getName());
                binding.txtIsiForum.setText(forum.getDescription());
                id = forum.getId();
                System.out.println("Halaman Fragment Detail : "+ id);
                URLGET = URLGET+id;
                loadSoalData();
            }
        }
    }

    public CommentAdapter getCommentAdapter() {
        if(commentAdapter == null){
            commentAdapter = new CommentAdapter(this);
        }
        return commentAdapter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDetailCommentBinding.inflate(inflater,container,false);
        initComponents();
        return binding.getRoot();
    }


    private void clearField() {
        binding.tvKomentar.setText("");
    }


    private void addKomentar() {
        System.out.println("masuk komentar ni");
        System.out.printf(URLADD);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLADD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            if(object.get("status").equals("Success")){
                                clearField();
                                Toast.makeText(getActivity(),"Komentar Berhasil Ditambahkan",Toast.LENGTH_LONG).show();
//                                loadKomentar();
                                loadSoalData();
                            } else{
                                Toast.makeText(getActivity(),"Komentar Gagal Ditambahkan",Toast.LENGTH_LONG).show();
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

                Date date = Calendar.getInstance().getTime();
                SessionManagement sessionManagement = new SessionManagement(getContext().getApplicationContext());

                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String strDate = dateFormat.format(date);
                map.put("date",strDate);
                map.put("description",binding.tvKomentar.getText().toString().trim());
                //id nya 0 terus
                map.put("forums_id",String.valueOf(id));
                map.put("students_id",sessionManagement.getId());
                System.out.println("Komentar");
                System.out.println(map);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void loadSoalData() {
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        Uri uri = Uri.parse(URLGET).buildUpon().build();
        StringRequest request = new StringRequest(Request.Method.GET, uri.toString(), response -> {
            try {
                JSONObject object = new JSONObject(response);
                Gson gson = new Gson();
                WSResponseComment weatherResponse = gson.fromJson(object.toString(), WSResponseComment.class);
                getCommentAdapter().changeData(weatherResponse.getData());
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


    private void  initComponents(){
        binding.rvDataComment.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvDataComment.setAdapter(getCommentAdapter());
        binding.srLayoutComment.setOnRefreshListener(()->{
            binding.srLayoutComment.setRefreshing(false);
//            loadKomentar();
            loadSoalData();
        });

        binding.btnKirim.setOnClickListener(v->{
            if(binding.tvKomentar.getText().equals("")){
                Toast.makeText(getActivity(), "Komentar tidak boleh kosong", Toast.LENGTH_SHORT).show();
            }else{
                addKomentar();
            }
//            loadKomentar();
            loadSoalData();
        });
    }

    @Override
    public void itemClicked(Comment comment) {

    }
}