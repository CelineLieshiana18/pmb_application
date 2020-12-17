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
import com.example.pmb_application.MainActivityDosenPanitia;
import com.example.pmb_application.R;
import com.example.pmb_application.SessionManagement;
import com.example.pmb_application.VariabelGlobal;
import com.example.pmb_application.adapter.SoalCTIsianAdapter;
import com.example.pmb_application.adapter.SoalCTPilihanGandaAdapter;
import com.example.pmb_application.databinding.FragmentIkutiCtPgMhsBinding;
import com.example.pmb_application.databinding.FragmentKelolaCtIsianBinding;
import com.example.pmb_application.entity.Dosen;
import com.example.pmb_application.entity.CT;
import com.example.pmb_application.entity.SoalCTIsian;
import com.example.pmb_application.entity.SoalCTPilihanGanda;
import com.example.pmb_application.entity.WSResponseSoalCTIsian;
import com.example.pmb_application.entity.WSResponseSoalCTPilihanGanda;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FragmentIkutiCTPgMhs extends Fragment {
    private FragmentIkutiCtPgMhsBinding binding;
    private FragmentIkutiCTIsianMhs isian;
    private FragmentCtMhs daftarct;
    private SessionManagement sessionManagement;
    int id;
    //belom
    String URLGETIsian = VariabelGlobal.link_ip + "api/soalIsians/getAllSoalIsianByIdCT/";
    String URLGETPg = VariabelGlobal.link_ip + "api/soalPgs/getAllSoalPgsByIdCT/";
    int soalpg = 1;
    int soalisian = 1;

    public FragmentCtMhs getDaftarct() {
        if(daftarct == null){
            daftarct = new FragmentCtMhs();
        }
        return daftarct;
    }

    public FragmentIkutiCTIsianMhs getIsian() {
        if(isian == null){
            isian = new FragmentIkutiCTIsianMhs();
        }
        return isian;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentIkutiCtPgMhsBinding.inflate(inflater,container,false);
        initComponents();
        return binding.getRoot();
    }

    private void initComponents() {
        binding.btnNext.setOnClickListener(v -> {
            if(sessionManagement.getNoPg().equals(sessionManagement.getJumlahSoalPg())){
                // no terakhir langsung cek isian
                if(sessionManagement.getJumlahSoalIsian() > 0){
                    // kalo ada
                    sessionManagement.setKeteranganSoal("isian");
                    FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_layout,getIsian());
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            }else{
                int currnumber = sessionManagement.getNoIsian();
                sessionManagement.setNoPg(currnumber+1);
                SoalCTPilihanGanda soalPG = new SoalCTPilihanGanda(sessionManagement.getSoalCTPg());
                binding.tvNoSoalPg.setText(soalPG.getNumber());
                binding.tvSoalCtPgMhs.setText(soalPG.getQuestion());
                binding.rbPilihanA.setText(soalPG.getA());
                binding.rbPilihanB.setText(soalPG.getB());
                binding.rbPilihanC.setText(soalPG.getC());
                binding.rbPilihanD.setText(soalPG.getD());
                binding.rbPilihanE.setText(soalPG.getE());
                // kalo soal terakhir dan isian ada
                if(sessionManagement.getJumlahSoalIsian() > 0 && sessionManagement.getNoPg().equals(sessionManagement.getJumlahSoalPg())){
                    binding.btnNext.setText("Soal Isian >");
                }
                // soal terakhir dan isian ga ada
                else if(sessionManagement.getJumlahSoalIsian() < 1 && sessionManagement.getNoPg().equals(sessionManagement.getJumlahSoalPg())){
                    // kalo soal isian ga ada langsung di disabled button nextnya
                    binding.btnNext.setEnabled(false);
                }
            }
        });
        binding.btnBack.setOnClickListener(v -> {
//            if(sessionManagement.getNoPg().equals(1)){
//                binding.btnBack.setEnabled(false);
//            }else{
            int currnumber = sessionManagement.getNoPg();
            sessionManagement.setNoPg(currnumber-1);
            if(sessionManagement.getNoPg().equals(1)){
                binding.btnBack.setEnabled(false);
            }
            SoalCTPilihanGanda soalPG = new SoalCTPilihanGanda(sessionManagement.getSoalCTPg());
            binding.tvNoSoalPg.setText(soalPG.getNumber());
            binding.tvSoalCtPgMhs.setText(soalPG.getQuestion());
            binding.rbPilihanA.setText(soalPG.getA());
            binding.rbPilihanB.setText(soalPG.getB());
            binding.rbPilihanC.setText(soalPG.getC());
            binding.rbPilihanD.setText(soalPG.getD());
            binding.rbPilihanE.setText(soalPG.getE());
//            }
        });
        binding.btnSubmit.setOnClickListener(v -> {
//            if(binding.txtNomor.getText() == null || binding.txtSoal.getText() == null || binding.txtScore.getText() == null){
//                Toast.makeText(getActivity(),"Gagal ditambahkan, Semua data harus terisi",Toast.LENGTH_LONG).show();
//            } else{
//                addSoalIsian();
//            }
        });
    }

    private void loadSoalDataPG() {
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        Uri uri = Uri.parse(URLGETPg).buildUpon().build();
        StringRequest request = new StringRequest(Request.Method.GET, uri.toString(), response -> {
            try {
                JSONObject object = new JSONObject(response);
                Gson gson = new Gson();
//                System.out.println("kegiatan");
//                System.out.println(object);
                WSResponseSoalCTPilihanGanda weatherResponse = gson.fromJson(object.toString(), WSResponseSoalCTPilihanGanda.class);

                //simpen di session
                ArrayList<SoalCTPilihanGanda> pilihanGandas = new ArrayList<SoalCTPilihanGanda>(weatherResponse.getData());
                sessionManagement.setSoalPg(pilihanGandas);
                //end
                if(pilihanGandas == null){
                    soalpg =0;
                } else{
                    int jml = pilihanGandas.size();
                    sessionManagement.setJumlahSoalPg(jml);
                }
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


    private void loadSoalDataIsian() {
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        Uri uri = Uri.parse(URLGETIsian).buildUpon().build();
        StringRequest request = new StringRequest(Request.Method.GET, uri.toString(), response -> {
            try {
                JSONObject object = new JSONObject(response);
                Gson gson = new Gson();
//                System.out.println("kegiatan");
//                System.out.println(object);
                WSResponseSoalCTIsian weatherResponse = gson.fromJson(object.toString(), WSResponseSoalCTIsian.class);

                //simpen di session
                ArrayList<SoalCTIsian> isians = new ArrayList<SoalCTIsian>(weatherResponse.getData());
                sessionManagement.setSoalIsian(isians);
                //end

                if(isians == null){
                    soalisian = 0;
                }else{
                    int jml = isians.size();
                    sessionManagement.setJumlahSoalIsian(jml);
                }
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


    @Override
    public void onStart() {
        super.onStart();
        sessionManagement = new SessionManagement(getContext().getApplicationContext());
        if(getArguments() != null && getArguments().containsKey("CT")){
            CT ct = getArguments().getParcelable("CT");
            if (ct!=null){
                binding.tvJudulCt.setText(ct.getName());

                id = ct.getId();
                URLGETIsian = URLGETIsian + id;
                URLGETPg = URLGETPg + id;

                loadSoalDataPG();
                loadSoalDataIsian();

                if(soalpg == 0 && soalisian == 1){
                    // soal pg ga ada soal isian ada
                    sessionManagement.setKeteranganSoal("isian");
                    FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_layout,getIsian());
                    transaction.addToBackStack(null);
                    transaction.commit();
                } else if(soalpg == 0 && soalisian ==0){
                    // nanti kasih alert dialog soal blm tersedia
                    FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_layout,getDaftarct());
                    transaction.addToBackStack(null);
                    transaction.commit();
                } else{
                    // soal pg ada
                    sessionManagement.setKeteranganSoal("pilihanganda");
                    SoalCTPilihanGanda soalPG = new SoalCTPilihanGanda(sessionManagement.getSoalCTPg());
                    binding.tvNoSoalPg.setText(soalPG.getNumber());
                    binding.tvSoalCtPgMhs.setText(soalPG.getQuestion());
                    binding.rbPilihanA.setText(soalPG.getA());
                    binding.rbPilihanB.setText(soalPG.getB());
                    binding.rbPilihanC.setText(soalPG.getC());
                    binding.rbPilihanD.setText(soalPG.getD());
                    binding.rbPilihanE.setText(soalPG.getE());
                }
            }
        }
    }

}