package com.evgenii.walktocircle.FragmentManager;

import android.app.Fragment;

import com.evgenii.walktocircle.Fragments.WalkFragment;
import com.evgenii.walktocircle.Fragments.WalkLocationDeniedFragment;
import com.evgenii.walktocircle.Fragments.WalkMapFragment;
import com.evgenii.walktocircle.WalkLocationPermissions;

public enum WalkFragmentType {
    Map,
    Walk,
    LocationDenied;

    /**
     * @return fragment object or null if this fragment is not the one that is currently shown.
     */
    public Fragment getFragment() {
        Fragment fragment = WalkFragmentOpener.getCurrentFragment();
        if (isFragmentOfType(fragment)) { return fragment; }
        return null;
    }

    /**
     * @return true if this is the currently shown fragment.
     */
    public boolean isVisible() {
        Fragment fragment = getFragment();
        return fragment != null;
    }

    /**
     * @return the name of the fragment that is currently shown.
     */
    public static WalkFragmentType getCurrent() {
        // Always show Location Denied screen if there are no location permissions
        if (WalkLocationPermissions.getInstance().hasLocationPermission()) {
            return LocationDenied;
        }

        return Map;
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

    /**
     * Creates a new fragment object.
     * @return
     */
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

    /**
     * Shows the fragment to the user.
     */
    public void show() {
        if (isVisible()) { return; } // Already shown
        Fragment fragment = create();
        WalkFragmentOpener.showFragmentWithFlipAnimation(fragment);
    }
};