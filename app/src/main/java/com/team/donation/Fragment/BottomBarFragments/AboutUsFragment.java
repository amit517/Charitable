package com.team.donation.Fragment.BottomBarFragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.team.donation.Activity.DonateAdminActivity;
import com.team.donation.R;
import com.team.donation.databinding.FragmentAboutUsBinding;

public class AboutUsFragment extends Fragment {

    private FragmentAboutUsBinding binding;
    private Context context;
    public AboutUsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_about_us,container,false);

        binding.phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = "tel:01832706402";
                Intent call = new Intent (Intent.ACTION_DIAL, Uri.parse(number));
                startActivity(call);
            }
        });

        binding.email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String[] TO = {"charitable321@gmail.com"};

                Intent intent = new Intent(Intent.ACTION_SEND);

                intent.setType("text/html");
                intent.putExtra(Intent.EXTRA_EMAIL, TO);
                intent.putExtra(Intent.EXTRA_SUBJECT, "Hello");
                intent.putExtra(Intent.EXTRA_TEXT, "Hi Charitable,");
                startActivity(Intent.createChooser(intent, "Send Email"));


            }
        });

        binding.support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DonateAdminActivity.class);
                context.startActivity(intent);
            }
        });



        return binding.getRoot();
    }
}
