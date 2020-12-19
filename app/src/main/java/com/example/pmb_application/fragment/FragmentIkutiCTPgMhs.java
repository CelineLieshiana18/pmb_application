package com.example.pmb_application.fragment;

import android.content.Intent;
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
            if(sessionManagement.getNoPg().equals(sessionManagement.getJumlahSoalPg()-1) && sessionManagement.getNoIsian().equals(-1)){
                //kalo di soal pg terakhir dan ga ad soal isian
                binding.btnKumpulkan.setVisibility(View.VISIBLE);
            }
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
            }
            else{
                binding.btnBack.setEnabled(true);
                int currnumber = sessionManagement.getNoIsian();
                sessionManagement.setNoPg(currnumber+1);

                ArrayList<SoalCTPilihanGanda> pilihanGandas = new ArrayList<SoalCTPilihanGanda>(sessionManagement.getSoalCTPg());
                SoalCTPilihanGanda soalCTPilihanGanda = new SoalCTPilihanGanda(pilihanGandas.get(sessionManagement.getNoPg()-1));
                binding.tvNoSoalPg.setText(String.valueOf(soalCTPilihanGanda.getNumber()));
                binding.tvSoalCtPgMhs.setText(soalCTPilihanGanda.getQuestion());
                binding.rbPilihanA.setText(soalCTPilihanGanda.getA());
                binding.rbPilihanB.setText(soalCTPilihanGanda.getB());
                binding.rbPilihanC.setText(soalCTPilihanGanda.getC());
                binding.rbPilihanD.setText(soalCTPilihanGanda.getD());
                binding.rbPilihanE.setText(soalCTPilihanGanda.getE());

                if(sessionManagement.getJumlahSoalIsian() > 0 && sessionManagement.getNoPg().equals(sessionManagement.getJumlahSoalPg())){
                    binding.btnNext.setText("Soal Isian");
                }
                // soal terakhir dan isian ga ada
                else if(sessionManagement.getJumlahSoalIsian() < 1 && sessionManagement.getNoPg().equals(sessionManagement.getJumlahSoalPg())){
                    // kalo soal isian ga ada langsung di disabled button nextnya
                    binding.btnNext.setEnabled(false);
                }
                String jwb = soalCTPilihanGanda.getJawaban();
                System.out.println("jawaban : "+jwb);
                if(jwb != null){
                    if(jwb.equalsIgnoreCase("A")){
                        binding.rbPilihanA.setChecked(true);
                    } else if(jwb.equalsIgnoreCase("B")){
                        binding.rbPilihanB.setChecked(true);
                    } else if(jwb.equalsIgnoreCase("C")){
                        binding.rbPilihanC.setChecked(true);
                    } else if(jwb.equalsIgnoreCase("D")){
                        binding.rbPilihanD.setChecked(true);
                    } else if(jwb.equalsIgnoreCase("E")){
                        binding.rbPilihanE.setChecked(true);
                    }
                } else{
                    binding.rbPilihanA.setChecked(false);
                    binding.rbPilihanB.setChecked(false);
                    binding.rbPilihanC.setChecked(false);
                    binding.rbPilihanD.setChecked(false);
                    binding.rbPilihanE.setChecked(false);
                }
            }
        });
        binding.btnBack.setOnClickListener(v -> {
            binding.btnNext.setText("Soal Berikutnya");
            System.out.println("no pg sekarang di btn back: "+ sessionManagement.getNoPg());
            int currnumber = sessionManagement.getNoPg();
            sessionManagement.setNoPg(currnumber-1);
            if(sessionManagement.getNoPg().equals(1)){
                binding.btnBack.setEnabled(false);
            }

            System.out.println(sessionManagement.getNoPg());

            ArrayList<SoalCTPilihanGanda> pilihanGandas = new ArrayList<SoalCTPilihanGanda>(sessionManagement.getSoalCTPg());
            SoalCTPilihanGanda soalCTPilihanGanda = new SoalCTPilihanGanda(pilihanGandas.get(sessionManagement.getNoPg()-1));
            binding.tvNoSoalPg.setText(String.valueOf(soalCTPilihanGanda.getNumber()));
            binding.tvSoalCtPgMhs.setText(soalCTPilihanGanda.getQuestion());
            binding.rbPilihanA.setText(soalCTPilihanGanda.getA());
            binding.rbPilihanB.setText(soalCTPilihanGanda.getB());
            binding.rbPilihanC.setText(soalCTPilihanGanda.getC());
            binding.rbPilihanD.setText(soalCTPilihanGanda.getD());
            binding.rbPilihanE.setText(soalCTPilihanGanda.getE());
            String jwb = soalCTPilihanGanda.getJawaban();
            System.out.println("Jawaban "+ jwb + " no : "+ sessionManagement.getNoPg());
            if(jwb != null){
                if(jwb.equalsIgnoreCase("A")){
                    binding.rbPilihanA.setChecked(true);
                } else if(jwb.equalsIgnoreCase("B")){
                    binding.rbPilihanB.setChecked(true);
                } else if(jwb.equalsIgnoreCase("C")){
                    binding.rbPilihanC.setChecked(true);
                } else if(jwb.equalsIgnoreCase("D")){
                    binding.rbPilihanD.setChecked(true);
                } else if(jwb.equalsIgnoreCase("E")){
                    binding.rbPilihanE.setChecked(true);
                }
            }else{
                binding.rbPilihanA.setChecked(false);
                binding.rbPilihanB.setChecked(false);
                binding.rbPilihanC.setChecked(false);
                binding.rbPilihanD.setChecked(false);
                binding.rbPilihanE.setChecked(false);
            }
        });
        binding.btnSubmit.setOnClickListener(v -> {
            String jawaban = "A";
            if(binding.rbPilihanB.isChecked()){
                jawaban = "B";
            } else if(binding.rbPilihanC.isChecked()){
                jawaban = "C";
            } else if(binding.rbPilihanD.isChecked()){
                jawaban = "D";
            } else if(binding.rbPilihanE.isChecked()){
                jawaban = "E";
            }
            ArrayList<SoalCTPilihanGanda> pilihanGandas = new ArrayList<SoalCTPilihanGanda>(sessionManagement.getSoalCTPg());
            SoalCTPilihanGanda soalCTPilihanGanda = new SoalCTPilihanGanda(pilihanGandas.get(sessionManagement.getNoPg()-1));
            System.out.println("no pg sekarang : "+ sessionManagement.getNoPg());
            soalCTPilihanGanda.setJawaban(jawaban);
            soalCTPilihanGanda.setUser_id(sessionManagement.getId());
            pilihanGandas.set(sessionManagement.getNoPg()-1,soalCTPilihanGanda);
            sessionManagement.setSoalPg(pilihanGandas);
            System.out.println("no pg sekarang ke 2: "+ sessionManagement.getNoPg());
            String toastmessage = "Jawaban "+jawaban+" telah dipilih";
            Toast.makeText(getActivity(), toastmessage, Toast.LENGTH_SHORT).show();
        });
    }

    private void loadSoalDataPG() {
        System.out.println("URL PG : "+ URLGETPg);
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        Uri uri = Uri.parse(URLGETPg).buildUpon().build();
        StringRequest request = new StringRequest(Request.Method.GET, uri.toString(), response -> {
            try {
                JSONObject object = new JSONObject(response);
                Gson gson = new Gson();
                WSResponseSoalCTPilihanGanda weatherResponse = gson.fromJson(object.toString(), WSResponseSoalCTPilihanGanda.class);

                System.out.println("objek"+ weatherResponse);

                //simpen di session
                ArrayList<SoalCTPilihanGanda> pilihanGandas2 = new ArrayList<SoalCTPilihanGanda>(weatherResponse.getData());
                System.out.println("masuk load soal data PG");
                if(sessionManagement.getSoalCTPg()==null){
                    sessionManagement.setSoalPg(pilihanGandas2);
                    sessionManagement.setNoPg(1);
                }else{
                    sessionManagement.setNoPg(1);
                }
                //end

                if(pilihanGandas2.size() == 0){
                    soalpg =0;
                } else{
                    int jml = pilihanGandas2.size();
                    sessionManagement.setJumlahSoalPg(jml);
                }

                // kalo di simpen d bwh load di onstart kaya diprosesnya sejalan sama load data jadi error karna blm keload dr server data soal nya
                if(soalpg == 1){
                    // soal pg ada
                    sessionManagement.setKeteranganSoal("pilihanganda");
//                    System.out.println("no pg : "+sessionManagement.getNoPg());

                    ArrayList<SoalCTPilihanGanda> pilihanGandas = new ArrayList<SoalCTPilihanGanda>(sessionManagement.getSoalCTPg());
                    SoalCTPilihanGanda soalCTPilihanGanda = new SoalCTPilihanGanda(pilihanGandas.get(sessionManagement.getNoPg()-1));
                    binding.tvNoSoalPg.setText(String.valueOf(soalCTPilihanGanda.getNumber()));
                    binding.tvSoalCtPgMhs.setText(soalCTPilihanGanda.getQuestion());
                    binding.rbPilihanA.setText(soalCTPilihanGanda.getA());
                    binding.rbPilihanB.setText(soalCTPilihanGanda.getB());
                    binding.rbPilihanC.setText(soalCTPilihanGanda.getC());
                    binding.rbPilihanD.setText(soalCTPilihanGanda.getD());
                    binding.rbPilihanE.setText(soalCTPilihanGanda.getE());
                    String jwb = soalCTPilihanGanda.getJawaban();
                    if(jwb != null){
                        if(jwb.equalsIgnoreCase("A")){
                            binding.rbPilihanA.setChecked(true);
                        } else if(jwb.equalsIgnoreCase("B")){
                            binding.rbPilihanB.setChecked(true);
                        } else if(jwb.equalsIgnoreCase("C")){
                            binding.rbPilihanC.setChecked(true);
                        } else if(jwb.equalsIgnoreCase("D")){
                            binding.rbPilihanD.setChecked(true);
                        } else if(jwb.equalsIgnoreCase("E")){
                            binding.rbPilihanE.setChecked(true);
                        }
                    }
                    binding.btnBack.setEnabled(false);
                    int no = soalCTPilihanGanda.getNumber();
                    System.out.println("no skrg : "+ no + " ,size : "+ pilihanGandas.size());

                    //error
                    System.out.println("jumlah soal isian : "+  sessionManagement.getJumlahSoalIsian());
                    Integer jml = sessionManagement.getJumlahSoalIsian();
                    if(jml > 0 && no == pilihanGandas.size()){
                        binding.btnNext.setText("Soal Isian");
                    }
//                     soal terakhir dan isian ga ada
                    else if(sessionManagement.getJumlahSoalIsian() < 1 && sessionManagement.getNoPg().equals(sessionManagement.getJumlahSoalPg())){
                        // kalo soal isian ga ada langsung di disabled button nextnya
                        binding.btnNext.setEnabled(false);
                    }
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
                WSResponseSoalCTIsian weatherResponse = gson.fromJson(object.toString(), WSResponseSoalCTIsian.class);

                //simpen di session
                ArrayList<SoalCTIsian> isians = new ArrayList<SoalCTIsian>(weatherResponse.getData());
                if(sessionManagement.getSoalCTIsian()==null){
                    sessionManagement.setSoalIsian(isians);
                }else{
                    sessionManagement.setNoIsian(1);
                }
                //end
                if(isians.size() == 0){
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
                binding.btnKumpulkan.setVisibility(View.GONE);
                binding.tvJudulCt.setText(ct.getName());

                id = ct.getId();
                URLGETIsian = URLGETIsian + id + "/" + sessionManagement.getId();
                URLGETPg = URLGETPg + id + "/" + sessionManagement.getId();

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
                }
            }
        }

        // dari isian ke PG
        if(getArguments() != null && getArguments().containsKey("ket")){
            ArrayList<SoalCTPilihanGanda> pilihanGandas = new ArrayList<SoalCTPilihanGanda>(sessionManagement.getSoalCTPg());
            SoalCTPilihanGanda soalCTPilihanGanda = new SoalCTPilihanGanda(pilihanGandas.get(sessionManagement.getNoPg()-1));
            binding.tvNoSoalPg.setText(String.valueOf(soalCTPilihanGanda.getNumber()));
            binding.tvSoalCtPgMhs.setText(soalCTPilihanGanda.getQuestion());
            binding.rbPilihanA.setText(soalCTPilihanGanda.getA());
            binding.rbPilihanB.setText(soalCTPilihanGanda.getB());
            binding.rbPilihanC.setText(soalCTPilihanGanda.getC());
            binding.rbPilihanD.setText(soalCTPilihanGanda.getD());
            binding.rbPilihanE.setText(soalCTPilihanGanda.getE());
            String jwb = soalCTPilihanGanda.getJawaban();
            if(jwb != null){
                if(jwb.equalsIgnoreCase("A")){
                    binding.rbPilihanA.setChecked(true);
                } else if(jwb.equalsIgnoreCase("B")){
                    binding.rbPilihanB.setChecked(true);
                } else if(jwb.equalsIgnoreCase("C")){
                    binding.rbPilihanC.setChecked(true);
                } else if(jwb.equalsIgnoreCase("D")){
                    binding.rbPilihanD.setChecked(true);
                } else if(jwb.equalsIgnoreCase("E")){
                    binding.rbPilihanE.setChecked(true);
                }
            }else{
                binding.rbPilihanA.setChecked(false);
                binding.rbPilihanB.setChecked(false);
                binding.rbPilihanC.setChecked(false);
                binding.rbPilihanD.setChecked(false);
                binding.rbPilihanE.setChecked(false);
            }
            binding.btnNext.setText("Soal Isian");
            binding.btnBack.setText("Soal Sebelumnya");
            binding.btnKumpulkan.setVisibility(View.GONE);
            if(soalCTPilihanGanda.getNumber()==1){
                binding.btnBack.setEnabled(false);
            }
        }
    }
}