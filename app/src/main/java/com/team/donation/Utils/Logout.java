package com.team.donation.Utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.google.firebase.auth.FirebaseAuth;
import com.team.donation.Activity.LoginActivity;

import static android.content.Context.MODE_PRIVATE;


public class Logout {
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    public static void logout(Context context){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        SharedPreferences sharedPreferences = context.getSharedPreferences(GlobalVariables.sharedPref, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.remove(GlobalVariables.sharedPref);
        editor.remove(GlobalVariables.userID);
        editor.remove(GlobalVariables.userMode);

        editor.apply();
        firebaseAuth.signOut();
        context.startActivity(new Intent(context, LoginActivity.class));
        ((Activity) context).finish();
    }

}
