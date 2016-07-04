package com.evgenii.walktocircle.FragmentManager;

import android.app.Fragment;

import com.evgenii.walktocircle.Fragments.WalkWalkFragment;
import com.evgenii.walktocircle.Fragments.WalkLocationDeniedFragment;
import com.evgenii.walktocircle.Fragments.WalkMapFragment;
import com.evgenii.walktocircle.MainActivityState;
import com.evgenii.walktocircle.WalkLocationPermissions;

public enum WalkFragmentType {
    Map,
    Walk,
    LocationDenied;

    public static WalkFragmentType shouldBeDisplayedNow() {
        // Location Denied: If location permission is not granted
        if (!WalkLocationPermissions.getInstance().hasLocationPermission()) {
            return LocationDenied;
        }

        // Walk: if user has dropped the pin
        if (MainActivityState.getInstance().getCurrentCircleLocation() != null) {
            return Walk;
        }

        return Map;
    }

    /**
     * Create and show the current fragment with animation.
     */
    public static void showWithAnimation() {
        shouldBeDisplayedNow().createAndShowWithAnimation();
    }

    /**
     * @return fragment object or null if this fragment is not the one that is currently shown.
     */
    public Fragment getFragmentIfCurrentlyVisible() {
        if (!shouldBeVisibleNow()) { return null; }
        Fragment fragment = WalkFragmentOpener.getCurrentFragment();
        if (isFragmentOfType(fragment)) { return fragment; }
        return null;
    }

    /**
     *
     * @return true if the fragment should be visible now.
     */
    public boolean shouldBeVisibleNow() {
        return WalkFragmentType.shouldBeDisplayedNow() == this;
    }

    /**
     * @return true if this is the currently shown fragment.
     */
    public boolean isVisible() {
        Fragment fragment = getFragmentIfCurrentlyVisible();
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
            case Map:
                if (fragment instanceof WalkMapFragment) { return true; }
                break;

            case Walk:
                if (fragment instanceof WalkWalkFragment) { return true; }
                break;

            case LocationDenied:
                if (fragment instanceof WalkLocationDeniedFragment) { return true; }
                break;
        }

        return false;
    }

    /**
     * Creates a new fragment object.
     * @return
     */
    public Fragment create() {
        switch(this) {
            case Map:
                return new WalkMapFragment();

            case Walk:
                return new WalkWalkFragment();

            case LocationDenied:
                return new WalkLocationDeniedFragment();
        }

        return null;
    }

    /**
     * Shows the fragment to the user.
     */
    private void createAndShowWithAnimation() {
        if (isVisible()) { return; } // Already shown
        Fragment fragment = create();
        WalkFragmentOpener.showFragmentWithFlipAnimation(fragment);
    }
};