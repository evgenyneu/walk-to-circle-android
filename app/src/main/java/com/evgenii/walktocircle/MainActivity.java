package com.evgenii.walktocircle;

import android.app.Activity;
import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.evgenii.walktocircle.fragmentManager.WalkFragmentOpener;
import com.evgenii.walktocircle.fragmentManager.WalkFragmentType;
import com.evgenii.walktocircle.fragments.WalkCongratulationsFragment;
import com.evgenii.walktocircle.fragments.WalkWalkFragment;
import com.evgenii.walktocircle.fragments.WalkLocationDeniedFragment;
import com.evgenii.walktocircle.fragments.WalkMapFragment;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;


public class MainActivity extends AppCompatActivity  {
    public static Activity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Save the instance to this Activity object
        instance = this;

        // Load saved data
        MainActivityState.load();

        // Make activity full screen
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);


        // Show map activity
        if (savedInstanceState == null) {
            WalkFragmentType.showWithAnimation();
        }

        // Show map when Google API is connected
        registerApiClientCallback();

        // Show map when user grants location permission
        registerLocationPermissionCallback();

        // Request location permission if we are not showing location denied screen
        if (!WalkLocationPermissions.getInstance().shouldShowRequestPermissionRationale()) {
            WalkLocationPermissions.getInstance().requestLocationPermissionIfNotGranted(this);
        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();

        WalkFragmentOpener.allowShowingFragments();
        WalkFragmentType.showWithAnimation();
        WalkApplication.getLocationService().startLocationUpdatesIfNeeded();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        WalkFragmentOpener.disallowShowingFragments();

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();

        WalkApplication.activityResumed();
        startGooglePlayServices();
        new WalkNotification().remove();
    }

    @Override
    protected void onPause() {
        super.onPause();

        WalkApplication.activityPaused();
    }

    // Google API
    // ----------------------

    private void startGooglePlayServices() {
        int resultCode = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);

        if (resultCode == ConnectionResult.SUCCESS) {
            WalkGoogleApiClient.getInstance().create();
        } else if (resultCode == ConnectionResult.SERVICE_MISSING ||
                resultCode == ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED ||
                resultCode == ConnectionResult.SERVICE_DISABLED) {

            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(this, resultCode, 1);
            dialog.show();
        }
    }

    private void registerApiClientCallback() {
        WalkGoogleApiClient.getInstance().didConnectCallback = new Runnable() {
            @Override
            public void run() {
                if (WalkLocationPermissions.getInstance().hasLocationPermission()) {
                    WalkApplication.getLocationService().startLocationUpdatesIfNeeded();
                    WalkFragmentType.showWithAnimation();
                    WalkMapFragment.ifVisibleEnableMyLocationAndZoomToLastLocation();
                }
            }
        };
    }

    // Permissions
    // ----------------------

    private void registerLocationPermissionCallback() {
        WalkLocationPermissions.getInstance().didGrantCallback = new Runnable() {
            @Override
            public void run() {
                MainActivityState.saveUserDidMakeLocationPermissionChoice(true);
                WalkApplication.getLocationService().startLocationUpdatesIfNeeded();
                WalkFragmentType.showWithAnimation();
                WalkMapFragment.ifVisibleEnableMyLocationAndZoomToLastLocation();

            }
        };

        WalkLocationPermissions.getInstance().didDenyCallback = new Runnable() {
            @Override
            public void run() {
                MainActivityState.saveUserDidMakeLocationPermissionChoice(true);
                WalkFragmentType.showWithAnimation();
            }
        };
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        WalkLocationPermissions.getInstance().onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    // Map fragment
    // ----------------------

    public void didTapMapButton(View view) {
        WalkMapFragment fragment = (WalkMapFragment) WalkFragmentType.Map.getFragmentIfCurrentlyVisible();

        if (fragment != null) {
            fragment.didTapStartButton();
        }
    }

    // Walk fragment
    // ----------------------

    public void didTapCloseWalkButton(View view) {
        WalkWalkFragment fragment = (WalkWalkFragment) WalkFragmentType.Walk.getFragmentIfCurrentlyVisibleAndShouldBeVisible();

        if (fragment != null) {
            fragment.didTapCloseButton();
        }
    }

    // Congratulations fragment
    // ----------------------

    public void didTapProceedFromCongratulations(View view) {
        WalkCongratulationsFragment fragment = (WalkCongratulationsFragment) WalkFragmentType.Congratulations.getFragmentIfCurrentlyVisibleAndShouldBeVisible();

        if (fragment != null) {
            fragment.didTapProceedButton();
        }
    }


    // Location denied fragment
    // ----------------------

    public void locationDenied_didTapOpenSettingsButton(View view) {
        WalkLocationDeniedFragment fragment = (WalkLocationDeniedFragment)
                WalkFragmentType.LocationDenied.getFragmentIfCurrentlyVisibleAndShouldBeVisible();

        if (fragment == null) { return; }
        fragment.didTapOpenSettings();
    }

    public void locationDenied_didTapRequestLocationPermissionButton(View view) {
        WalkLocationPermissions.getInstance().requestLocationPermissionIfNotGranted(this);
    }
}
