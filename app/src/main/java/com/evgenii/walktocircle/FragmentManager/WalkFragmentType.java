package com.evgenii.walktocircle.FragmentManager;

import android.app.Fragment;

import com.evgenii.walktocircle.Fragments.WalkFragment;
import com.evgenii.walktocircle.Fragments.WalkLocationDeniedFragment;
import com.evgenii.walktocircle.Fragments.WalkMapFragment;

public enum WalkFragmentType {
    Map,
    Walk,
    LocationDenied;

    public Fragment getFragment(WalkFragmentOpener fragmentOpener) {
        Fragment fragment = fragmentOpener.getCurrentFragment();
        if (isFragmentOfType(fragment)) { return fragment; }
        return null;
    }

    /**
     * Returns true if the supplied fragment instance is of the current type
     */
    public boolean isFragmentOfType(Fragment fragment) {
        switch(this) {
            case Map: {
                if (fragment instanceof WalkMapFragment) { return true; }
            }

            case Walk: {
                if (fragment instanceof WalkFragment) { return true; }
            }

            case LocationDenied:  {
                if (fragment instanceof WalkLocationDeniedFragment) { return true; }
            }
        }

        return false;
    }

    public boolean isVisible(WalkFragmentOpener fragmentOpener) {
        Fragment fragment = getFragment(fragmentOpener);
        return fragment != null;
    }

    public Fragment create() {
        switch(this) {
            case Map: {
                return new WalkMapFragment();
            }

            case Walk: {
                return new WalkFragment();
            }

            case LocationDenied:  {
                return new WalkLocationDeniedFragment();
            }
        }

        return null;
    }

    public void show(WalkFragmentOpener fragmentOpener) {
        if (isVisible(fragmentOpener)) { return; } // Already shown
        Fragment fragment = create();
        fragmentOpener.showFragmentWithFlipAnimation(fragment);
    }
};