package com.example.pmb_application.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pmb_application.R;
import com.example.pmb_application.entity.Dosen;
import com.example.pmb_application.entity.Kegiatan;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class KegiatanAdapter extends RecyclerView.Adapter<KegiatanAdapter.KegiatanViewHolder> {

    private ArrayList<Kegiatan> kegiatans;
    private ItemClickListener clickListener;

    public KegiatanAdapter() {
    }

    public KegiatanAdapter(ItemClickListener clickListener) {
        this.clickListener = clickListener;
        kegiatans = new ArrayList<>();
    }

    public void setKegiatans(ArrayList<Kegiatan> kegiatans) {
        this.kegiatans = kegiatans;
        notifyDataSetChanged();
    }

    public void setClickListener(ItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public KegiatanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.kegiatan_item, parent,false);
        return new KegiatanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KegiatanViewHolder holder, int position) {
        final Kegiatan kegiatan = kegiatans.get(position);
        holder.txtTanggal.setText(kegiatan.getDate());
        holder.txtJamMulai.setText(kegiatan.getStart());
        holder.txtJamAkhir.setText(kegiatan.getEnd());
        holder.txtDetailKegiatan.setText(kegiatan.getDescription());

        holder.itemView.setOnClickListener(v -> {
            clickListener.itemClicked(kegiatan);
        });
    }

    @Override
    public int getItemCount() {
        return kegiatans.size();
    }

    public void changeData(ArrayList source){
        kegiatans.clear();
        kegiatans.addAll(source);
        notifyDataSetChanged();
    }

    public class KegiatanViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_tanggal_kegiatan)
        TextView txtTanggal;
        @BindView(R.id.tv_jammulai_kegiatan)
        TextView txtJamMulai;
        @BindView(R.id.tv_jamakhir_kegiatan)
        TextView txtJamAkhir;
        @BindView(R.id.tv_detailkegiatan)
        TextView txtDetailKegiatan;

        public KegiatanViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public interface ItemClickListener {
        void itemClicked(Kegiatan kegiatan);
    }
}
