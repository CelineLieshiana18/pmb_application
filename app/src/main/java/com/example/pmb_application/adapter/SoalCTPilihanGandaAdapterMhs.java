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

public class SoalCTPilihanGandaAdapterMhs extends RecyclerView.Adapter<SoalCTPilihanGandaAdapterMhs.SoalCTPilihanGandaMhsViewHolder> {

    private ArrayList<SoalCTPilihanGanda> soalCTPilihanGandas;
    private ItemClickListener clickListener;

    public SoalCTPilihanGandaAdapterMhs() {
    }

    public SoalCTPilihanGandaAdapterMhs(ItemClickListener clickListener) {
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
    public SoalCTPilihanGandaMhsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.soal_pg_ct_item, parent,false);
        return new SoalCTPilihanGandaMhsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SoalCTPilihanGandaMhsViewHolder holder, int position) {
        final SoalCTPilihanGanda soalCTPilihanGanda = soalCTPilihanGandas.get(position);
        holder.txtNo.setText(String.valueOf(soalCTPilihanGanda.getNumber()));
        holder.txtSoal.setText(soalCTPilihanGanda.getQuestion());
        holder.txtA.setText(soalCTPilihanGanda.getA());
        holder.txtB.setText(soalCTPilihanGanda.getB());
        holder.txtC.setText(soalCTPilihanGanda.getC());
        holder.txtD.setText(soalCTPilihanGanda.getD());
        holder.txtE.setText(soalCTPilihanGanda.getE());
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

    public class SoalCTPilihanGandaMhsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_no_soal_pg)
        TextView txtNo;
        @BindView(R.id.tv_soal_ct_pg_mhs)
        TextView txtSoal;
        @BindView(R.id.rbPilihanA)
        TextView txtA;
        @BindView(R.id.rbPilihanB)
        TextView txtB;
        @BindView(R.id.rbPilihanC)
        TextView txtC;
        @BindView(R.id.rbPilihanD)
        TextView txtD;
        @BindView(R.id.rbPilihanE)
        TextView txtE;

        public SoalCTPilihanGandaMhsViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public interface ItemClickListener {
        void itemClicked(SoalCTPilihanGanda soalCTPilihanGanda);
    }
}
