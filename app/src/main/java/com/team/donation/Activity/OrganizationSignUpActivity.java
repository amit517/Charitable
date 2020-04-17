package com.team.donation.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.team.donation.Model.Organization;
import com.team.donation.Model.User;
import com.team.donation.R;
import com.team.donation.Utils.NetChecker;
import com.team.donation.databinding.ActivityOrganizationSignUpBinding;
import com.team.donation.databinding.ActivitySignUpBinding;

public class OrganizationSignUpActivity extends AppCompatActivity {

    private ActivityOrganizationSignUpBinding binding;
    private Context context;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_organization_sign_up);

        init();

        binding.signInTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



        binding.signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (NetChecker.isNetworkAvailable(context)){

                    progressDialog.show();
                    String orgName = binding.orgNameET.getText().toString();
                    String orgRegNo = binding.regET.getText().toString();
                    String phoneNumber =binding.phoneET.getText().toString();
                    String address=binding.addressET.getText().toString();
                    String email=binding.emailET.getText().toString().trim();
                    String password = binding.passwordET.getText().toString();

                    boolean result = checkField(orgName,orgRegNo,phoneNumber,address,email,password);


                    if(result)
                    {
                        creatUser(orgName,orgRegNo,phoneNumber,address,email,password);
                    }
                    else
                    {
                        if(orgName.isEmpty())
                        {
                            binding.orgNameET.setError("First Name Can't Be Empty");
                        }
                        else if (orgRegNo.isEmpty())
                        {
                            binding.regET.setError("Last Name Can't Be empty");
                        }

                        else if (phoneNumber.isEmpty())
                        {
                            binding.phoneET.setError("Phone Number Can't Be empty");
                        }
                        else if (address.isEmpty())
                        {
                            binding.addressET.setError("Address Can't Be empty");
                        }
                        else if (email.isEmpty())
                        {
                            binding.emailET.setError("Email Can't Be empty");
                        }
                        else if (password.isEmpty())
                        {
                            binding.passwordET.setError("Password Can't Be empty");
                        }
                    }

                }

                else {
                    Toast.makeText(context, "Please check your internet connection!!!", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

            }
        });


    }

    private void creatUser(final String orgName,
                           final String orgRegNo,
                           final String phoneNumber,
                           final String address,
                           final String email,
                           String password) {

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "createUserWithEmail:success");

                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

                            assert firebaseUser != null;
                            String uniqueId = firebaseUser.getUid();
                            Organization organization = new Organization(orgName,orgRegNo,phoneNumber,address,email,uniqueId,"organization","Active");

                            storeOnDatabase(organization);



                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(context, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });



    }

    private void storeOnDatabase(Organization organization) {

        DatabaseReference userRef = databaseReference.child("users").child(organization.getUniqueId());
        userRef.setValue(organization).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    progressDialog.dismiss();
                    Toast.makeText(context, "SignUp Successful", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                Log.d("TAG", "onFailure: "+e.getMessage());
            }
        });

    }

    private boolean checkField(String orgName, String orgRegNo, String phoneNumber, String address, String email, String password) {

        if(orgName.isEmpty() || orgRegNo.isEmpty()|| phoneNumber.isEmpty()|| address.isEmpty()|| email.isEmpty() || password.isEmpty())
        {
            return false;
        }
        else
        {
            return true;
        }

    }

    private void init() {
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        context = OrganizationSignUpActivity.this;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please wait...");
    }


}
