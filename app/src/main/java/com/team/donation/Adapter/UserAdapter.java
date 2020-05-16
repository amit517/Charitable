package com.team.donation.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.team.donation.Model.User;
import com.team.donation.R;

import java.util.ArrayList;


public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private Context context;
    private ArrayList<User> userArrayList;

    public UserAdapter(Context context, ArrayList<User> userArrayList) {
        this.context = context;
        this.userArrayList = userArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(context).inflate(R.layout.layout_user,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.userName.setText("Name : "+userArrayList.get(position).getFirstName()+" "+userArrayList.get(position).getLastName());
        holder.userAddress.setText("Address : "+userArrayList.get(position).getAddress());
        holder.userEmail.setText("Email : "+userArrayList.get(position).getEmail());
        holder.userPhone.setText("Phone Number : "+userArrayList.get(position).getPhoneNumber());

    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView userName,userPhone,userAddress,userEmail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            userName=itemView.findViewById(R.id.userName);
            userPhone=itemView.findViewById(R.id.userPhone);
            userAddress=itemView.findViewById(R.id.userAddress);
            userEmail=itemView.findViewById(R.id.userEmail);
        }
    }
}
