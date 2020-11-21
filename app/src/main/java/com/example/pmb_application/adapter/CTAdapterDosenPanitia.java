package com.example.pmb_application.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pmb_application.R;
import com.example.pmb_application.entity.CT;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CTAdapterDosenPanitia extends RecyclerView.Adapter<CTAdapterDosenPanitia.CTViewHolder> {

    private ArrayList<CT> ctArrayList;
    private ItemClickListener clickListener;

    public CTAdapterDosenPanitia() {
    }

    public CTAdapterDosenPanitia(ItemClickListener clickListener) {
        this.clickListener = clickListener;
        ctArrayList = new ArrayList<>();
    }

    public void setDosens(ArrayList<CT> ctArrayList) {
        this.ctArrayList = ctArrayList;
        notifyDataSetChanged();
    }

    public void setClickListener(ItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public CTViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ct_item_dosen_panitia, parent,false);
        return new CTViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CTViewHolder holder, int position) {
        final CT ct = ctArrayList.get(position);
        holder.txtJudul.setText(ct.getName());
        holder.txtDurasi.setText(ct.getDuration());
        holder.txtDeskripsi.setText(ct.getDescription());

        holder.itemView.setOnClickListener(v -> {
            clickListener.itemClicked(ct);
        });
    }

    @Override
    public int getItemCount() {
        return ctArrayList.size();
    }

    public void changeData(ArrayList source){
        ctArrayList.clear();
        ctArrayList.addAll(source);
        notifyDataSetChanged();
    }

    public class CTViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_judul_ct_inti)
        TextView txtJudul;
        @BindView(R.id.tv_durasi_ct)
        TextView txtDurasi;
        @BindView(R.id.tv_deskripsi_ct)
        TextView txtDeskripsi;

        public CTViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public interface ItemClickListener {
        void itemClicked(CT ct);
    }
}
