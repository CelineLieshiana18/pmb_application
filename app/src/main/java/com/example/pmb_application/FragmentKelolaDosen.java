package com.example.pmb_application;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pmb_application.Adapter.DosenAdapter;
import com.example.pmb_application.entity.Dosen;
import com.example.pmb_application.entity.WSResponse;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;

public class FragmentKelolaDosen extends Fragment implements DosenAdapter.ItemClickListener{


    @BindView(R.id.sr_layout_daftar_dosen_mhs)
    SwipeRefreshLayout srLayout;
    @BindView(R.id.rv_data_daftar_dosen_mhs)
    RecyclerView rvData;
    private DosenAdapter dosenAdapter;

    public DosenAdapter getDosenAdapter() {
        if (dosenAdapter == null){
            dosenAdapter = new DosenAdapter(this);
        }
        return dosenAdapter;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_kelola_dosen, container, false);
    }

    @Override
    public void itemClicked(Dosen dosen) {

    }


    private void loadDosenData(){
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        Uri uri = Uri.parse("http://192.168.100.6:8090/api/lecturer/").buildUpon().build();
        StringRequest request = new StringRequest(Request.Method.GET, uri.toString(), response -> {
            try {
                JSONObject object = new JSONObject(response);
                Gson gson = new Gson();
                WSResponse weatherResponse = gson.fromJson(object.toString(), WSResponse.class);
                getDosenAdapter().changeData(weatherResponse.getDatas());
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