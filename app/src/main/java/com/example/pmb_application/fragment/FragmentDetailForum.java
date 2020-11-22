package com.example.pmb_application.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

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
import com.example.pmb_application.databinding.FragmentDetailForumBinding;
import com.example.pmb_application.entity.Forum;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FragmentDetailForum extends Fragment {
    private FragmentDetailForumBinding binding;
    private FragmentKelolaKegiatanPengumumanForum kelolaForum;
    String URL = VariabelGlobal.link_ip + "api/forums/";
    int id;

    public FragmentKelolaKegiatanPengumumanForum getKelolaForum() {
        if(kelolaForum == null){
            kelolaForum = new FragmentKelolaKegiatanPengumumanForum();
        }
        return kelolaForum;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDetailForumBinding.inflate(inflater,container,false);
        binding.btnUbahForum.setOnClickListener(v -> updateForum());
        return binding.getRoot();
    }

    private void updateForum() {
        System.out.println(URL);

        StringRequest stringRequest = new StringRequest(Request.Method.PUT, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            if(object.get("status").equals("Success")){
                                Toast.makeText(getActivity(),"Forum Berhasil Diubah",Toast.LENGTH_LONG).show();
                                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                                transaction.replace(R.id.frame_layout_dosen_panitia,getKelolaForum());
                                transaction.addToBackStack(null);
                                transaction.commit();
                            } else{
                                Toast.makeText(getActivity(),"Tidak Berhasil Diubah",Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(getActivity(),"masuk catch",Toast.LENGTH_LONG).show();
                        }
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(),"volley error",Toast.LENGTH_LONG).show();
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
    public void onStart() {
        super.onStart();
        if(getArguments() != null && getArguments().containsKey("Forum")){
            Forum forum = getArguments().getParcelable("Forum");
            if (forum!=null){
                binding.txtIsiForum.setText(forum.getDescription());
                binding.txtNamaForum.setText(forum.getName());
                binding.txtTanggalForum.setText(forum.getDate());
                id = forum.getId();
                URL = VariabelGlobal.link_ip + "api/forums/"+id;
            }
        }
    }

}