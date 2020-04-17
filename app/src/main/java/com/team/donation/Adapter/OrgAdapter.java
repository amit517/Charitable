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

/**
 * Created by Amit on 17,April,2020
 * kundu.amit517@gmail.com
 */
public class OrgAdapter extends RecyclerView.Adapter<OrgAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Organization> organizationArrayList;

    public OrgAdapter(Context context, ArrayList<Organization> organizationArrayList) {
        this.context = context;
        this.organizationArrayList = organizationArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.layout_organization,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.orgName.append(organizationArrayList.get(position).getName());
        holder.orgPhone.append(organizationArrayList.get(position).getPhoneNumber());
        holder.orgEmail.append(organizationArrayList.get(position).getEmail());
        holder.orgRegNo.append(organizationArrayList.get(position).getRegNumber());
        holder.orgAddress.append(organizationArrayList.get(position).getAddress());

        holder.deleteOrg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return organizationArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView orgName,orgRegNo,orgPhone,orgAddress,orgEmail;
        Button deleteOrg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            orgName = itemView.findViewById(R.id.orgName);
            orgRegNo = itemView.findViewById(R.id.orgRegNo);
            orgPhone = itemView.findViewById(R.id.orgPhone);
            orgAddress = itemView.findViewById(R.id.orgAddress);
            orgEmail = itemView.findViewById(R.id.orgEmail);
            deleteOrg = itemView.findViewById(R.id.deleteOrg);

        }
    }
}
