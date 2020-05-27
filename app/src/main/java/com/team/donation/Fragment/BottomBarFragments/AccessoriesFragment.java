package com.team.donation.Fragment.BottomBarFragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Path;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.team.donation.Adapter.AccessoriesAdapter;
import com.team.donation.Fragment.AddAccessoriesFragment;
import com.team.donation.Fragment.AddMoneyFragment;
import com.team.donation.Model.Accessories;
import com.team.donation.R;
import com.team.donation.Utils.PushFragment;
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
                PushFragment.replaceFragment(context,new AddAccessoriesFragment(),"AddAcc");
            }
        });


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
                Log.d("TAG", "onItemSelected: "+binding.itemTypeSpinner.getSelectedItem().toString());
                if (!binding.itemTypeSpinner.getSelectedItem().toString().equals("Select One")){
                    adapter.clear();
                }
                Query query= databaseReference.child("Accessories").orderByChild("productType").equalTo(binding.itemTypeSpinner.getSelectedItem().toString());
                Log.d("TAG", "onItemSelected: "+binding.itemTypeSpinner.getSelectedItem().toString());

                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()){
                            accessoriesArrayList.clear();

                            for (DataSnapshot data : dataSnapshot.getChildren()){
                                Accessories accessories = data.getValue(Accessories.class);
                                accessoriesArrayList.add(accessories);
                            }
                            Log.d("TAG", "onItemSelected: "+accessoriesArrayList.size());
                            Log.d("TAG", "onItemSelected: "+accessoriesArrayList.get(0).getCreatorName());
                        }

                        adapter.notifyDataSetChanged();
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

    private void getAllacc() {

        Query userRef = databaseReference.child("Accessories");
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
                    binding.animationView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    private void configureRV() {
        adapter = new AccessoriesAdapter(context,accessoriesArrayList);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(context);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);

        binding.moneyRV.setLayoutManager(mLayoutManager);
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
