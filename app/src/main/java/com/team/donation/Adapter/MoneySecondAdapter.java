package com.team.donation.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.team.donation.Activity.DonateMoneyActivity;
import com.team.donation.Model.Money;
import com.team.donation.R;

import java.util.ArrayList;

/**
 * Created by Amit on 13,April,2020
 * kundu.amit517@gmail.com
 */
public class MoneySecondAdapter extends RecyclerView.Adapter<MoneySecondAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Money> moneyArrayList;
    private String type;

    public MoneySecondAdapter(Context context, ArrayList<Money> moneyArrayList,String type) {
        this.context = context;
        this.moneyArrayList = moneyArrayList;
        this.type =type;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_money,parent,false);
        return new  ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Money money = moneyArrayList.get(position);
        String amount = String.valueOf(money.getAskedAmount());

        if (type.equals("admin")){
            holder.donateNow.setVisibility(View.GONE);
        }

        holder.askedAmmount.setText(amount);
        holder.dateMoney.setText(money.getPostedDate());
        holder.post_creator.append(money.getOrganizationName());
        holder.description.setText(money.getDescription());
        holder.donateNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, DonateMoneyActivity.class);
                intent.putExtra("money",money);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return moneyArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView askedAmmount,post_creator,dateMoney,description;
        Button donateNow;
        
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            askedAmmount = itemView.findViewById(R.id.askedAmmount);
            post_creator = itemView.findViewById(R.id.post_creator);
            dateMoney = itemView.findViewById(R.id.dateMoney);
            donateNow = itemView.findViewById(R.id.donateNow);
            description = itemView.findViewById(R.id.description);

        }
    }

    public void clear() {
        moneyArrayList.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(ArrayList<Money> list) {
        moneyArrayList.addAll(list);
        notifyDataSetChanged();
    }
}
