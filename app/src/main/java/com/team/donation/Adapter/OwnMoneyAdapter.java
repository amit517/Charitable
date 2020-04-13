package com.team.donation.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.team.donation.Model.Money;
import com.team.donation.R;

import java.util.ArrayList;

/**
 * Created by Amit on 12,April,2020
 * kundu.amit517@gmail.com
 */
public class OwnMoneyAdapter extends RecyclerView.Adapter<OwnMoneyAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Money> moneyArrayList;

    public OwnMoneyAdapter(Context context, ArrayList<Money> moneyArrayList) {
        this.context = context;
        this.moneyArrayList = moneyArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.layout_own_money,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return moneyArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
