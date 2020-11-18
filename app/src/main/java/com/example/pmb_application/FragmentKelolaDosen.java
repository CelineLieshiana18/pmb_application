package com.example.pmb_application;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import com.example.pmb_application.adapter.DosenAdapter;
import com.example.pmb_application.entity.Dosen;
import com.example.pmb_application.entity.WSResponseDosen;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentKelolaDosen extends Fragment implements DosenAdapter.ItemClickListener{


    @BindView(R.id.sr_layout_kelola_dosen)
    SwipeRefreshLayout srLayout;
    @BindView(R.id.rv_data_kelola_dosen)
    RecyclerView rvData;
    private DosenAdapter dosenAdapter;
    private VariabelGlobal variabelGlobal;
    String URL = VariabelGlobal.link_ip + "api/lecturer/";

    public DosenAdapter getDosenAdapter() {
        if (dosenAdapter == null){
            dosenAdapter = new DosenAdapter(this);
        }
        return dosenAdapter;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        loadDosenData();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kelola_dosen,container,false);
        ButterKnife.bind(this,view);
        rvData.setLayoutManager(new LinearLayoutManager(getContext()));
//        rvData.setAdapter(getDosenAdapter());
        return view;
    }

    @Override
    public void itemClicked(Dosen dosen) {

    }


    private void loadDosenData(){
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        Uri uri = Uri.parse(URL).buildUpon().build();
        System.out.println(uri);
        StringRequest request = new StringRequest(Request.Method.GET, uri.toString(), response -> {
            try {
                JSONObject object = new JSONObject(response);
                Gson gson = new Gson();
                System.out.println("object");
                System.out.println(object);
                WSResponseDosen weatherResponse = gson.fromJson(object.toString(), WSResponseDosen.class);
                System.out.println("object data");
                System.out.println(weatherResponse);
//                getDosenAdapter().changeData(weatherResponse.getData());
//                System.out.println(getDosenAdapter());
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