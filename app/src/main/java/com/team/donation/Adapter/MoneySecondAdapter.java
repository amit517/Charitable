package com.team.donation.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.team.donation.Activity.DonateMoneyActivity;
import com.team.donation.Model.Money;
import com.team.donation.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;


public class MoneySecondAdapter extends RecyclerView.Adapter<MoneySecondAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Money> moneyArrayList;
    private String type;
    private final ClickListener listener;

    public MoneySecondAdapter(Context context, ArrayList<Money> moneyArrayList, String type, ClickListener listener) {
        this.context = context;
        this.moneyArrayList = moneyArrayList;
        this.type = type;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_money,parent,false);
        return new  ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final Money money = moneyArrayList.get(position);
        String amount = String.valueOf(money.getAskedAmount());

        if (type.equals("admin")){
            holder.donateNow.setVisibility(View.GONE);
            holder.report1.setVisibility(View.GONE);
            holder.delete.setVisibility(View.VISIBLE);
        }

        if (type.equals("archive") || type.equals("admin")){
            holder.askedAmmount.setText(String.valueOf(money.getFixedAmount()));
            holder.askedAmmount.setTextColor(context.getResources().getColor(R.color.gray2));
        }else {
            holder.askedAmmount.setText(amount);
        }


        holder.dateMoney.setText(money.getPostedDate());
        holder.post_creator.setText(money.getOrganizationName());
        holder.description.setText(money.getDescription());
        holder.donateNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, DonateMoneyActivity.class);
                intent.putExtra("money",money);
                context.startActivity(intent);
            }
        });

        holder.report1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context, "Send email report", Toast.LENGTH_SHORT).show();

                String[] TO = {"charitable321@gmail.com"};

                Intent intent = new Intent(Intent.ACTION_SEND);

                intent.setType("text/html");
                intent.putExtra(Intent.EXTRA_EMAIL, TO);
                intent.putExtra(Intent.EXTRA_SUBJECT, "Want to report");
                //intent.putExtra(Intent.EXTRA_TEXT, "Hi Charitable, I want to report on the post "+currentAcc.getUniqueID()+" \n Organization Name "+currentAcc.getCreatorName());
                intent.putExtra(Intent.EXTRA_TEXT, "Hi Charitable, I want to report on the post "+money.getUniqueID()+" \n Organization Name "+money.getOrganizationName());
                context.startActivity(Intent.createChooser(intent, "Send Email"));


            }
        });

        holder.progress_bar.setMax((int) money.getFixedAmount());
        Log.d("TAG", "onBindViewHolder: "+money.getAskedAmount());
        Log.d("TAG", "onBindViewHolder: "+money.getFixedAmount());
        Log.d("TAG", "onBindViewHolder: "+money.getProgressBar());
        holder.progress_bar.setProgress((int) money.getProgressBar());
        holder.progress_bar.setProgressDrawable(context.getResources().getDrawable(R.drawable.prograssbar));
        Log.d("TAG", "onBindViewHolder: "+(int) Math.abs(money.getFixedAmount() - money.getAskedAmount()));
        holder.moneyTitle.setText(money.getTitle());
        String  img_url = money.getProductImageLink();
        Picasso
                .get()
                .load(img_url)
                .resize(600,600)
                .into(holder.moneyImage);

        if (type.equals("archive")){
            holder.moneyRoot.setBackgroundColor(ContextCompat.getColor(context, R.color.gray));
            holder.donateNow.setVisibility(View.GONE);
        }else {
            if (position%2 == 0) {
                holder.moneyRoot.setBackgroundColor(ContextCompat.getColor(context, R.color.light_blue));
            } else {
                holder.moneyRoot.setBackgroundColor(ContextCompat.getColor(context, R.color.light_green));
            }
        }

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.listenerRef.get().OnDeleteClicked(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return moneyArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView askedAmmount,post_creator,dateMoney,description,moneyTitle;
        Button donateNow,delete;
        ImageView report1,moneyImage;
        CardView moneyRoot;
        ProgressBar progress_bar;
        private WeakReference<ClickListener> listenerRef;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            askedAmmount = itemView.findViewById(R.id.askedAmmount);
            post_creator = itemView.findViewById(R.id.post_creator);
            dateMoney = itemView.findViewById(R.id.dateMoney);
            donateNow = itemView.findViewById(R.id.donateNow);
            description = itemView.findViewById(R.id.description);
            report1 = itemView.findViewById(R.id.report1);
            delete = itemView.findViewById(R.id.delete);
            moneyImage = itemView.findViewById(R.id.moneyImage);
            moneyRoot = itemView.findViewById(R.id.moneyRoot);
            progress_bar = itemView.findViewById(R.id.progress_bar);
            moneyTitle = itemView.findViewById(R.id.moneyTitle);
            listenerRef = new WeakReference<>(listener);
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

    public interface ClickListener {
        void OnDeleteClicked(int position);
    }

    public void clear(int position) {
        moneyArrayList.remove(position);
        notifyItemRemoved(position);
    }
}
