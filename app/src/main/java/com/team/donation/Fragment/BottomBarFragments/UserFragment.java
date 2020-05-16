package com.team.donation.Fragment.BottomBarFragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.team.donation.Activity.AdminMainActivity;
import com.team.donation.Activity.LoginActivity;
import com.team.donation.Activity.OrganizerMainActivity;
import com.team.donation.Activity.UserMainActivity;
import com.team.donation.Model.Accessories;
import com.team.donation.Model.Organization;
import com.team.donation.Model.User;
import com.team.donation.R;
import com.team.donation.Utils.DateTimeHelper;
import com.team.donation.Utils.GlobalVariables;
import com.team.donation.Utils.Logout;
import com.team.donation.databinding.FragmentUserBinding;

import kotlin.jvm.internal.PropertyReference0Impl;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment {

    private FragmentUserBinding binding;
    private Context context;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private String userMode;
    private User user;
    private Organization organization;

    public UserFragment() {
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

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_user,container,false);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        checkuser();


        binding.signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logout.logout(context);
            }
        });

        databaseReference.child("users").child(firebaseAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    if (snapshot.getValue() != null) {
                        try {
                            switch (userMode){
                                case "User":
                                    user = snapshot.getValue(User.class);
                                    binding.firstNameET.setText(user.getFirstName());
                                    binding.lastNameET.setText(user.getLastName());
                                    binding.nidET.setText(user.getNidNumber());
                                    binding.phoneET.setText(user.getPhoneNumber());
                                    binding.addressET.setText(user.getAddress());


                                    String  img_url = user.getProfileImageUrl();
                                    //String  img_url = "https://image.shutterstock.com/image-photo/mountains-during-sunset-beautiful-natural-600w-407021107.jpg";
                                    if (img_url!=null){
                                        Picasso
                                                .get()
                                                .load(img_url)
                                                .resize(150,150)
                                                .into(binding.profileImage);
                                    }



                                    break;
                                case "Organization":

                                    organization = snapshot.getValue(Organization.class);
                                    binding.orgNameET.setText(organization.getName());
                                    binding.nidET.setText(organization.getRegNumber());
                                    binding.phoneET.setText(organization.getPhoneNumber());
                                    binding.addressET.setText(organization.getAddress());

                                    img_url = organization.getProfileImageUrl();
                                    //String  img_url = "https://image.shutterstock.com/image-photo/mountains-during-sunset-beautiful-natural-600w-407021107.jpg";
                                    if (img_url!=null){
                                        Picasso
                                                .get()
                                                .load(img_url)
                                                .resize(150,150)
                                                .into(binding.profileImage);
                                    }
                                    break;
                                default:
                                    // Amar Mathay Bari Daw :P
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Log.e("TAG", " it's null.");

                    }
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        binding.updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (userMode){
                    case "User":
                        user.setFirstName(binding.firstNameET.getText().toString());
                        user.setLastName(binding.lastNameET.getText().toString());
                        user.setNidNumber(binding.nidET.getText().toString());
                        user.setPhoneNumber(binding.phoneET.getText().toString());
                        user.setAddress(binding.addressET.getText().toString());

                        DatabaseReference userRef = databaseReference.child("users").child(user.getUniqueId());
                        userRef.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(context, "Successfully Updated", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                            }
                        });

                        break;

                    case "Organization":

                        organization.setRegNumber(binding.nidET.getText().toString());
                        organization.setPhoneNumber(binding.phoneET.getText().toString());
                        organization.setAddress(binding.addressET.getText().toString());
                        organization.setName(binding.orgNameET.getText().toString());

                        userRef = databaseReference.child("users").child(organization.getUniqueId());
                        userRef.setValue(organization).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(context, "Successfully Updated", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;

                }
            }
        });

        return binding.getRoot();
    }

    private void checkuser() {

        SharedPreferences sharedPreferences = context.getSharedPreferences(GlobalVariables.sharedPref, Context.MODE_PRIVATE);
        userMode = sharedPreferences.getString(GlobalVariables.userMode,"");

        switch (userMode){
            case "User":
                binding.orgNameET.setVisibility(View.GONE);
                break;

            case "Organization":
                binding.userNameSection.setVisibility(View.GONE);
                break;
            case "Admin":
                binding.userObject.setVisibility(View.GONE);
                binding.updateBtn.setVisibility(View.GONE);
                binding.adminSection.setVisibility(View.VISIBLE);
                break;

        }

    }
}
