package com.team.donation.Activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.team.donation.R;
import com.team.donation.databinding.ActivitySplashBinding;

public class SplashActivity extends AppCompatActivity {
    public ActivitySplashBinding binding;
    private static final int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Request to show full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);





        new Handler().postDelayed(new Runnable() {
            public void run() {
                Intent mainIntent = new Intent(SplashActivity.this, LoginActivity.class);
                Bundle anim = ActivityOptions.makeCustomAnimation(getApplicationContext(),
                        R.anim.animation, R.anim.animation2).toBundle();
                startActivity(mainIntent, anim);

                SplashActivity.this.finish();

            }
        }, (long) SPLASH_TIME_OUT);
    }

}
