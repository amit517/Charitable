package com.team.donation.Fragment.AdminFragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.team.donation.Adapter.AccessoriesAdapter;
import com.team.donation.Adapter.MoneySecondAdapter;
import com.team.donation.Model.Accessories;
import com.team.donation.Model.Money;
import com.team.donation.Model.Transection;
import com.team.donation.R;
import com.team.donation.Utils.PushFragment;
import com.team.donation.databinding.FragmentAllPostBinding;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllPostFragment extends Fragment implements MoneySecondAdapter.ClickListener,AccessoriesAdapter.AccClickListener {

    private FragmentAllPostBinding binding;
    private Context context;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private ProgressDialog progressDialog;

    private ArrayList<Money> moneyArrayList;
    private MoneySecondAdapter adapter;

    private ArrayList<Accessories> accessoriesArrayList;
    private AccessoriesAdapter accAdapter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public AllPostFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment1
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_all_post, container, false);
        init();
        configureRV();

        getAllmoney();
        getAllacc();


        binding.search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAllacc();
            }
        });

        ArrayAdapter<CharSequence> Spinneradapter = ArrayAdapter.createFromResource(context,
                R.array.currency_array, android.R.layout.simple_spinner_item);

        Spinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        binding.itemTypeSpinner.setAdapter(Spinneradapter);


        binding.itemTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (!binding.itemTypeSpinner.getSelectedItem().toString().equals("Select One")){
                    adapter.clear();
                }

                Query query = databaseReference.child("Accessories").orderByChild("productType").equalTo(binding.itemTypeSpinner.getSelectedItem().toString());
                Log.d("TAG", "onItemSelected: " + binding.itemTypeSpinner.getSelectedItem().toString());

                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            accessoriesArrayList.clear();

                            for (DataSnapshot data : dataSnapshot.getChildren()) {
                                Accessories accessories = data.getValue(Accessories.class);
                                accessoriesArrayList.add(accessories);
                            }
                            Log.d("TAG", "onItemSelected: " + accessoriesArrayList.size());
                            Log.d("TAG", "onItemSelected: " + accessoriesArrayList.get(0).getCreatorName());
                        }

                        accAdapter.notifyDataSetChanged();
                        // binding.animationView.setVisibility(View.GONE);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        return binding.getRoot();
    }

    private void configureRV() {

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(context);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);

        LinearLayoutManager m2LayoutManager = new LinearLayoutManager(context);
        m2LayoutManager.setReverseLayout(true);
        m2LayoutManager.setStackFromEnd(true);

        adapter = new MoneySecondAdapter(context, moneyArrayList, "admin", this);
        binding.moneyRV.setLayoutManager(mLayoutManager);
        binding.moneyRV.setAdapter(adapter);


        accAdapter = new AccessoriesAdapter(context, accessoriesArrayList,"admin",this);
        binding.accRV.setLayoutManager(m2LayoutManager);
        binding.accRV.setAdapter(accAdapter);


    }

    private void init() {

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait...");
        moneyArrayList = new ArrayList<>();
        accessoriesArrayList = new ArrayList<>();


    }

    private void getAllmoney() {

        DatabaseReference userRef = databaseReference.child("Money");
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    moneyArrayList.clear();
                    for (DataSnapshot data : dataSnapshot.getChildren()
                    ) {

                        data.getChildren().equals(Transection.class);

                        Money money = data.getValue(Money.class);
                        moneyArrayList.add(money);

                        Log.d("TAG", "onDataChange: " + dataSnapshot);

                    }
                    Log.d("TAG", "onDataChange money: " + moneyArrayList.size());
                    adapter.notifyDataSetChanged();
                    binding.animationView.setVisibility(View.GONE);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void getAllacc() {

        DatabaseReference userRef = databaseReference.child("Accessories");
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    accessoriesArrayList.clear();
                    for (DataSnapshot data : dataSnapshot.getChildren()
                    ) {
                        Accessories accessories = data.getValue(Accessories.class);
                        accessoriesArrayList.add(accessories);

                    }
                    Log.d("TAG", "onDataChange: acc" + accessoriesArrayList.size());
                    accAdapter.notifyDataSetChanged();
                    binding.animationView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void OnDeleteClicked(final int position) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Delete!");
        builder.setMessage("Are you sure you want to delete?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                String key = moneyArrayList.get(position).getUniqueID();
                Log.d("TAG", "onClick: "+key);
                databaseReference.child("Money").child(key).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            PushFragment.reloadFragment(context,"AllPost");
                            Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
            }
        })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setCancelable(false);
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void OnAccDeleteClicked(final int position) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Delete!");
        builder.setMessage("Are you sure you want to delete?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                String key = accessoriesArrayList.get(position).getUniqueID();
                Log.d("TAG", "onClick: "+key);
                databaseReference.child("Accessories").child(key).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            PushFragment.reloadFragment(context,"AllPost");
                            Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
            }
        })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setCancelable(false);
        AlertDialog alert = builder.create();
        alert.show();
    }
}
