package com.team.donation.Utils;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.team.donation.R;

import java.util.Objects;

/**
 * Created by Amit on 02,May,2020
 */
public class PushFragment {

    public static void replaceFragment(Context context, Fragment fragment, String tag) {

        FragmentManager fm = ((FragmentActivity)context).getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        if (fm.findFragmentByTag ( tag ) == null) { // No fragment in backStack with same tag..
            ft.replace ( R.id.frame_layout, fragment, tag );
            ft.addToBackStack ( tag );
            ft.commit ();
        }
        else {
            ft.replace(R.id.frame_layout, fragment, tag).commit();
        }
    }
}
