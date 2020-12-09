package com.example.pmb_application.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pmb_application.R;
import com.example.pmb_application.entity.Comment;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private ArrayList<Comment> comments;
    private ItemClickListener clickListener;


    public CommentAdapter(ItemClickListener clickListener) {
        this.clickListener = clickListener;
        comments = new ArrayList<>();
    }

    public void setDosens(ArrayList<Comment> comments) {
        this.comments = comments;
        notifyDataSetChanged();
    }

    public void setClickListener(ItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item, parent,false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        final Comment comment = comments.get(position);
        holder.txtNamaUser.setText(String.valueOf(comment.getStudent_name()));
        holder.txtIsi.setText(comment.getDescription());
        //date
        String targetFormat = "dd MMM yyyy hh:mm";
        String currentFormat = "yyyy-MM-dd hh:mm:ss";
        String sourceDate = comment.getDate();
        String timezone = "Asia/Jakarta";
        DateFormat srcDf = new SimpleDateFormat(currentFormat);
        srcDf.setTimeZone(TimeZone.getTimeZone(timezone));
        DateFormat destDf = new SimpleDateFormat(targetFormat);
        try {
            Date date = srcDf.parse(sourceDate);
            String targetDate = destDf.format(date);
            holder.txtTanggal.setText(targetDate);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        holder.itemView.setOnClickListener(v -> {
            clickListener.itemClicked(comment);
        });
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public void changeData(ArrayList source){
        comments.clear();
        comments.addAll(source);
        notifyDataSetChanged();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_nama_user_comment)
        TextView txtNamaUser;
        @BindView(R.id.tv_tanggal_comment)
        TextView txtTanggal;
        @BindView(R.id.tv_isi_komentar)
        TextView txtIsi;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public interface ItemClickListener {
        void itemClicked(Comment comment);
    }
}
