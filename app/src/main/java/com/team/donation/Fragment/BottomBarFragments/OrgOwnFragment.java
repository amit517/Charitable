package com.team.donation.Fragment.BottomBarFragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.team.donation.R;
import com.team.donation.databinding.FragmentOragOwnBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrgOwnFragment extends Fragment {
    private FragmentOragOwnBinding binding;
    private Context context;

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

        return binding.getRoot();
    }
}
