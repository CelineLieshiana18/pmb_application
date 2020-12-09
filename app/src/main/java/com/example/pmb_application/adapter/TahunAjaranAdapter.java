package com.example.pmb_application.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pmb_application.R;
import com.example.pmb_application.entity.TahunAjaran;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TahunAjaranAdapter extends RecyclerView.Adapter<TahunAjaranAdapter.TahunAjaranViewHolder> {

    private ArrayList<TahunAjaran> tahunAjarans;
    private ItemClickListener clickListener;


    public TahunAjaranAdapter(ItemClickListener clickListener) {
        this.clickListener = clickListener;
        tahunAjarans = new ArrayList<>();
    }

    public void setDosens(ArrayList<TahunAjaran> tahunAjarans) {
        this.tahunAjarans = tahunAjarans;
        notifyDataSetChanged();
    }

    public void setClickListener(ItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public TahunAjaranViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tahun_item, parent,false);
        return new TahunAjaranViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TahunAjaranViewHolder holder, int position) {
        final TahunAjaran tahunAjaran = tahunAjarans.get(position);
        holder.txtNama.setText(String.valueOf(tahunAjaran.getName()));
        if(tahunAjaran.getStatus() == 1){
            holder.txtTahun.setText("Aktif");
        } else{
            holder.txtTahun.setText("Tidak Aktif");
        }
        holder.itemView.setOnClickListener(v -> {
            clickListener.itemClicked(tahunAjaran);
        });
    }

    @Override
    public int getItemCount() {
        return tahunAjarans.size();
    }

    public void changeData(ArrayList source){
        tahunAjarans.clear();
        tahunAjarans.addAll(source);
        notifyDataSetChanged();
    }

    public class TahunAjaranViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_name_tahun)
        TextView txtNama;
        @BindView(R.id.tv_status_tahun)
        TextView txtTahun;

        public TahunAjaranViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public interface ItemClickListener {
        void itemClicked(TahunAjaran tahunAjaran);
    }
}
