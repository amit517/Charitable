package com.team.donation.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.team.donation.Model.Accessories;
import com.team.donation.Model.Money;
import com.team.donation.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;




public class AccessoriesAdapter extends RecyclerView.Adapter<AccessoriesAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Accessories> accessoriesArrayList;
    private String type = "null";
    private AccClickListener listener;

    public AccessoriesAdapter(Context context, ArrayList<Accessories> accessoriesArrayList, String type, AccClickListener listener) {
        this.context = context;
        this.accessoriesArrayList = accessoriesArrayList;
        this.type = type;
        this.listener = listener;
    }

    public AccessoriesAdapter(Context context, ArrayList<Accessories> accessoriesArrayList) {
        this.context = context;
        this.accessoriesArrayList = accessoriesArrayList;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_accessories,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {



        final Accessories currentAcc = accessoriesArrayList.get(position);

        if (currentAcc.getType().equals("User")){
            holder.postType.setTextColor(ContextCompat.getColor(context,R.color.green));
        }
        else if (currentAcc.getType().equals("Organization")){
            holder.postType.setTextColor(ContextCompat.getColor(context,R.color.red));
        }
        else {
            holder.postType.setTextColor(ContextCompat.getColor(context,R.color.black_alfa_70));
        }
        holder.postType.setText(currentAcc.getType());
        holder.productTitle.setText(currentAcc.getProductTitle());
        holder.creatorName.setText(currentAcc.getCreatorName());
        holder.date.setText(currentAcc.getPostedDate());
        holder.type.setText(currentAcc.getProductType());
        holder.productDescription.setText(currentAcc.getProductDescription());
        holder.phoneNumber.setText(currentAcc.getCreatorPhoneNo());
        holder.locationTv.setText(currentAcc.getCreatorAddress());

        String  img_url = currentAcc.getProductImageLink();
        //String  img_url = "https://image.shutterstock.com/image-photo/mountains-during-sunset-beautiful-natural-600w-407021107.jpg";
        Log.d("TAG", "onBindViewHolder: "+img_url);

        Picasso
                .get()
                .load(img_url)
                .resize(600,600)
                .into(holder.accProductimage);

        holder.callBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (currentAcc.getCreatorPhoneNo()!=null){
                        String number = "tel:"+ currentAcc.getCreatorPhoneNo();
                        Intent call = new Intent (Intent.ACTION_DIAL, Uri.parse(number));
                        context.startActivity(call);
                    }else{
                        Toast.makeText(context, "Number is not available", Toast.LENGTH_SHORT).show();
                    }

                }
            });

        if (position%2 == 0) {
            holder.moneyRoot.setBackgroundColor(ContextCompat.getColor(context, R.color.light_blue));
        } else {
            holder.moneyRoot.setBackgroundColor(ContextCompat.getColor(context, R.color.light_green));
        }

        if (type.equals("admin")){
            holder.acc_delete_buttondelete.setVisibility(View.VISIBLE);
            holder.acc_delete_buttondelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.OnAccDeleteClicked(position);
                }
            });
        }

        /*holder.report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] TO = {"charitable321@gmail.com"};

                Intent intent = new Intent(Intent.ACTION_SEND);

                intent.setType("text/html");
                intent.putExtra(Intent.EXTRA_EMAIL, TO);
                intent.putExtra(Intent.EXTRA_SUBJECT, "Want To report");
                intent.putExtra(Intent.EXTRA_TEXT, "Hi Charitable, I want to report on the post "+currentAcc.getUniqueID()+" \n Organization Name "+currentAcc.getCreatorName());

                context.startActivity(Intent.createChooser(intent, "Send Email"));
            }
        });*/

    }

    @Override
    public int getItemCount() {
        return accessoriesArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView postType,productTitle,date,creatorName,type,productDescription,phoneNumber,locationTv;
        ImageButton callBtn;
        ImageView accProductimage,report;
        CardView moneyRoot;
        Button acc_delete_buttondelete;
        private WeakReference<AccClickListener> listenerRef;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            postType = itemView.findViewById(R.id.postType);
            productTitle = itemView.findViewById(R.id.productTitle);
            creatorName = itemView.findViewById(R.id.creatorName);
            date = itemView.findViewById(R.id.date);
            type = itemView.findViewById(R.id.type);
            productDescription = itemView.findViewById(R.id.productDescription);
            phoneNumber = itemView.findViewById(R.id.phoneNumber);
            locationTv = itemView.findViewById(R.id.locationTv);
            accProductimage = itemView.findViewById(R.id.accProductimage);
            callBtn = itemView.findViewById(R.id.callBtn);
            moneyRoot = itemView.findViewById(R.id.moneyRoot);
            acc_delete_buttondelete = itemView.findViewById(R.id.acc_delete_buttondelete);
            //report = itemView.findViewById(R.id.report);
            listenerRef = new WeakReference<>(listener);

        }


    }

    public interface AccClickListener {
        void OnAccDeleteClicked(int position);
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
