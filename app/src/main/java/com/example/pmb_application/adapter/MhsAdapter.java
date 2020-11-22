package com.example.pmb_application.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pmb_application.R;
import com.example.pmb_application.entity.Student;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MhsAdapter extends RecyclerView.Adapter<MhsAdapter.MhsViewHolder> {

    private ArrayList<Student> students;
    private ItemClickListener clickListener;

    public MhsAdapter(ItemClickListener clickListener) {
        this.clickListener = clickListener;
        students = new ArrayList<>();
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
        notifyDataSetChanged();
    }

    public void setClickListener(ItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public MhsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mahasiswa_item, parent,false);
        return new MhsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MhsViewHolder holder, int position) {
        final Student student = students.get(position);
        holder.txtNrp.setText(student.getNrp());
        holder.txtName.setText(student.getName());

        holder.itemView.setOnClickListener(v -> {
            clickListener.itemClicked(student);
        });
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public void changeData(ArrayList source){
        students.clear();
        students.addAll(source);
        notifyDataSetChanged();
    }

    public class MhsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_nrp_mhs_item)
        TextView txtNrp;
        @BindView(R.id.tv_name_mhs_item)
        TextView txtName;

        public MhsViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public interface ItemClickListener {
        void itemClicked(Student student);
    }
}
