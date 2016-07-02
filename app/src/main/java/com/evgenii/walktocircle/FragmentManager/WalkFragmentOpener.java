package com.evgenii.walktocircle.FragmentManager;

import android.app.Activity;
import android.app.Fragment;

import com.evgenii.walktocircle.Fragments.WalkMapFragment;
import com.evgenii.walktocircle.MainActivity;
import com.evgenii.walktocircle.R;
import com.evgenii.walktocircle.WalkAnimation;

/**
 * Show a fragment with animation.
 */
public class WalkFragmentOpener {
    public static void showFragmentWithFlipAnimation(Fragment fragment) {
        Fragment currentFragment = getCurrentFragment();

        if (currentFragment != null && currentFragment.getClass().equals(fragment.getClass())) {
            return;
        }

        WalkAnimation animation = getNextAnimation();

        MainActivity.instance.getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(animation.enter, animation.exit, 0, 0)
                .replace(R.id.container, fragment)
                .commit();
    }

    private static WalkAnimation getNextAnimation() {
        if (getCurrentFragment() instanceof WalkMapFragment) {
            return new WalkAnimation(R.animator.flip_top_in, R.animator.flip_top_out);
        } else {
            return new WalkAnimation(R.animator.flip_bottom_in, R.animator.flip_bottom_out);
        }
    }

    public static Fragment getCurrentFragment() {
        return MainActivity.instance.getFragmentManager().findFragmentById(R.id.container);
    }
}
