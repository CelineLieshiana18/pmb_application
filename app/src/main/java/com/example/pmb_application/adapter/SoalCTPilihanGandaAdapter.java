package com.example.pmb_application.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pmb_application.R;
import com.example.pmb_application.entity.SoalCTPilihanGanda;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SoalCTPilihanGandaAdapter extends RecyclerView.Adapter<SoalCTPilihanGandaAdapter.SoalCTPilihanGandaViewHolder> {

    private ArrayList<SoalCTPilihanGanda> soalCTPilihanGandas;
    private ItemClickListener clickListener;

    public SoalCTPilihanGandaAdapter() {
    }

    public SoalCTPilihanGandaAdapter(ItemClickListener clickListener) {
        this.clickListener = clickListener;
        soalCTPilihanGandas = new ArrayList<>();
    }

    public void setDosens(ArrayList<SoalCTPilihanGanda> soalCTPilihanGandas) {
        this.soalCTPilihanGandas = soalCTPilihanGandas;
        notifyDataSetChanged();
    }

    public void setClickListener(ItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public SoalCTPilihanGandaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ct_pg_item_dosen_panitia, parent,false);
        return new SoalCTPilihanGandaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SoalCTPilihanGandaViewHolder holder, int position) {
        final SoalCTPilihanGanda soalCTPilihanGanda = soalCTPilihanGandas.get(position);
        holder.txtNo.setText(String.valueOf(soalCTPilihanGanda.getNumber()));
        holder.txtSoal.setText(soalCTPilihanGanda.getQuestion());
        holder.txtKey.setText(soalCTPilihanGanda.getKey());

        holder.itemView.setOnClickListener(v -> {
            clickListener.itemClicked(soalCTPilihanGanda);
        });
    }

    @Override
    public int getItemCount() {
        return soalCTPilihanGandas.size();
    }

    public void changeData(ArrayList source){
        soalCTPilihanGandas.clear();
        soalCTPilihanGandas.addAll(source);
        notifyDataSetChanged();
    }

    public class SoalCTPilihanGandaViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_no_ct_pg)
        TextView txtNo;
        @BindView(R.id.tv_soal_ct_pg)
        TextView txtSoal;
        @BindView(R.id.tv_key_ct_pg)
        TextView txtKey;

        public SoalCTPilihanGandaViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public interface ItemClickListener {
        void itemClicked(SoalCTPilihanGanda soalCTPilihanGanda);
    }
}
