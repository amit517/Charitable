package com.team.donation.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.team.donation.Activity.LoginActivity;
import com.team.donation.R;
import com.team.donation.databinding.FragmentUserRegistationBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserRegisterFragment extends Fragment {

    private FragmentUserRegistationBinding binding;
    private Context context;
    public UserRegisterFragment() {
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
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_user_registation,container,false);

        binding.signInTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, LoginActivity.class);
                startActivity(intent);
            }
        });


        return binding.getRoot();
    }
}
