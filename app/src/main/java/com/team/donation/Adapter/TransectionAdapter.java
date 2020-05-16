package com.team.donation.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.team.donation.Model.Transection;
import com.team.donation.R;

import java.util.ArrayList;


public class TransectionAdapter extends RecyclerView.Adapter<TransectionAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Transection> transectionArrayList;

    public TransectionAdapter(Context context, ArrayList<Transection> transectionArrayList) {
        this.context = context;
        this.transectionArrayList = transectionArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.layout_transection,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Transection transection = transectionArrayList.get(position);

        String amount = String.valueOf(transection.getAmmount());
        holder.transectionAmount.setText("Amount : "+amount);
        holder.name.setText("Sender Name : "+transection.getSenderName());
        holder.transectionID.setText("Transection ID : "+transection.getTransectionID());
        holder.trandate.setText(transection.getTransectionDate());

    }

    @Override
    public int getItemCount() {
        return transectionArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView transectionAmount,trandate,transectionID,name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            transectionAmount = itemView.findViewById(R.id.transectionAmount);
            trandate = itemView.findViewById(R.id.trandate);
            transectionID = itemView.findViewById(R.id.transectionID);
            name = itemView.findViewById(R.id.name);

        }
    }
}
