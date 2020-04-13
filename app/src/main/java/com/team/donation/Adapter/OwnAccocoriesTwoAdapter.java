package com.team.donation.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.team.donation.Model.Accessories;
import com.team.donation.R;

import java.util.ArrayList;

/**
 * Created by Amit on 12,April,2020
 * kundu.amit517@gmail.com
 */
public class OwnAccocoriesTwoAdapter extends RecyclerView.Adapter<OwnAccocoriesTwoAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Accessories> accessoriesArrayList;

    public OwnAccocoriesTwoAdapter(Context context, ArrayList<Accessories> accessoriesArrayList) {
        this.context = context;
        this.accessoriesArrayList = accessoriesArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_own_accecories,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        /*final Accessories currentAcc = accessoriesArrayList.get(position);
        holder.postType.setText(currentAcc.getType());
        holder.productTitle.setText(currentAcc.getProductTitle());
        holder.creatorName.setText(currentAcc.getCreatorName());
        holder.date.setText(currentAcc.getPostedDate());
        holder.type.setText(currentAcc.getProductType());
        holder.productDescription.setText(currentAcc.getProductDescription());
        String  img_url = currentAcc.getProductImageLink();
        Log.d("TAG", "onBindViewHolder: "+img_url);

        Picasso
                .get()
                .load(img_url)
                .resize(400,400)
                .into(holder.accProductimage);
        
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();
            }
        });*/

        Accessories currentAccecories = accessoriesArrayList.get(position);

        holder.test.setText(currentAccecories.getProductDescription());
    }

    @Override
    public int getItemCount() {
        return accessoriesArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        /*TextView postType,productTitle,date,creatorName,type,productDescription;
        Button deleteBtn;
        ImageView accProductimage;*/

        TextView test;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            /*postType = itemView.findViewById(R.id.postType1);
            productTitle = itemView.findViewById(R.id.productTitle1);
            creatorName = itemView.findViewById(R.id.creatorName1);
            date = itemView.findViewById(R.id.date1);
            type = itemView.findViewById(R.id.type1);
            productDescription = itemView.findViewById(R.id.productDescription1);
            accProductimage = itemView.findViewById(R.id.accProductimage1);
            deleteBtn = itemView.findViewById(R.id.acc_delete_post);*/

            //test = itemView.findViewById(R.id.productDescription1);
            
        }
    }
}
