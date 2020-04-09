package com.team.donation.Fragment.BottomBarFragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.team.donation.Activity.AdminMainActivity;
import com.team.donation.Activity.LoginActivity;
import com.team.donation.Activity.OrganizerMainActivity;
import com.team.donation.Activity.UserMainActivity;
import com.team.donation.Fragment.AddMoneyFragment;
import com.team.donation.R;
import com.team.donation.Utils.GlobalVariables;
import com.team.donation.databinding.FragmentMoneyBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoneyFragment extends Fragment {

    private FragmentMoneyBinding binding;
    private Context context;
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

        checkuser();
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
