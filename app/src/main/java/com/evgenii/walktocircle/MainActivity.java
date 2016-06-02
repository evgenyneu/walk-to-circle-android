package com.evgenii.walktocircle;

import android.app.Dialog;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.evgenii.walktocircle.Fragments.WalkLocationDeniedFragment;
import com.evgenii.walktocircle.Fragments.WalkMapFragment;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        if (savedInstanceState == null) {
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, new WalkMapFragment())
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
    protected void onResume() {
        super.onResume();
        startGooglePlayServices();

        if (WalkLocationPermissions.getInstance().hasLocationPermission()) {
            // Show normal screen if we have location permission and showing "location denied" screen
            if (getLocationDeniedFragment() != null) {
                showMapFragment();
            }
        } else if (WalkLocationPermissions.getInstance().shouldShowLocationDeniedScreen(this)) {
            showLocationDeniedFragment();
        }
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
                    showMapFragment();
                    reloadMap();
                }
            }
        };
    }

    private void registerLocationPermissionCallback() {
        WalkLocationPermissions.getInstance().didGrantCallback = new Runnable() {
            @Override
            public void run() {
                showMapFragment();
                reloadMap();
            }
        };

        WalkLocationPermissions.getInstance().didDenyCallback = new Runnable() {
            @Override
            public void run() {
                showLocationDeniedFragment();
            }
        };
    }


    // Map
    // ----------------------

    void reloadMap() {
        WalkMapFragment map = getMapFragment();
        if (map != null) {
            map.enableMyLocationAndZoom();
        }
    }

    private void showMapFragment() {
        if (getMapFragment() != null) { return; } // Already showing map
        showFragmentWithFlipAnimation(new WalkMapFragment());
    }

    WalkMapFragment getMapFragment() {
        Fragment fragment = getCurrentFragment();
        if (fragment instanceof WalkMapFragment) { return (WalkMapFragment)fragment; }
        return null;
    }

    public void didTapMapButton(View view) {
        if (getMapFragment() == null) {
            showMapFragment();
        } else {
            showLocationDeniedFragment();
        }
    }

    // Location denied fragment
    // ----------------------

    public void locationDenied_didTapOpenSettingsButton(View view) {
        WalkLocationDeniedFragment fragment = getLocationDeniedFragment();
        if (fragment == null) { return; }
        fragment.didTapOpenSettings();
    }

    public void locationDenied_didTapRequestLocationPermissionButton(View view) {
        WalkLocationPermissions.getInstance().requestLocationPermissionIfNotGranted(this);
    }

    WalkLocationDeniedFragment getLocationDeniedFragment() {
        Fragment fragment = getCurrentFragment();
        if (fragment instanceof WalkLocationDeniedFragment) { return (WalkLocationDeniedFragment)fragment; }
        return null;
    }

    // Permissions
    // ----------------------

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        WalkLocationPermissions.getInstance().onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void showLocationDeniedFragment() {
        showFragmentWithFlipAnimation(new WalkLocationDeniedFragment());
    }

    // Show fragments
    // ----------------------

    private void showFragmentWithFlipAnimation(Fragment fragment) {
        Fragment currentFragment = getCurrentFragment();

        if (currentFragment != null && currentFragment.getClass().equals(fragment.getClass())) {
            return;
        }

        WalkAnimation animation = getNextAnimation();

        getFragmentManager()
            .beginTransaction()
            .setCustomAnimations(animation.enter, animation.exit, 0, 0)
            .replace(R.id.container, fragment)
            .commit();
    }

    private WalkAnimation getNextAnimation() {
        if (getCurrentFragment() instanceof WalkMapFragment) {
            return new WalkAnimation(R.animator.flip_top_in, R.animator.flip_top_out);
        } else {
            return new WalkAnimation(R.animator.flip_bottom_in, R.animator.flip_bottom_out);
        }
    }

    private Fragment getCurrentFragment() {
        return getFragmentManager().findFragmentById(R.id.container);
    }
}
