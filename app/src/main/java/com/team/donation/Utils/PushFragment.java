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
            ft.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
            ft.commit ();
        }
        else {
            ft.replace(R.id.frame_layout, fragment, tag).setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit).commit();
        }
    }

    public static void reloadFragment(Context context,String tag){
        Fragment frg = null;
        frg = ((FragmentActivity)context).getSupportFragmentManager().findFragmentByTag(tag);
        final FragmentTransaction ft = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
        ft.detach(frg);
        ft.attach(frg);
        ft.setCustomAnimations(R.animator.slide_in_left,
                R.animator.slide_out_right, 0, 0);
        ft.commit();
    }
}
