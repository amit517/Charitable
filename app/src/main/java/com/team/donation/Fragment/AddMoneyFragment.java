package com.team.donation.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.team.donation.Activity.UserSignUpActivity;
import com.team.donation.Model.Money;
import com.team.donation.R;
import com.team.donation.Utils.DateTimeHelper;
import com.team.donation.Utils.NetChecker;
import com.team.donation.databinding.FragmentAddMoneyBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddMoneyFragment extends Fragment {

    private Context context;
    private FragmentAddMoneyBinding binding;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private ProgressDialog progressDialog;
    private String userId;

    public AddMoneyFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_add_money,container,false);

        init();

        binding.addMoneyToDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (NetChecker.isNetworkAvailable(context)){
                    progressDialog.show();
                    userId = firebaseAuth.getCurrentUser().getUid();
                    databaseReference.child("users").child(userId).child("name").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            try {
                                if (snapshot.getValue() != null) {
                                    try {

                                        double amount = Double.parseDouble(binding.moneyAmount.getText().toString());
                                        String description = binding.moneyDescription.getText().toString();
                                        String bkahsNo = binding.bkashNo.getText().toString();
                                        String postedDate = DateTimeHelper.getDate();
                                        boolean status = true;
                                        String orgName = snapshot.getValue()+"";

                                        Money money = new Money(userId,amount,postedDate,status,orgName,bkahsNo,description);
                                        storeTodatabse(money);


                                        Log.d("TAG", "onDataChange: "+orgName);
                                    } catch (Exception e) {
                                        progressDialog.dismiss();
                                        e.printStackTrace();
                                    }
                                } else {
                                    Log.e("TAG", " it's null.");
                                    progressDialog.dismiss();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                progressDialog.dismiss();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            progressDialog.dismiss();
                        }
                    });

                }
                else {
                    progressDialog.dismiss();
                    Toast.makeText(context, "No network Available", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return binding.getRoot();
    }

    private void storeTodatabse(Money money) {

        DatabaseReference reference = databaseReference.child("Money");
        String uniqueId = reference.push().getKey();
        money.setUniqueID(uniqueId);

        reference.child(uniqueId).setValue(money).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Success");
                    builder.setMessage("Your Event Successfully Added. Thank you for you collaboration.");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            progressDialog.dismiss();
                            getActivity().onBackPressed();
                        }
                    })
                    .setCancelable(false);
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                {
                    Toast.makeText(context, "Something went wrong. Please try again after sometime.", Toast.LENGTH_SHORT).show();
                }
            }
        });




    }

    private void init() {

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait...");

    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frame_layout, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }
}
