package com.team.donation.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.team.donation.R;
import com.team.donation.Utils.GlobalVariables;
import com.team.donation.Utils.NetChecker;
import com.team.donation.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private FirebaseAuth firebaseAuth;
    private String userMode = "User";
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_login);

        init();

        if (firebaseAuth.getCurrentUser()!=null){
            checkCurrentUser();
        }

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (NetChecker.isNetworkAvailable(LoginActivity.this)){
                    String email = binding.etUserId.getText().toString();
                    String password = binding.etPassword.getText().toString();


                    boolean result = checkField(email,password); // checking if any of the field is empty

                    if(result)
                    {
                        progressDialog.show();
                        signInWithEmailPassword(email,password);
                    }
                    else
                    {
                        if(email.isEmpty())
                        {
                            binding.etUserId.setError("Email Can't Be Empty");
                        }
                        else if (password.isEmpty())
                        {
                            binding.etPassword.setError("Password Can't Be empty");
                        }
                    }


                }
                
                else {
                    Toast.makeText(LoginActivity.this, "Please check you internet!", Toast.LENGTH_SHORT).show();
                }
            }
        });




        binding.signUpTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, UserSignUpActivity.class));
            }
        });

        binding.orgSignUpTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, OrganizationSignUpActivity.class));
            }
        });

        binding.userTypeRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = findViewById(checkedId);
                userMode = rb.getText().toString();
            }
        });

    }

    private void checkCurrentUser() {

        SharedPreferences sharedPreferences = getSharedPreferences(GlobalVariables.sharedPref, MODE_PRIVATE);
        userMode = sharedPreferences.getString(GlobalVariables.userMode,"");
        goToNewActivity(userMode);
    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(GlobalVariables.sharedPref,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(GlobalVariables.userMode,userMode);
        editor.putString(GlobalVariables.userID,firebaseAuth.getCurrentUser().getUid());

        editor.apply();

    }

    private void init() {
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("Please wait...");
    }

    private void signInWithEmailPassword(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    /*Toast.makeText(LoginActivity.this, "Sign In Successful", Toast.LENGTH_SHORT).show();
                    goToMain();*/
                    saveData();
                    progressDialog.dismiss();
                    goToNewActivity(userMode);


                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private void goToNewActivity(String userMode) {

        switch (userMode){
            case "User":
                startActivity(new Intent(LoginActivity.this,UserMainActivity.class));
                LoginActivity.this.finish();
                break;

            case "Organization":
                startActivity(new Intent(LoginActivity.this,OrganizerMainActivity.class));
                LoginActivity.this.finish();
                break;
            case "Admin":
                startActivity(new Intent(LoginActivity.this,AdminMainActivity.class));
                LoginActivity.this.finish();
                break;

            default:
                // Amar Mathay Bari Daw :P
        }

    }

    private boolean checkField(String teamID, String teamPassword) {

        if(teamID.isEmpty() || teamPassword.isEmpty())
        {
            return false;
        }
        else
        {
            return true;
        }

    }
}
