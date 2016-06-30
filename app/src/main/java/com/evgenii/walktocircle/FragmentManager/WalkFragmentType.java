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

        switch(this) {
            case Map: {
                if (fragment instanceof WalkMapFragment) { return fragment; }
            }

            case Walk: {
                if (fragment instanceof WalkFragment) { return fragment; }
            }

            case LocationDenied:  {
                if (fragment instanceof WalkLocationDeniedFragment) { return fragment; }
            }
        }

        return null;
    }

    public boolean isVisible(WalkFragmentOpener fragmentOpener) {
        Fragment fragment = fragmentOpener.getCurrentFragment();
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