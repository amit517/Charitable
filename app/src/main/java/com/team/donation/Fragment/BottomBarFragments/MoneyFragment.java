package com.team.donation.Fragment.BottomBarFragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.team.donation.Activity.AdminMainActivity;
import com.team.donation.Activity.LoginActivity;
import com.team.donation.Activity.OrganizerMainActivity;
import com.team.donation.Activity.UserMainActivity;
import com.team.donation.Adapter.MoneySecondAdapter;
import com.team.donation.Fragment.AddMoneyFragment;
import com.team.donation.Model.Accessories;
import com.team.donation.Model.Money;
import com.team.donation.Model.Transection;
import com.team.donation.R;
import com.team.donation.Utils.GlobalVariables;
import com.team.donation.databinding.FragmentMoneyBinding;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoneyFragment extends Fragment {

    private FragmentMoneyBinding binding;
    private Context context;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private ProgressDialog progressDialog;

    private ArrayList<Money> moneyArrayList;
    private MoneySecondAdapter adapter;


    public MoneyFragment() {
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
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_money,container,false);

        binding.animationView.setAnimation("19134-loading.json");
        binding.animationView.loop(true);
        binding.animationView.playAnimation();
        init();

        checkuser();
        configureRV();

        getAllmoney();
        binding.addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                AddMoneyFragment clientDetailsFragment = new AddMoneyFragment();
                ft.replace(R.id.frame_layout, clientDetailsFragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });






        return binding.getRoot();
    }

    private void getAllmoney() {

        DatabaseReference userRef = databaseReference.child("Money");
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    moneyArrayList.clear();
                    for (DataSnapshot data:dataSnapshot.getChildren()
                    ) {

                        data.getChildren().equals(Transection.class);

                        Money money = data.getValue(Money.class);

                        if (money.isEnabled()){
                            moneyArrayList.add(money);
                        }
                        Log.d("TAG", "onDataChange: "+dataSnapshot);

                    }
                    Log.d("TAG", "onDataChange: "+moneyArrayList.size());
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

        adapter = new MoneySecondAdapter(context,moneyArrayList,"org");

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
        moneyArrayList = new ArrayList<>();

    }


    private void checkuser() {

        SharedPreferences sharedPreferences = context.getSharedPreferences(GlobalVariables.sharedPref, Context.MODE_PRIVATE);

        String userMode = sharedPreferences.getString(GlobalVariables.userMode,"");

        Log.d("TAG", "checkuser: "+userMode);

        switch (userMode){
            case "User":
            case "Admin":
                binding.addNew.setVisibility(View.GONE);
                break;


            default:
                // Amar Mathay Bari Daw :P
        }
    }
}
