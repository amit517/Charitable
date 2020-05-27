package com.team.donation.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.team.donation.Model.Money;
import com.team.donation.Model.Transection;
import com.team.donation.R;
import com.team.donation.Utils.DateTimeHelper;
import com.team.donation.Utils.NetChecker;
import com.team.donation.databinding.ActivityDonateMoneyBinding;

public class DonateMoneyActivity extends AppCompatActivity {

    private Money money;
    private ActivityDonateMoneyBinding binding;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private ProgressDialog progressDialog;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_donate_money);

        init();
        getMoneyObject();

        binding.toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        binding.copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("label", binding.sendNumber.getText().toString());
                if (clipboard == null || clip == null) return;
                clipboard.setPrimaryClip(clip);
                Toast.makeText(DonateMoneyActivity.this, "Number Copied", Toast.LENGTH_SHORT).show();
            }
        });



        binding.addMoneyToDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final double amount = Double.parseDouble(binding.moneyAmount.getText().toString());
                if (money.getAskedAmount() >=amount){ // Amount is lower or equal
                    if (NetChecker.isNetworkAvailable(DonateMoneyActivity.this)){
                        progressDialog.show();
                        //userId = firebaseAuth.getCurrentUser().getUid();
                        DatabaseReference reference = databaseReference.child("Transaction");
                        String uniqueId = reference.push().getKey();
                        String date = DateTimeHelper.getDate();

                        Transection transection = new Transection(binding.bkashTransectionNo.getText().toString(),amount,binding.name.getText().toString(),uniqueId,money.getUniqueID(),date,firebaseAuth.getCurrentUser().getUid(),money.getOrganizationName());
                        reference.child(uniqueId).setValue(transection).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) { // Transaction added to the database
                                /*progressDialog.dismiss();
                                AlertDialog.Builder builder = new AlertDialog.Builder(DonateMoneyActivity.this);
                                builder.setTitle("Success");
                                builder.setMessage("Your Money has been successfully added.");
                                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        progressDialog.dismiss();
                                        onBackPressed();
                                    }
                                })
                                        .setCancelable(false);
                                AlertDialog alert = builder.create();
                                alert.show();*/



                                final double currentAmount = money.getAskedAmount() - amount;
                                if (currentAmount == 0){ // Have to disable the section and set the value to FB

                                    DatabaseReference reference2 = databaseReference.child("Money").child(money.getUniqueID()).child("enabled");
                                    reference2.setValue(false).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {

                                            DatabaseReference reference1 = databaseReference.child("Money").child(money.getUniqueID()).child("progressBar");
                                            reference1.setValue(money.getFixedAmount()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    DatabaseReference reference = databaseReference.child("Money").child(money.getUniqueID()).child("askedAmount");
                                                    reference.setValue(currentAmount).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            progressDialog.dismiss();
                                                            AlertDialog.Builder builder = new AlertDialog.Builder(DonateMoneyActivity.this);
                                                            builder.setTitle("Success");
                                                            builder.setMessage("Your Money has been successfully added.");
                                                            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                                public void onClick(DialogInterface dialog, int id) {
                                                                    //progressDialog.dismiss();
                                                                    onBackPressed();
                                                                }
                                                            })
                                                                    .setCancelable(false);
                                                            AlertDialog alert = builder.create();
                                                            alert.show();
                                                        }
                                                    });
                                                }
                                            });

                                        }
                                    });

                                }

                                else { // Update the value in firebase

                                    DatabaseReference reference1 = databaseReference.child("Money").child(money.getUniqueID()).child("progressBar");
                                    reference1.setValue(money.getProgressBar()+amount).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            DatabaseReference reference = databaseReference.child("Money").child(money.getUniqueID()).child("askedAmount");
                                            reference.setValue(currentAmount).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    progressDialog.dismiss();
                                                    AlertDialog.Builder builder = new AlertDialog.Builder(DonateMoneyActivity.this);
                                                    builder.setTitle("Success");
                                                    builder.setMessage("Your Money has been successfully added.");
                                                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int id) {
                                                            progressDialog.dismiss();
                                                            onBackPressed();
                                                        }
                                                    })
                                                            .setCancelable(false);
                                                    AlertDialog alert = builder.create();
                                                    alert.show();
                                                }
                                            });
                                        }
                                    });

                                }

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                Toast.makeText(DonateMoneyActivity.this, "some thing went wrong", Toast.LENGTH_SHORT).show();
                            }
                        });


                    }
                    else {
                        progressDialog.dismiss();
                        Toast.makeText(DonateMoneyActivity.this, "No network Available", Toast.LENGTH_SHORT).show();
                    }

                }
                else {
                    Toast.makeText(DonateMoneyActivity.this, "Donation exceed limit!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        
    }

    private void init() {

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait...");
        setSupportActionBar(binding.toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void getMoneyObject() {
        Intent intent = getIntent();
        money = intent.getParcelableExtra("money");
        binding.sendNumber.setText(money.getBkashNumber());
    }
}
