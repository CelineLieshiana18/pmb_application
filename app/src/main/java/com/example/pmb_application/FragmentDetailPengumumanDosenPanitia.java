package com.example.pmb_application;

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
import com.example.pmb_application.databinding.FragmentDetailKelolaDosenBinding;
import com.example.pmb_application.databinding.FragmentDetailPengumumanDosenPanitiaBinding;
import com.example.pmb_application.entity.Dosen;
import com.example.pmb_application.entity.Pengumuman;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FragmentDetailPengumumanDosenPanitia extends Fragment {
    private FragmentKelolaKegiatanPengumumanForum kelolaPengumuman;
    private FragmentDetailPengumumanDosenPanitiaBinding binding;

    int id;
    String URL = VariabelGlobal.link_ip + "api/announcement/";

    public FragmentKelolaKegiatanPengumumanForum getKelolaPengumuman() {
        if (kelolaPengumuman == null){
            kelolaPengumuman = new FragmentKelolaKegiatanPengumumanForum();
        }
        return kelolaPengumuman;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(getArguments() != null && getArguments().containsKey("Pengumuman")){
            Pengumuman pengumuman = getArguments().getParcelable("Pengumuman");
            if (pengumuman!=null){
                binding.txtIsiPengumuman.setText(pengumuman.getDeskripsi());
                binding.txtTanggalPengumuman.setText(pengumuman.getDate());
                id = pengumuman.getId();
                URL = VariabelGlobal.link_ip + "api/announcement/"+id;
                System.out.println("URL P");
                System.out.println(URL);
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDetailPengumumanDosenPanitiaBinding.inflate(inflater,container,false);
        binding.btnUbahPengumuman.setOnClickListener(v -> updatePengumuman());
        return binding.getRoot();
    }


    private void updatePengumuman() {
        System.out.println(URL);

        StringRequest stringRequest = new StringRequest(Request.Method.PUT, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            if(object.get("status").equals("Success")){
                                Toast.makeText(getActivity(),"Dosen Berhasil Diubah",Toast.LENGTH_LONG).show();
                                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                                transaction.replace(R.id.frame_layout_dosen_panitia,getKelolaPengumuman());
                                transaction.addToBackStack(null);
                                transaction.commit();
                            } else{
                                Toast.makeText(getActivity(),"Tidak Berhasil Ditambahkan",Toast.LENGTH_LONG).show();
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
                map.put("date",binding.txtTanggalPengumuman.getText().toString().trim());
                map.put("description",binding.txtIsiPengumuman.getText().toString().trim());
                SessionManagement sessionManagement = new SessionManagement(getActivity().getBaseContext());
                map.put("students_id","1"); // dummy soalnya panit nya blm ada baru dosen
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(stringRequest);
    }
}
