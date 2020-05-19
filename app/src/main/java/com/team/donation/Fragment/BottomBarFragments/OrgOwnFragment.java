package com.team.donation.Fragment.BottomBarFragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.team.donation.Activity.SeeDonationActivity;
import com.team.donation.Adapter.AccAdapter;
import com.team.donation.Adapter.OwnMoneyAdapter;
import com.team.donation.Adapter.TransectionAdapter;
import com.team.donation.Model.Accessories;
import com.team.donation.Model.Money;
import com.team.donation.Model.Transection;
import com.team.donation.R;
import com.team.donation.Utils.GlobalVariables;
import com.team.donation.Utils.PushFragment;
import com.team.donation.databinding.FragmentOragOwnBinding;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrgOwnFragment extends Fragment implements AccAdapter.OnDeleteClickListener {
    private FragmentOragOwnBinding binding;
    private Context context;
    private String userMode;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private ProgressDialog progressDialog;
    private String userId;
    private ArrayList<Accessories> accessoriesArrayList;
    private ArrayList<Money> moneyArrayList;
    private OwnMoneyAdapter moneyAdapter;
    private AccAdapter adapter;
    private ArrayList<Transection> transectionArrayList;
    private TransectionAdapter transectionAdapter;

    //private AccessoriesAdapter adapter;

    public OrgOwnFragment() {
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

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_orag_own,container,false);

        init();
        configureRV();
        checkuser();

        userId = firebaseAuth.getCurrentUser().getUid();

        if (userMode.equals("Organization")){
            orgAccecories();
            orgMoney();
        }
        else {
            userMoney();
        }

        Query query= databaseReference.child("Transaction").orderByChild("userId").equalTo(firebaseAuth.getCurrentUser().getUid());
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
                transectionAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return binding.getRoot();
    }


    private void userMoney() {
        Query query= databaseReference.child("Accessories").orderByChild("userId").equalTo(userId);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    accessoriesArrayList.clear();
                    for (DataSnapshot data : dataSnapshot.getChildren()){
                        Accessories accessories = data.getValue(Accessories.class);
                        accessoriesArrayList.add(accessories);
                    }
                    Log.d("TAG", "onChildAdded: "+accessoriesArrayList.size());
                    Log.d("TAG", "onChildAdded: "+accessoriesArrayList.get(0).getCreatorName());
                }
                adapter.notifyDataSetChanged();
                binding.animationView.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void orgMoney() {
        Query moneyQuerary= databaseReference.child("Money").orderByChild("userId").equalTo(userId);
        moneyQuerary.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    moneyArrayList.clear();

                    for (DataSnapshot data : dataSnapshot.getChildren()){
                        Money money = data.getValue(Money.class);
                        moneyArrayList.add(money);
                    }
                    Log.d("TAG", "onChildAdded: "+moneyArrayList.size());
                }

                moneyAdapter.notifyDataSetChanged();
                binding.animationView.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void orgAccecories() {

        Query query= databaseReference.child("Accessories").orderByChild("userId").equalTo(userId);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    accessoriesArrayList.clear();
                    for (DataSnapshot data : dataSnapshot.getChildren()){
                        Accessories accessories = data.getValue(Accessories.class);
                        accessoriesArrayList.add(accessories);
                    }
                    Log.d("TAG", "onChildAdded: "+accessoriesArrayList.size());
                    Log.d("TAG", "onChildAdded: "+accessoriesArrayList.get(0).getCreatorName());
                }
                adapter.notifyDataSetChanged();
                binding.animationView.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void configureRV() {

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(context);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);

        LinearLayoutManager m2LayoutManager = new LinearLayoutManager(context);
        m2LayoutManager.setReverseLayout(true);
        m2LayoutManager.setStackFromEnd(true);

        moneyAdapter = new OwnMoneyAdapter(context,moneyArrayList);
        binding.moneyRV.setLayoutManager(mLayoutManager);
        binding.moneyRV.setAdapter(moneyAdapter);

        adapter= new AccAdapter(context,accessoriesArrayList,this);
        binding.accoriesRV.setLayoutManager(m2LayoutManager);
        binding.accoriesRV.setAdapter(adapter);

        transectionAdapter = new TransectionAdapter(context,transectionArrayList,"own");
        LinearLayoutManager mLayoutManager3 = new LinearLayoutManager(context);
        mLayoutManager3.setReverseLayout(true);
        mLayoutManager3.setStackFromEnd(true);
        binding.donationRV.setLayoutManager(mLayoutManager3);
        binding.donationRV.setAdapter(transectionAdapter);

    }

    private void checkuser() {

        SharedPreferences sharedPreferences = context.getSharedPreferences(GlobalVariables.sharedPref, Context.MODE_PRIVATE);

        userMode = sharedPreferences.getString(GlobalVariables.userMode,"");

        Log.d("TAG", "checkuser: "+userMode);

        switch (userMode){
            case "User":
            case "Admin":

                binding.moneyCV.setVisibility(View.GONE);
                binding.moneyRV.setVisibility(View.GONE);
                break;


            default:
                // Amar Mathay Bari Daw :P
        }
    }

    private void init() {

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait...");
        accessoriesArrayList = new ArrayList<>();
        moneyArrayList = new ArrayList<>();
        transectionArrayList = new ArrayList<>();
    }

    @Override
    public void deleteButtonclicked(final int position) {

        String key = accessoriesArrayList.get(position).getUniqueID();

        databaseReference.child("Accessories").child(key).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    PushFragment.reloadFragment(context,"own");
                    Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });


    }
}
