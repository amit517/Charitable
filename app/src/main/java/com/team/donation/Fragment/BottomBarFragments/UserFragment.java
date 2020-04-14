package com.team.donation.Fragment.BottomBarFragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.team.donation.Activity.LoginActivity;
import com.team.donation.R;
import com.team.donation.Utils.GlobalVariables;
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

        binding.signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getContext().getSharedPreferences(GlobalVariables.sharedPref, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.remove(GlobalVariables.sharedPref);
                editor.remove(GlobalVariables.userID);
                editor.remove(GlobalVariables.userMode);

                editor.apply();
                firebaseAuth.signOut();
                startActivity(new Intent(getContext(), LoginActivity.class));
                getActivity().finish();
            }
        });


        return binding.getRoot();
    }
}
