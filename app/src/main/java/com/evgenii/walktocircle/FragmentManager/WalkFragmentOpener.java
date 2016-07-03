package com.evgenii.walktocircle.FragmentManager;

import android.app.Activity;
import android.app.Fragment;
import android.util.Log;

import com.evgenii.walktocircle.Fragments.WalkMapFragment;
import com.evgenii.walktocircle.MainActivity;
import com.evgenii.walktocircle.R;
import com.evgenii.walktocircle.WalkAnimation;
import com.evgenii.walktocircle.WalkApplication;

/**
 * Show a fragment with animation.
 */
public class WalkFragmentOpener {
    // true if fragments can be shown.
    private static boolean mCanShowFragments = false;

    /**
     * Call this method to ensure the fragments can be shown.
     */
    public static void allowShowingFragments() {
        mCanShowFragments = true;
    }

    /**
     * Call this method to prevent showing fragments.
     * This method is called in onSaveInstanceState activity method.
     */
    public static void disallowShowingFragments() {
        mCanShowFragments = false;
    }

    public static void showFragmentWithFlipAnimation(Fragment fragment) {
        if (!mCanShowFragments) { return; }

        Fragment currentFragment = getCurrentFragment();

        if (currentFragment == null) {
            // There are no currently shown fragments, show without animation
            MainActivity.instance.getFragmentManager()
                .beginTransaction()
                .add(R.id.container, fragment)
                .commit();
        } else if (currentFragment.getClass().equals(fragment.getClass())) {
            return;
        }

        // Show fragment with animation
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
