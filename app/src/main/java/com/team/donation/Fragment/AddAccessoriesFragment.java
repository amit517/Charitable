package com.team.donation.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.team.donation.Model.Accessories;
import com.team.donation.Model.Organization;
import com.team.donation.Model.User;
import com.team.donation.R;
import com.team.donation.Utils.DateTimeHelper;
import com.team.donation.Utils.GlobalVariables;
import com.team.donation.Utils.NetChecker;
import com.team.donation.databinding.FragmentAddAccessoriesBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddAccessoriesFragment extends Fragment {

    private Context context;
    private FragmentAddAccessoriesBinding binding;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private ProgressDialog progressDialog;
    private String userId;
    private String userMode;
    private StorageReference storageReference;
    private Uri uri;
    private String productImageUrl;

    public AddAccessoriesFragment() {
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

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_add_accessories,container,false);

        init();
        checkuser();

        binding.productImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent,1);
            }
        });

        binding.addAccesoriesToDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (NetChecker.isNetworkAvailable(context)){
                    progressDialog.show();
                    userId = firebaseAuth.getCurrentUser().getUid();

                    final StorageReference profileImageRef = storageReference.child("Product Images").child(String.valueOf(System.currentTimeMillis()));
                    if (uri!=null){
                        profileImageRef.putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                if (task.isSuccessful()){
                                    profileImageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            productImageUrl = uri.toString();
                                            databaseReference.child("users").child(userId).addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    try {
                                                        if (snapshot.getValue() != null) {
                                                            try {
                                                                switch (userMode){
                                                                    case "User":
                                                                        User user = snapshot.getValue(User.class);
                                                                        Log.d("TAG", "onDataChange: "+user.getFirstName());

                                                                        String productTitle = binding.productTitle.getText().toString();
                                                                        String productType = binding.itemTypeSpinner.getSelectedItem().toString();
                                                                        Log.d("TAG", "onDataChange: "+productType);
                                                                        String productDescription = binding.productDescription.getText().toString();
                                                                        String creatorPhoneNo = binding.contactNo.getText().toString();
                                                                        String creatorAddress = user.getAddress();
                                                                        String postedDate = DateTimeHelper.getDate();
                                                                        boolean isEnabled = true;
                                                                        String creatorName = user.getFirstName()+" "+user.getLastName();
                                                                        String type = "User";
                                                                        Accessories accessories = new Accessories(productTitle,productType,productDescription,creatorPhoneNo,creatorAddress,postedDate,isEnabled,creatorName,type,productImageUrl,userId);
                                                                        storeToDataBase(accessories);
                                                                        break;
                                                                    case "Organization":
                                                                        Organization organization = snapshot.getValue(Organization.class);
                                                                        Log.d("TAG", "onDataChange: "+organization.getName());

                                                                        productTitle = binding.productTitle.getText().toString();
                                                                        productType = binding.itemTypeSpinner.getSelectedItem().toString();
                                                                        Log.d("TAG", "onDataChange: "+productType);
                                                                        productDescription = binding.productDescription.getText().toString();
                                                                        creatorPhoneNo = binding.contactNo.getText().toString();
                                                                        creatorAddress = organization.getAddress();
                                                                        postedDate = DateTimeHelper.getDate();
                                                                        isEnabled = true;
                                                                        creatorName = organization.getName();
                                                                        type = "Organization";
                                                                        accessories = new Accessories(productTitle, productType, productDescription, creatorPhoneNo, creatorAddress, postedDate, isEnabled, creatorName, type, productImageUrl, userId);
                                                                        storeToDataBase(accessories);
                                                                        break;
                                                                    default:
                                                                        // Amar Mathay Bari Daw :P
                                                                }


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
                                    });
                                }
                            }
                        });

                    }else {
                        progressDialog.dismiss();
                        Toast.makeText(context, "Please select Product image", Toast.LENGTH_SHORT).show();
                    }

                }
                else {
                    progressDialog.dismiss();
                    Toast.makeText(context, "No network Available", Toast.LENGTH_SHORT).show();
                }

            }
        });


        return binding.getRoot();
    }

    private void storeToDataBase(Accessories accessories) {

        DatabaseReference reference = databaseReference.child("Accessories");
        String uniqueId = reference.push().getKey();
        accessories.setUniqueID(uniqueId);

        reference.child(uniqueId).setValue(accessories).addOnCompleteListener(new OnCompleteListener<Void>() {
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
        storageReference = FirebaseStorage.getInstance().getReference();
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait...");
    }

    private void checkuser() {

        SharedPreferences sharedPreferences = context.getSharedPreferences(GlobalVariables.sharedPref, Context.MODE_PRIVATE);
        userMode = sharedPreferences.getString(GlobalVariables.userMode,"");

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode==1 && data!=null){
            uri = data.getData();
            binding.productImage.setImageURI(uri);
            Log.d("TAG", "onActivityResult: "+uri);
        }
    }
}
