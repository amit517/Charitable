package com.team.donation.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.team.donation.Model.Accessories;
import com.team.donation.R;

import java.util.ArrayList;


public class AccAdapter extends RecyclerView.Adapter<AccAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Accessories> accessoriesArrayList;
    private OnDeleteClickListener onDeleteClickListener;

    public AccAdapter(Context context, ArrayList<Accessories> accessoriesArrayList, OnDeleteClickListener onDeleteClickListener) {
        this.context = context;
        this.accessoriesArrayList = accessoriesArrayList;
        this.onDeleteClickListener = onDeleteClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.layout_own_accecories,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
            Accessories currentAccecoris = accessoriesArrayList.get(position);

            holder.productTitle11.setText(currentAccecoris.getProductTitle());
            holder.date11.setText(currentAccecoris.getPostedDate());
            holder.productDescription11.setText(currentAccecoris.getProductDescription());

            holder.deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onDeleteClickListener.deleteButtonclicked(position);
                }
            });

        String  img_url = currentAccecoris.getProductImageLink();
        //String  img_url = "https://image.shutterstock.com/image-photo/mountains-during-sunset-beautiful-natural-600w-407021107.jpg";
        Log.d("TAG", "onBindViewHolder: "+img_url);

        Picasso
                .get()
                .load(img_url)
                .resize(600,600)
                .into(holder.accProductimage11);
    }

    @Override
    public int getItemCount() {
        return accessoriesArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView date11,productTitle11,productDescription11;
        ImageView accProductimage11;
        Button deleteButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            date11 = itemView.findViewById(R.id.date11);
            productTitle11 = itemView.findViewById(R.id.productTitle11);
            productDescription11 = itemView.findViewById(R.id.productDescription11);
            accProductimage11 = itemView.findViewById(R.id.accProductimage11);
            deleteButton = itemView.findViewById(R.id.acc_delete_button);
        }
    }

    public interface OnDeleteClickListener {
        void deleteButtonclicked(int position);
    }

    public void clear(int position) {
        accessoriesArrayList.remove(position);
        notifyItemRemoved(position);
    }

    public void clear() {
        int size = accessoriesArrayList.size();
        accessoriesArrayList.clear();
        notifyItemRangeRemoved(0, size);
    }

}
