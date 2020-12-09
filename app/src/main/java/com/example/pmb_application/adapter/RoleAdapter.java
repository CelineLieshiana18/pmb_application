package com.example.pmb_application.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pmb_application.R;
import com.example.pmb_application.entity.Role;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RoleAdapter extends RecyclerView.Adapter<RoleAdapter.RoleViewHolder> {

    private ArrayList<Role> roles;
    private ItemClickListener clickListener;

    public RoleAdapter(ItemClickListener clickListener) {
        this.clickListener = clickListener;
        roles = new ArrayList<>();
    }

    public void setRoles(ArrayList<Role> roles) {
        this.roles = roles;
        notifyDataSetChanged();
    }

    public void setClickListener(ItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public RoleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.role_item, parent,false);
        return new RoleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoleViewHolder holder, int position) {
        final Role role = roles.get(position);
        holder.txtName.setText(role.getName());
        holder.itemView.setOnClickListener(v -> {
            clickListener.itemClicked(role);
        });
    }

    @Override
    public int getItemCount() {
        return roles.size();
    }

    public void changeData(ArrayList source){
        roles.clear();
        roles.addAll(source);
        notifyDataSetChanged();
    }

    public class RoleViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_name_role)
        TextView txtName;

        public RoleViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public interface ItemClickListener {
        void itemClicked(Role role);
    }
}
