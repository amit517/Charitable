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
import com.team.donation.databinding.FragmentOrganizerRegisterBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrganizerRegisterFragment extends Fragment {

    private FragmentOrganizerRegisterBinding binding;
    private Context context;
    public OrganizerRegisterFragment() {
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
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_organizer_register,container,false);



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
