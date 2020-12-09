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

public class SoalCTIsianAdapter extends RecyclerView.Adapter<SoalCTIsianAdapter.SoalCTIsianViewHolder> {

    private ArrayList<SoalCTIsian> soalCTIsians;
    private ItemClickListener clickListener;

    public SoalCTIsianAdapter() {
    }

    public SoalCTIsianAdapter(ItemClickListener clickListener) {
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ct_isian_item_dosen_panitia, parent,false);
        return new SoalCTIsianViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SoalCTIsianViewHolder holder, int position) {
        final SoalCTIsian soalCTIsian = soalCTIsians.get(position);
        holder.txtNo.setText(String.valueOf(soalCTIsian.getNumber()));
        holder.txtSoal.setText(soalCTIsian.getQuestion());
        holder.txtScore.setText(soalCTIsian.getScore());

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
        @BindView(R.id.tv_no_ct_isian)
        TextView txtNo;
        @BindView(R.id.tv_soal_ct_isian)
        TextView txtSoal;
        @BindView(R.id.tv_score_ct_isian)
        TextView txtScore;

        public SoalCTIsianViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public interface ItemClickListener {
        void itemClicked(SoalCTIsian soalCTIsian);
    }
}
