package com.team.donation.Fragment.AdminFragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.team.donation.Adapter.AccessoriesAdapter;
import com.team.donation.Adapter.MoneySecondAdapter;
import com.team.donation.Adapter.OrgAdapter;
import com.team.donation.Adapter.UserAdapter;
import com.team.donation.Model.Accessories;
import com.team.donation.Model.Money;
import com.team.donation.Model.Organization;
import com.team.donation.Model.Transection;
import com.team.donation.Model.User;
import com.team.donation.R;
import com.team.donation.databinding.FragmentAllUserBinding;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllUserFragment extends Fragment {

    private FragmentAllUserBinding binding;
    private Context context;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private ProgressDialog progressDialog;

    private ArrayList<User> moneyArrayList;
    private UserAdapter adapter;

    private ArrayList<Organization> accessoriesArrayList;
    private OrgAdapter accAdapter;

    public AllUserFragment() {
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
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_all_user,container,false);
        init();
        configureRV();

        getAllmoney();
        getAllacc();


        return binding.getRoot();
    }


    private void configureRV() {


        adapter = new UserAdapter(context,moneyArrayList);
        binding.moneyRV.setLayoutManager(new LinearLayoutManager(context));
        binding.moneyRV.setAdapter(adapter);


        accAdapter = new OrgAdapter(context,accessoriesArrayList);
        binding.accRV.setLayoutManager(new LinearLayoutManager(context));
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

        Query query= databaseReference.child("users").orderByChild("accountType").equalTo("user");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    moneyArrayList.clear();
                    for (DataSnapshot data:dataSnapshot.getChildren()
                    ) {


                            User money = data.getValue(User.class);
                            moneyArrayList.add(money);

                    }
                    Log.d("TAG", "onDataChange money: "+moneyArrayList.size());
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

        Query query= databaseReference.child("users").orderByChild("accountType").equalTo("organization");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    accessoriesArrayList.clear();
                    for (DataSnapshot data:dataSnapshot.getChildren()
                    ) {
                        Organization accessories = data.getValue(Organization.class);
                        accessoriesArrayList.add(accessories);

                    }
                    Log.d("TAG", "onDataChange: acc"+accessoriesArrayList.size());
                    accAdapter.notifyDataSetChanged();
                    binding.animationView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
