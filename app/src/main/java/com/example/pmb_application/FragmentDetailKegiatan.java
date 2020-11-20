package com.example.pmb_application;

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
import com.example.pmb_application.databinding.FragmentDetailKegiatanBinding;
import com.example.pmb_application.databinding.FragmentDetailKelolaDosenBinding;
import com.example.pmb_application.databinding.FragmentKelolaKegiatanBinding;
import com.example.pmb_application.entity.Dosen;
import com.example.pmb_application.entity.Kegiatan;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FragmentDetailKegiatan extends Fragment {

    private FragmentKelolaKegiatan kelolaKegiatan;
    private FragmentDetailKegiatanBinding binding;

    String URL = VariabelGlobal.link_ip + "api/activities/";
    int id;

    public FragmentKelolaKegiatan getKelolaKegiatan() {
        if(kelolaKegiatan == null){
            kelolaKegiatan = new FragmentKelolaKegiatan();
        }
        return kelolaKegiatan;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentDetailKegiatanBinding.inflate(inflater,container,false);
        binding.btnUbahKegiatan.setOnClickListener(v -> updateKegiatan());
        return inflater.inflate(R.layout.fragment_detail_kegiatan, container, false);
    }

    private void updateKegiatan() {
        System.out.println(URL);

        StringRequest stringRequest = new StringRequest(Request.Method.PUT, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            if(object.get("status").equals("Success")){
                                Toast.makeText(getActivity(),"Kegiatan Berhasil Diubah",Toast.LENGTH_LONG).show();
                                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                                transaction.replace(R.id.frame_layout_dosen_panitia,getKelolaKegiatan());
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
                        Toast.makeText(getActivity(),error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put("date",binding.txtTanggalKegiatan.getText().toString().trim());
                map.put("start",binding.txtJamMulaiKegiatan.getText().toString().trim());
                map.put("end",binding.txtJamAkhirKegiatan.getText().toString().trim());
                map.put("description",binding.txtIsiKegiatan.getText().toString().trim());
                map.put("place",binding.txtTempatKegiatan.getText().toString().trim());
                map.put("pic",binding.txtPICKegiatan.getText().toString().trim());
                map.put("years_id","1");//harusnya nantidi backend aja
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
        if(getArguments() != null && getArguments().containsKey("Kegiatan")){
            Kegiatan kegiatan = getArguments().getParcelable("Kegiatan");
            if (kegiatan!=null){
                binding.txtIsiKegiatan.setText(kegiatan.getDescription());
                binding.txtJamAkhirKegiatan.setText(kegiatan.getEnd());
                binding.txtJamMulaiKegiatan.setText(kegiatan.getStart());
                binding.txtPICKegiatan.setText(kegiatan.getPic());
                binding.txtTanggalKegiatan.setText(kegiatan.getDate());

                id = kegiatan.getId();
                URL = VariabelGlobal.link_ip + "api/activities/"+id;
            }
        }
    }

}