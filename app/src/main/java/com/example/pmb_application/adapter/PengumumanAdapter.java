package com.example.pmb_application.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pmb_application.R;
import com.example.pmb_application.entity.Pengumuman;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PengumumanAdapter extends RecyclerView.Adapter<PengumumanAdapter.PengumumanViewHolder> {

    private ArrayList<Pengumuman> pengumumans;
    private ItemClickListener clickListener;

    public PengumumanAdapter(ItemClickListener clickListener) {
        this.clickListener = clickListener;
        pengumumans = new ArrayList<>();
    }

    public void setPengumumans(ArrayList<Pengumuman> pengumumans) {
        this.pengumumans = pengumumans;
        notifyDataSetChanged();
    }

    public void setClickListener(ItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public PengumumanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pengumuman_item, parent,false);
        return new PengumumanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PengumumanViewHolder holder, int position) {
        final Pengumuman pengumuman = pengumumans.get(position);
        holder.txtNama.setText(pengumuman.getNama_user());
        holder.txtTanggal.setText(pengumuman.getDate());
        holder.txtIsi.setText(pengumuman.getDeskripsi());
        System.out.println("desc");
        System.out.println(pengumuman.getDeskripsi());
        holder.itemView.setOnClickListener(v -> {
            clickListener.itemClicked(pengumuman);
        });
    }

    @Override
    public int getItemCount() {
        return pengumumans.size();
    }

    public void changeData(ArrayList source){
        pengumumans.clear();
        pengumumans.addAll(source);
        notifyDataSetChanged();
    }

    public class PengumumanViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_nama_user)
        TextView txtNama;
        @BindView(R.id.tv_tanggal_pengumuman)
        TextView txtTanggal;
        @BindView(R.id.tv_isi_pengumuman)
        TextView txtIsi;

        public PengumumanViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public interface ItemClickListener {
        void itemClicked(Pengumuman pengumuman);
    }
}
