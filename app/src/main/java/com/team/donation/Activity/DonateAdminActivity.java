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
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.team.donation.Model.Transection;
import com.team.donation.R;
import com.team.donation.Utils.DateTimeHelper;
import com.team.donation.Utils.NetChecker;
import com.team.donation.databinding.ActivityDonateAdminBinding;

public class DonateAdminActivity extends AppCompatActivity {

    private ActivityDonateAdminBinding binding;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_donate_admin);
        init();

        binding.copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("label", "01832706402");
                if (clipboard == null || clip == null) return;
                clipboard.setPrimaryClip(clip);
                Toast.makeText(DonateAdminActivity.this, "Number Copied", Toast.LENGTH_SHORT).show();
            }
        });

        binding.toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.addMoneyToDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (NetChecker.isNetworkAvailable(DonateAdminActivity.this)) {
                    progressDialog.show();
                    final double amount = Double.parseDouble(binding.moneyAmount.getText().toString());
                    DatabaseReference reference = databaseReference.child("Transaction");
                    String uniqueId = reference.push().getKey();
                    String date = DateTimeHelper.getDate();

                    Transection transection = new Transection(binding.bkashTransectionNo.getText().toString(), amount, binding.name.getText().toString(), uniqueId, "null", date,firebaseAuth.getCurrentUser().getUid(),"Admin");
                    reference.child(uniqueId).setValue(transection).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(DonateAdminActivity.this);
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
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(DonateAdminActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                        }
                    });
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
}
