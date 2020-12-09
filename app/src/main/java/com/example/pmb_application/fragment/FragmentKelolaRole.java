package com.example.pmb_application.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pmb_application.R;
import com.example.pmb_application.VariabelGlobal;
import com.example.pmb_application.adapter.RoleAdapter;
import com.example.pmb_application.databinding.FragmentKelolaRoleBinding;
import com.example.pmb_application.entity.Role;
import com.example.pmb_application.entity.WSResponseRole;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FragmentKelolaRole extends Fragment implements RoleAdapter.ItemClickListener{
    private FragmentDetailRole detailRole;
    private FragmentKelolaRoleBinding binding;
    private RoleAdapter roleAdapter;
    String URL = VariabelGlobal.link_ip + "api/roles/";

    public FragmentDetailRole getDetailRole() {
        if(detailRole == null){
            detailRole = new FragmentDetailRole();
        }
        return detailRole;
    }

    public RoleAdapter getRoleAdapter() {
        if(roleAdapter == null){
            roleAdapter = new RoleAdapter(this);
        }
        return roleAdapter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadRoleData();
    }

    private void loadRoleData() {
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        Uri uri = Uri.parse(URL).buildUpon().build();
        System.out.println(uri);
        StringRequest request = new StringRequest(Request.Method.GET, uri.toString(), response -> {
            try {
                JSONObject object = new JSONObject(response);
                Gson gson = new Gson();
                WSResponseRole weatherResponse = gson.fromJson(object.toString(), WSResponseRole.class);
                getRoleAdapter().changeData(weatherResponse.getData());
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentKelolaRoleBinding.inflate(inflater,container,false);
        initComponent();
        return binding.getRoot();
    }

    private void initComponent() {
        binding.btnTambahRole.setOnClickListener(v -> addRole());
        binding.rvDataKelolaRole.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvDataKelolaRole.setAdapter(getRoleAdapter());
        binding.srLayoutKelolaRole.setOnRefreshListener(()->{
            binding.srLayoutKelolaRole.setRefreshing(false);
            loadRoleData();
        });
    }

    private void clearField() {
        binding.txtNamaRole.setText("");
    }

    private void addRole() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            if(object.get("status").equals("Success")){
                                clearField();
                                Toast.makeText(getActivity(),"Role Berhasil Ditambahkan",Toast.LENGTH_LONG).show();
                                loadRoleData();
                            } else{
                                Toast.makeText(getActivity(),"Role Gagal Ditambahkan",Toast.LENGTH_LONG).show();
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
                map.put("name",binding.txtNamaRole.getText().toString().trim());
                System.out.println("Role");
                System.out.println(map);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(stringRequest);
    }

    @Override
    public void itemClicked(Role role) {

        Bundle bundle = new Bundle();
        bundle.putParcelable("Role",role);
        getDetailRole().setArguments(bundle);

        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout_admin,getDetailRole());
        transaction.addToBackStack(null);
        transaction.commit();

    }
}