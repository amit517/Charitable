package com.team.donation.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.team.donation.Adapter.TransectionAdapter;
import com.team.donation.Model.Accessories;
import com.team.donation.Model.Transection;
import com.team.donation.R;
import com.team.donation.databinding.ActivitySeeDonationBinding;

import java.util.ArrayList;

public class SeeDonationActivity extends AppCompatActivity {

    private ActivitySeeDonationBinding binding;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private ProgressDialog progressDialog;

    private ArrayList<Transection> transectionArrayList;
    private TransectionAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_see_donation);

        init();


        Intent intent = getIntent();
        String moneyToken = intent.getStringExtra("token");

        configureRV();
        binding.toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Query query= databaseReference.child("Transaction").orderByChild("postToken").equalTo(moneyToken);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){
                    transectionArrayList.clear();
                    for (DataSnapshot data : dataSnapshot.getChildren()){
                        Transection accessories = data.getValue(Transection.class);
                        transectionArrayList.add(accessories);
                    }
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }

    private void configureRV() {
        adapter = new TransectionAdapter(SeeDonationActivity.this,transectionArrayList);
        binding.donationRV.setLayoutManager(new LinearLayoutManager(SeeDonationActivity.this));
        binding.donationRV.setAdapter(adapter);
    }

    private void init() {

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait...");
        transectionArrayList = new ArrayList<>();
        setSupportActionBar(binding.toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
