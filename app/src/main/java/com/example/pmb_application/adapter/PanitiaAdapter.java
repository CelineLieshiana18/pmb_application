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

public class PanitiaAdapter extends RecyclerView.Adapter<PanitiaAdapter.PanitiaViewHolder> {

    private ArrayList<Student> students;
    private ItemClickListener clickListener;

    public PanitiaAdapter() {
    }

    public PanitiaAdapter(ItemClickListener clickListener) {
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
    public PanitiaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.panitia_item, parent,false);
        return new PanitiaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PanitiaViewHolder holder, int position) {
        final Student student = students.get(position);
        holder.txtNrp.setText(student.getNrp());
        holder.txtName.setText(student.getName());
        holder.txtJabatan.setText(student.getRoles_name());

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

    public class PanitiaViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_nrp_panitia_item)
        TextView txtNrp;
        @BindView(R.id.tv_name_panitia_item)
        TextView txtName;
        @BindView(R.id.tv_jabatan_panitia_item)
        TextView txtJabatan;

        public PanitiaViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public interface ItemClickListener {
        void itemClicked(Student student);
    }
}
