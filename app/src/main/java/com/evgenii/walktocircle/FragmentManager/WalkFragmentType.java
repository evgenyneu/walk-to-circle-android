package com.evgenii.walktocircle.fragmentManager;

import android.app.Fragment;

import com.evgenii.walktocircle.fragments.WalkCongratulationsFragment;
import com.evgenii.walktocircle.fragments.WalkWalkFragment;
import com.evgenii.walktocircle.fragments.WalkLocationDeniedFragment;
import com.evgenii.walktocircle.fragments.WalkMapFragment;
import com.evgenii.walktocircle.MainActivityState;
import com.evgenii.walktocircle.WalkLocationPermissions;

public enum WalkFragmentType {
    Map,
    Walk,
    Congratulations,
    LocationDenied;

    /**
     *
     * @return fragment type that should be displayed now.
     */
    private static WalkFragmentType shouldBeDisplayedNow() {
        // Location Denied: If location permission is not granted by user
        if (!WalkLocationPermissions.getInstance().hasLocationPermission()) {
            return LocationDenied;
        }

        // Walk: if user has dropped the pin
        if (MainActivityState.getInstance().getCurrentCircleLocation() != null) {
            return Walk;
        }

        // Congratulations: shown when user reaches the circle.
        if (MainActivityState.getInstance().getShowCongratulationsScreen()) {
            return Congratulations;
        }

        return Map;
    }

    /**
     * Create and show the current fragment with animation.
     */
    public static void showWithAnimation() {
        shouldBeDisplayedNow().createAndShow(true);
    }

    /**
     * Create and show the current fragment without animation.
     */
    public static void showWithoutAnimation() {
        shouldBeDisplayedNow().createAndShow(false);
    }

    /**
     * @return fragment object or null if this fragment is not the one that is currently shown.
     * Returns null if the fragment should not be shown now.
     */
    public Fragment getFragmentIfCurrentlyVisibleAndShouldBeVisible() {
        if (!shouldBeVisibleNow()) { return null; }
        Fragment fragment = WalkFragmentOpener.getCurrentFragment();
        if (isFragmentOfType(fragment)) { return fragment; }
        return null;
    }

    /**
     *
     * @return fragment object or null if this fragment is not the one that is currently shown.
     */
    public Fragment getFragmentIfCurrentlyVisible() {
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
    private boolean isVisible() {
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

            case Congratulations:
                if (fragment instanceof WalkCongratulationsFragment) { return true; }
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

            case Congratulations:
                return new WalkCongratulationsFragment();

            case LocationDenied:
                return new WalkLocationDeniedFragment();
        }

        return null;
    }

    /**
     * Shows the fragment to the user.
     * @param withAnimation indicates whether to show the fragment with animation.
     */
    private void createAndShow(boolean withAnimation) {
        if (isVisible()) { return; } // Already shown
        Fragment fragment = create();
        WalkFragmentOpener.showFragment(withAnimation, fragment);
    }
};