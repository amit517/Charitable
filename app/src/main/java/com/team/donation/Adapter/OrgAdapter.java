package com.team.donation.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.team.donation.Model.Organization;
import com.team.donation.R;

import java.util.ArrayList;


public class OrgAdapter extends RecyclerView.Adapter<OrgAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Organization> organizationArrayList;
    private AccAdapter.OnDeleteClickListener onDeleteClickListener;

    public OrgAdapter(Context context, ArrayList<Organization> organizationArrayList, AccAdapter.OnDeleteClickListener onDeleteClickListener) {
        this.context = context;
        this.organizationArrayList = organizationArrayList;
        this.onDeleteClickListener = onDeleteClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.layout_organization,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        if (organizationArrayList.get(position).getIsActive().equals("Banned")){
            holder.deleteOrg.setVisibility(View.GONE);
        }

        holder.orgName.setText(organizationArrayList.get(position).getName());
        holder.orgPhone.setText(organizationArrayList.get(position).getPhoneNumber());
        holder.orgEmail.setText(organizationArrayList.get(position).getEmail());
        holder.orgRegNo.setText(organizationArrayList.get(position).getRegNumber());
        holder.orgAddress.setText(organizationArrayList.get(position).getAddress());
        holder.accountStatus.setText(organizationArrayList.get(position).getIsActive());

        holder.deleteOrg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDeleteClickListener.deleteButtonclicked(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return organizationArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView orgName,orgRegNo,orgPhone,orgAddress,orgEmail,accountStatus;
        Button deleteOrg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            orgName = itemView.findViewById(R.id.orgName);
            orgRegNo = itemView.findViewById(R.id.orgRegNo);
            orgPhone = itemView.findViewById(R.id.orgPhone);
            orgAddress = itemView.findViewById(R.id.orgAddress);
            orgEmail = itemView.findViewById(R.id.orgEmail);
            deleteOrg = itemView.findViewById(R.id.deleteOrg);
            accountStatus = itemView.findViewById(R.id.accountStatus);

        }
    }

    public void clear() {
        organizationArrayList.clear();
        notifyDataSetChanged();
    }
}
