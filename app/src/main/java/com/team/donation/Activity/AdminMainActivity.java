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
import com.team.donation.Fragment.AdminFragment.AllPostFragment;
import com.team.donation.Fragment.AdminFragment.AllUserFragment;
import com.team.donation.Fragment.AdminFragment.SeeDonationFragment;
import com.team.donation.Fragment.BottomBarFragments.AboutUsFragment;
import com.team.donation.Fragment.BottomBarFragments.AccessoriesFragment;
import com.team.donation.Fragment.BottomBarFragments.MoneyFragment;
import com.team.donation.Fragment.BottomBarFragments.OrgOwnFragment;
import com.team.donation.Fragment.BottomBarFragments.UserFragment;
import com.team.donation.R;
import com.team.donation.Utils.Logout;
import com.team.donation.databinding.ActivityAdminMainBinding;

public class AdminMainActivity extends AppCompatActivity{
    private ActivityAdminMainBinding binding;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_admin_main);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();


        new Thread(new Runnable() {
            public void run() {
                // a potentially time consuming task
                checkUser(new firebaseCallBack() {
                    @Override
                    public void Onresult(String user) {
                        Log.d("TAG", "Onresult: "+user);

                        if (!user.equals("admin")){
                            binding.frameLayout.setVisibility(View.GONE);
                            AlertDialog.Builder builder = new AlertDialog.Builder(AdminMainActivity.this,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                            builder.setTitle("Unauthorized");
                            builder.setIcon(R.drawable.ic_error_outline_black_24dp);
                            builder.setMessage("You are not authorized in this section.\nPlease select correct user mode and try again.");
                            builder.setNegativeButton("Logout", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Logout.logout(AdminMainActivity.this);
                                }
                            })
                                    .setCancelable(false);
                            AlertDialog alert = builder.create();
                            alert.show();

                        }
                    }
                });
            }
        }).start();


        replaceFragment(new AllPostFragment());

        binding.bottomNavBar.addBubbleListener(new OnBubbleClickListener() {
            @Override
            public void onBubbleClick(int i) {
                Log.d("ERR", "onBubbleClick: "+i);

                switch (i){
                    case R.id.posts:
                        replaceFragment(new AllPostFragment());
                        break;
                    case R.id.all_user:
                        replaceFragment(new AllUserFragment());
                        break;

                    case R.id.see_donation:
                        replaceFragment(new SeeDonationFragment());
                        break;
                    case R.id.user_profile:
                        replaceFragment(new UserFragment());
                        break;
                    default: // DashBoard Fragment
                        replaceFragment(new MoneyFragment());
                }

            }
        });

    }

    private void checkUser(final AdminMainActivity.firebaseCallBack firebaseCallBack) {

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

    @Override
    public void onBackPressed() {

        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count == 1) {
            super.onBackPressed();
            //additional code

            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Exit Charitable")
                    .setMessage("Are you sure you want to exit?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }

                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            replaceFragment(new AllPostFragment());
                        }
                    })
                    .setCancelable(false)
                    .show();

        } else {
            getSupportFragmentManager().popBackStack();
        }

    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frame_layout, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }


    private interface firebaseCallBack{
        void Onresult(String user);
    }
}
