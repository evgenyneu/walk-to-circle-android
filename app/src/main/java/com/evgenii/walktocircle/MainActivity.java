package com.evgenii.walktocircle;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.evgenii.walktocircle.FragmentManager.WalkFragmentOpener;
import com.evgenii.walktocircle.FragmentManager.WalkFragmentType;
import com.evgenii.walktocircle.Fragments.WalkLocationDeniedFragment;
import com.evgenii.walktocircle.Fragments.WalkMapFragment;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;


public class MainActivity extends AppCompatActivity {
    WalkFragmentOpener mFragmentOpener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFragmentOpener = new WalkFragmentOpener(this);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        if (savedInstanceState == null) {
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, WalkFragmentType.Map.create())
                    .commit();
        }

        registerApiClientCallback();
        registerLocationPermissionCallback();

        if (!WalkLocationPermissions.getInstance().shouldShowLocationDeniedScreen(this)) {
            // Request location permission if we are not going to show location denied screen in onResume
            WalkLocationPermissions.getInstance().requestLocationPermissionIfNotGranted(this);
        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();

        WalkApplication.activityResumed();
        startGooglePlayServices();

        if (WalkLocationPermissions.getInstance().hasLocationPermission()) {
            // Show normal screen if we have location permission and showing "location denied" screen
            if (WalkFragmentType.LocationDenied.isVisible(mFragmentOpener)) {
                WalkFragmentType.Map.show(mFragmentOpener);
            }
        } else if (WalkLocationPermissions.getInstance().shouldShowLocationDeniedScreen(this)) {
            WalkFragmentType.LocationDenied.show(mFragmentOpener);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        WalkApplication.activityPaused();
    }

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
                    WalkFragmentType.Map.show(mFragmentOpener);
                    reloadMap();
                }
            }
        };
    }

    private void registerLocationPermissionCallback() {
        WalkLocationPermissions.getInstance().didGrantCallback = new Runnable() {
            @Override
            public void run() {
                WalkFragmentType.Map.show(mFragmentOpener);
                reloadMap();
            }
        };

        WalkLocationPermissions.getInstance().didDenyCallback = new Runnable() {
            @Override
            public void run() {
                WalkFragmentType.LocationDenied.show(mFragmentOpener);
            }
        };
    }


    // Map fragment
    // ----------------------

    void reloadMap() {
        WalkMapFragment map = getMapFragment();
        if (map != null) {
            map.enableMyLocationAndZoomToLastLocation();
        }
    }

    WalkMapFragment getMapFragment() {
        return (WalkMapFragment) WalkFragmentType.Map.getFragment(mFragmentOpener);
    }

    public void didTapMapButton(View view) {
        WalkMapFragment mapFragment = getMapFragment();
        if (mapFragment != null) {
            mapFragment.didTapStartButton();
        }
    }


    // Location denied fragment
    // ----------------------

    public void locationDenied_didTapOpenSettingsButton(View view) {
        WalkLocationDeniedFragment fragment = (WalkLocationDeniedFragment)
                WalkFragmentType.LocationDenied.getFragment(mFragmentOpener);

        if (fragment == null) { return; }
        fragment.didTapOpenSettings();
    }

    public void locationDenied_didTapRequestLocationPermissionButton(View view) {
        WalkLocationPermissions.getInstance().requestLocationPermissionIfNotGranted(this);
    }

    // Permissions
    // ----------------------

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        WalkLocationPermissions.getInstance().onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
