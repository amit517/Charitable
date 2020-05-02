package com.team.donation.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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
import com.team.donation.Utils.PushFragment;
import com.team.donation.databinding.ActivityAdminMainBinding;

import java.util.concurrent.CopyOnWriteArrayList;

public class AdminMainActivity extends AppCompatActivity{
    private ActivityAdminMainBinding binding;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private Fragment contentFragment;
    private boolean doubleBackToExitPressedOnce;
    private Context context = AdminMainActivity.this;

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


        PushFragment.replaceFragment(context,new AllPostFragment(),"AllPost");

        binding.bottomNavBar.addBubbleListener(new OnBubbleClickListener() {
            @Override
            public void onBubbleClick(int i) {
                Log.d("ERR", "onBubbleClick: "+i);

                switch (i){
                    case R.id.posts:
                        PushFragment.replaceFragment(context,new AllPostFragment(),"AllPost");
                        break;
                    case R.id.all_user:
                        PushFragment.replaceFragment(context,new AllUserFragment(),"AllUser");
                        break;

                    case R.id.see_donation:
                        PushFragment.replaceFragment(context,new SeeDonationFragment(),"See Donation");
                        break;
                    case R.id.user_profile:
                        PushFragment.replaceFragment(context,new UserFragment(),"Profile");
                        break;
                    default:
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
    protected void onResume() {
        super.onResume();
        doubleBackToExitPressedOnce = true;
    }

    @Override
    public void onBackPressed() {

        final FragmentManager fm = getSupportFragmentManager();
        Fragment contentFragment = getSupportFragmentManager().getFragments()
                .get(getSupportFragmentManager().getFragments().size() - 1);

        if (contentFragment instanceof AllPostFragment ||
                contentFragment instanceof AllUserFragment ||
                contentFragment instanceof SeeDonationFragment ||
                contentFragment instanceof UserFragment ||
                fm.getBackStackEntryCount() == 0) {

            if (doubleBackToExitPressedOnce) {
                this.doubleBackToExitPressedOnce = false;
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        doubleBackToExitPressedOnce = true;
                    }
                }, 2000);
                Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();
            } else {
                finish();
            }
        } else if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStack();
            if (fm.getBackStackEntryCount() != 0) {
                return;
            }
        }
    }


    private interface firebaseCallBack{
        void Onresult(String user);
    }
}
