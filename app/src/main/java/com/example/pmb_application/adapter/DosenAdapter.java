package com.example.pmb_application.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pmb_application.R;
import com.example.pmb_application.entity.Dosen;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DosenAdapter extends RecyclerView.Adapter<DosenAdapter.DosenViewHolder> {

    private ArrayList<Dosen> dosens;
    private ItemClickListener clickListener;

    public DosenAdapter() {
    }

    public DosenAdapter(ItemClickListener clickListener) {
        this.clickListener = clickListener;
        dosens = new ArrayList<>();
    }

    public void setDosens(ArrayList<Dosen> dosens) {
        this.dosens = dosens;
        notifyDataSetChanged();
    }

    public void setClickListener(ItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public DosenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dosen_item, parent,false);
        return new DosenViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DosenViewHolder holder, int position) {
        final Dosen dosen = dosens.get(position);
        System.out.println("dosen");
        System.out.println(dosen.getName());
        holder.txtNik.setText(dosen.getNip());
        holder.txtName.setText(dosen.getName());
        holder.txtJabatan.setText(dosen.getJabatan());

        holder.itemView.setOnClickListener(v -> {
            clickListener.itemClicked(dosen);
        });
    }

    @Override
    public int getItemCount() {
        return dosens.size();
    }

    public void changeData(ArrayList source){
        dosens.clear();
        dosens.addAll(source);
        notifyDataSetChanged();
    }

    public class DosenViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_nrp_dosen_item)
        TextView txtNik;
        @BindView(R.id.tv_name_dosen_item)
        TextView txtName;
        @BindView(R.id.tv_jabatan_dosen_item)
        TextView txtJabatan;

        public DosenViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public interface ItemClickListener {
        void itemClicked(Dosen dosen);
    }
}
