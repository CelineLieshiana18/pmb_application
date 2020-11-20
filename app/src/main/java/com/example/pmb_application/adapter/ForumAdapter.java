package com.example.pmb_application.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pmb_application.R;
import com.example.pmb_application.entity.Dosen;
import com.example.pmb_application.entity.Forum;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ForumAdapter extends RecyclerView.Adapter<ForumAdapter.ForumViewHolder> {

    private ArrayList<Forum> forums;
    private ItemClickListener clickListener;

    public ForumAdapter() {
    }

    public ForumAdapter(ItemClickListener clickListener) {
        this.clickListener = clickListener;
        forums = new ArrayList<>();
    }

    public void setDosens(ArrayList<Forum> forums) {
        this.forums = forums;
        notifyDataSetChanged();
    }

    public void setClickListener(ItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ForumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.forum_item, parent,false);
        return new ForumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ForumViewHolder holder, int position) {
        final Forum forum = forums.get(position);
        holder.txtJudul.setText(forum.getName());
        holder.txtTanggal.setText(forum.getDate());
        holder.txtDeskripsi.setText(forum.getDescription());

        holder.itemView.setOnClickListener(v -> {
            clickListener.itemClicked(forum);
        });
    }

    @Override
    public int getItemCount() {
        return forums.size();
    }

    public void changeData(ArrayList source){
        forums.clear();
        forums.addAll(source);
        notifyDataSetChanged();
    }

    public class ForumViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_judul_forum)
        TextView txtJudul;
        @BindView(R.id.tv_tanggal_forum)
        TextView txtTanggal;
        @BindView(R.id.tv_deskripsi_forum)
        TextView txtDeskripsi;

        public ForumViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public interface ItemClickListener {
        void itemClicked(Forum forum);
    }
}
