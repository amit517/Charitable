package com.team.donation.Fragment.BottomBarFragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

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
import com.team.donation.Adapter.AccAdapter;
import com.team.donation.Adapter.OwnAccessoriesAdapter;
import com.team.donation.Adapter.OwnMoneyAdapter;
import com.team.donation.Model.Accessories;
import com.team.donation.Model.Money;
import com.team.donation.R;
import com.team.donation.Utils.GlobalVariables;
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
    private OwnAccessoriesAdapter accessoriesAdapter;
    private AccAdapter adapter;
    private int currentPositon;

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

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

/*            Query moneyQuerary= databaseReference.child("Money").orderByChild("userId").equalTo(userId);
            moneyQuerary.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()){
                        moneyArrayList.clear();

                        for (DataSnapshot data : dataSnapshot.getChildren()){
                            Money money = data.getValue(Money.class);
                            moneyArrayList.add(money);
                        }
                        Log.d("TAG", "onChildAdded: "+accessoriesArrayList.size());
                        Log.d("TAG", "onChildAdded: "+accessoriesArrayList.get(0).getCreatorName());
                    }

                    //moneyAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });*/

        }
        else {

        }

        return binding.getRoot();
    }

    private void configureRV() {
        moneyAdapter = new OwnMoneyAdapter(context,moneyArrayList);
        binding.moneyRV.setLayoutManager(new LinearLayoutManager(context));
        binding.moneyRV.setAdapter(moneyAdapter);
        binding.moneyRV.setHasFixedSize(true);

        adapter= new AccAdapter(context,accessoriesArrayList,this);
        binding.accoriesRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.accoriesRV.setAdapter(adapter);


        /*accessoriesAdapter= new OwnAccessoriesAdapter(context,accessoriesArrayList);
        binding.accoriesRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.accoriesRV.setAdapter(adapter);*/
    }



    private void checkuser() {

        SharedPreferences sharedPreferences = context.getSharedPreferences(GlobalVariables.sharedPref, Context.MODE_PRIVATE);

        userMode = sharedPreferences.getString(GlobalVariables.userMode,"");

        Log.d("TAG", "checkuser: "+userMode);

        switch (userMode){
            case "User":
            case "Admin":

                /*binding.moneyTv.setVisibility(View.GONE);
                binding.moneyRV.setVisibility(View.GONE);*/

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









    }

    @Override
    public void deleteButtonclicked(int position) {


        currentPositon = position;

        Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();

        String key = accessoriesArrayList.get(position).getUniqueID();

        databaseReference.child("Accessories").child(key).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Success");
                    builder.setMessage("Successfully Deleted. Thanks for your collaboration");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            Fragment fragment = new OrgOwnFragment();
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.frame_layout, fragment);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();


                        }
                    })
                            .setCancelable(false);
                    AlertDialog alert = builder.create();
                    alert.show();

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
