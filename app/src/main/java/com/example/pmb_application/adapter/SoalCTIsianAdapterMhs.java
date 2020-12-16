package com.example.pmb_application.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pmb_application.R;
import com.example.pmb_application.entity.SoalCTIsian;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SoalCTIsianAdapterMhs extends RecyclerView.Adapter<SoalCTIsianAdapterMhs.SoalCTIsianViewHolder> {

    private ArrayList<SoalCTIsian> soalCTIsians;
    private ItemClickListener clickListener;

    public SoalCTIsianAdapterMhs() {
    }

    public SoalCTIsianAdapterMhs(ItemClickListener clickListener) {
        this.clickListener = clickListener;
        soalCTIsians = new ArrayList<>();
    }

    public void setDosens(ArrayList<SoalCTIsian> soalCTIsians) {
        this.soalCTIsians = soalCTIsians;
        notifyDataSetChanged();
    }

    public void setClickListener(ItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public SoalCTIsianViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.soal_isian_ct_item, parent,false);
        return new SoalCTIsianViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SoalCTIsianViewHolder holder, int position) {
        final SoalCTIsian soalCTIsian = soalCTIsians.get(position);
        holder.txtSoal.setText(soalCTIsian.getQuestion());
        holder.txtNoSoal.setText(soalCTIsian.getNumber());

        holder.itemView.setOnClickListener(v -> {
            clickListener.itemClicked(soalCTIsian);
        });
    }

    @Override
    public int getItemCount() {
        return soalCTIsians.size();
    }

    public void changeData(ArrayList source){
        soalCTIsians.clear();
        soalCTIsians.addAll(source);
        notifyDataSetChanged();
    }

    public class SoalCTIsianViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_no_soal_isian)
        TextView txtNoSoal;
        @BindView(R.id.tv_soal_ct_isian_mhs)
        TextView txtSoal;

        public SoalCTIsianViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public interface ItemClickListener {
        void itemClicked(SoalCTIsian soalCTIsian);
    }
}
