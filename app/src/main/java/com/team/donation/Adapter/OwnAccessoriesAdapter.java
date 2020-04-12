package com.team.donation.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.team.donation.Model.Accessories;

import java.util.ArrayList;

/**
 * Created by Amit on 12,April,2020
 * kundu.amit517@gmail.com
 */
public class OwnAccessoriesAdapter extends RecyclerView.Adapter<OwnAccessoriesAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Accessories> accessoriesArrayList;


    public OwnAccessoriesAdapter(Context context, ArrayList<Accessories> accessoriesArrayList) {
        this.context = context;
        this.accessoriesArrayList = accessoriesArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);


        }
    }
}
