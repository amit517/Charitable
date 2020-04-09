package com.team.donation.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.team.donation.R;
import com.team.donation.databinding.FragmentAddAccessoriesBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddAccessoriesFragment extends Fragment {

    private Context context;
    private FragmentAddAccessoriesBinding binding;
    public AddAccessoriesFragment() {
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

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_add_accessories,container,false);


        return binding.getRoot();
    }
}
