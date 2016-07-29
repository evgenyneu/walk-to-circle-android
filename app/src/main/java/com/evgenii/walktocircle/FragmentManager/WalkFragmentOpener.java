package com.evgenii.walktocircle.fragmentManager;

import android.app.Fragment;
import android.app.FragmentTransaction;

import com.evgenii.walktocircle.WalkApplication;
import com.evgenii.walktocircle.fragments.WalkCongratulationsFragment;
import com.evgenii.walktocircle.fragments.WalkMapFragment;
import com.evgenii.walktocircle.MainActivity;
import com.evgenii.walktocircle.R;
import com.evgenii.walktocircle.WalkAnimation;

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

    /**
     * Shows the current fragment to user unless it is already shown
     * @param withAnimation if true the fragment is shown with flip animation
     * @param fragment the fragment to be shown
     */
    public static void showFragment(boolean withAnimation, Fragment fragment) {
        if (!mCanShowFragments) { return; }

        Fragment currentFragment = getCurrentFragment();

        if (currentFragment == null) {
            // There are no currently shown fragments, show without animation
            MainActivity.instance.getFragmentManager()
                .beginTransaction()
                .add(R.id.container, fragment)
                .commit();

            return;
        } else if (currentFragment.getClass().equals(fragment.getClass())) {
            // The fragment is already shown.
            return;
        }

        FragmentTransaction fragmentTransaction = MainActivity.instance.getFragmentManager()
                .beginTransaction();

        if (withAnimation) {
            // Show fragment with animation
            WalkAnimation animation = getNextAnimation();
            fragmentTransaction.setCustomAnimations(animation.enter, animation.exit, 0, 0);
        }

        fragmentTransaction.replace(R.id.container, fragment).commit();
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
