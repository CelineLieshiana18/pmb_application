package com.example.pmb_application.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pmb_application.R;
import com.example.pmb_application.VariabelGlobal;
import com.example.pmb_application.databinding.FragmentDetailRoleBinding;
import com.example.pmb_application.entity.Role;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FragmentDetailRole extends Fragment {
    private FragmentDetailRoleBinding binding;
    private FragmentKelolaPenggunaDosenPanitia kelolaPengguna;
    int id;
    String URL;

    public FragmentKelolaPenggunaDosenPanitia getKelolaPengguna() {
        if(kelolaPengguna == null){
            kelolaPengguna = new FragmentKelolaPenggunaDosenPanitia();
        }
        return kelolaPengguna;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDetailRoleBinding.inflate(inflater,container,false);
        binding.btnUbahRole.setOnClickListener(v -> updateRole());
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        if(getArguments() != null && getArguments().containsKey("Role")){
            Role role = getArguments().getParcelable("Role");
            if (role!=null){
                binding.txtNamaRole.setText(role.getName());
                id = role.getId();
                URL = VariabelGlobal.link_ip + "api/roles/"+id;
            }
        }
    }


    private void updateRole() {
        System.out.println(URL);

        StringRequest stringRequest = new StringRequest(Request.Method.PUT, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            if(object.get("status").equals("Success")){
                                Toast.makeText(getActivity(),"Role Berhasil Diubah",Toast.LENGTH_LONG).show();
                                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                                transaction.replace(R.id.frame_layout_admin,getKelolaPengguna());
                                transaction.addToBackStack(null);
                                transaction.commit();
                            } else{
                                Toast.makeText(getActivity(),"Role Gagal Diubah",Toast.LENGTH_LONG).show();
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
                map.put("name",binding.txtNamaRole.getText().toString().trim());
                System.out.println(map);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(stringRequest);

    }
}