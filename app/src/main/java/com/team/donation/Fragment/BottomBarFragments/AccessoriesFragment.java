package com.team.donation.Fragment.BottomBarFragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.team.donation.Adapter.AccessoriesAdapter;
import com.team.donation.Fragment.AddAccessoriesFragment;
import com.team.donation.Fragment.AddMoneyFragment;
import com.team.donation.Model.Accessories;
import com.team.donation.R;
import com.team.donation.databinding.FragmentAccessoriesBinding;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccessoriesFragment extends Fragment {
    private FragmentAccessoriesBinding binding;
    private Context context;
    private ArrayList<Accessories> accessoriesArrayList;
    private AccessoriesAdapter adapter;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private ProgressDialog progressDialog;
    private String userId;


    public AccessoriesFragment() {
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

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_accessories,container,false);

        init();
        configureRV();
        getAllacc();

        binding.addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                AddAccessoriesFragment clientDetailsFragment = new AddAccessoriesFragment();
                ft.replace(R.id.frame_layout, clientDetailsFragment);
                ft.addToBackStack(null);
                ft.commit();

            }
        });
        return binding.getRoot();
    }

    private void getAllacc() {

        DatabaseReference userRef = databaseReference.child("Accessories");
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    accessoriesArrayList.clear();
                    for (DataSnapshot data:dataSnapshot.getChildren()
                    ) {
                        Accessories accessories = data.getValue(Accessories.class);
                        accessoriesArrayList.add(accessories);

                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    private void configureRV() {
        adapter = new AccessoriesAdapter(context,accessoriesArrayList);
        binding.moneyRV.setLayoutManager(new LinearLayoutManager(context));
        binding.moneyRV.setAdapter(adapter);
    }

    private void init() {
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait...");
        accessoriesArrayList = new ArrayList<>();
    }
}
