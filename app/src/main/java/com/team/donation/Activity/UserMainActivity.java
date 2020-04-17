package com.team.donation.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.fxn.OnBubbleClickListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.team.donation.Fragment.BottomBarFragments.AboutUsFragment;
import com.team.donation.Fragment.BottomBarFragments.AccessoriesFragment;
import com.team.donation.Fragment.BottomBarFragments.MoneyFragment;
import com.team.donation.Fragment.BottomBarFragments.OrgOwnFragment;
import com.team.donation.Fragment.BottomBarFragments.UserFragment;
import com.team.donation.R;
import com.team.donation.Utils.Logout;
import com.team.donation.databinding.ActivityUserMainBinding;

public class UserMainActivity extends AppCompatActivity {

    private ActivityUserMainBinding binding;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_user_main);


        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();



        checkUser(new firebaseCallBack() {
            @Override
            public void Onresult(String user) {

                if (!user.equals("user")){

                    binding.frameLayout.setVisibility(View.GONE);
                    AlertDialog.Builder builder = new AlertDialog.Builder(UserMainActivity.this,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                    builder.setTitle("Unauthorized");
                    builder.setIcon(R.drawable.ic_error_outline_black_24dp);
                    builder.setMessage("You are not authorized in this section.\nPlease select correct user mode and try again.");
                    builder.setNegativeButton("Logout", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Logout.logout(UserMainActivity.this);
                        }
                    })
                            .setCancelable(false);
                    AlertDialog alert = builder.create();
                    alert.show();

                }

            }
        });



        replaceFragment(new MoneyFragment());
        binding.bottomNavBar.addBubbleListener(new OnBubbleClickListener() {
            @Override
            public void onBubbleClick(int i) {
                Log.d("ERR", "onBubbleClick: "+i);

                switch (i){
                    case R.id.money:
                        replaceFragment(new MoneyFragment());
                        break;
                    case R.id.accessories:
                        replaceFragment(new AccessoriesFragment());
                        break;

                    case R.id.own_post:
                        replaceFragment(new OrgOwnFragment());
                        break;
                    case R.id.user_profile:
                        replaceFragment(new UserFragment());
                        break;
                    case R.id.about:
                        replaceFragment(new AboutUsFragment());
                        break;

                    default: // DashBoard Fragment
                        replaceFragment(new MoneyFragment());
                }

            }
        });

    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frame_layout, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void onBackPressed() {

        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count == 1) {
            super.onBackPressed();
            //additional code

            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Closing Activity")
                    .setMessage("Are you sure you want to close this activity?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }

                    })
                    .setNegativeButton("No", null)
                    .show();

        } else {
            getSupportFragmentManager().popBackStack();
        }

    }


    private void checkUser(final UserMainActivity.firebaseCallBack firebaseCallBack) {

        String userID = firebaseAuth.getUid();

        Query query = databaseReference.child("users").child(userID).child("accountType");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String user= dataSnapshot.getValue(String.class);
                Log.d("TAG", "onDataChange: "+user);
                firebaseCallBack.Onresult(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private interface firebaseCallBack{
        void Onresult(String user);
    }


}
