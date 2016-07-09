package com.evgenii.walktocircle;

import android.app.Activity;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.evgenii.walktocircle.FragmentManager.WalkFragmentOpener;
import com.evgenii.walktocircle.FragmentManager.WalkFragmentType;
import com.evgenii.walktocircle.Fragments.WalkCongratulationsFragment;
import com.evgenii.walktocircle.Fragments.WalkWalkFragment;
import com.evgenii.walktocircle.Fragments.WalkLocationDeniedFragment;
import com.evgenii.walktocircle.Fragments.WalkMapFragment;
import com.evgenii.walktocircle.walkService.WalkInProgressService;
import com.evgenii.walktocircle.walkService.WalkInProgressServiceCallbacks;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;


public class MainActivity extends AppCompatActivity implements WalkInProgressServiceCallbacks {
    public static Activity instance;
    WalkInProgressService mWalkInProgressService;
    boolean mWalkInProgressServiceBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startWalkInPorgressService();

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
    protected void onDestroy() {
        unbindWalkInProgressService();

        super.onDestroy();
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
                WalkApplication.getLocationService().startLocationUpdatesIfNeeded();
                WalkFragmentType.showWithAnimation();
                WalkMapFragment.ifVisibleEnableMyLocationAndZoomToLastLocation();
            }
        };

        WalkLocationPermissions.getInstance().didDenyCallback = new Runnable() {
            @Override
            public void run() {
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

    // Walk in progress service
    // ----------------------

    private void startWalkInPorgressService() {
        Intent intent = new Intent(WalkApplication.getAppContext(), WalkInProgressService.class);
        WalkApplication.getAppContext().startService(intent);
        WalkApplication.getAppContext().bindService(intent, mConnection, 0);
    }

    private void unbindWalkInProgressService() {
        if (mWalkInProgressServiceBound) {
            Log.d("ii", "Unbind service");
            mWalkInProgressService.setCallbacks(null);
            WalkApplication.getAppContext().unbindService(mConnection);
        }
    }

    /** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            WalkInProgressService.WalkInProgressServiceBinder binder =
                    (WalkInProgressService.WalkInProgressServiceBinder) service;

            mWalkInProgressService = binder.getService();
            mWalkInProgressService.setCallbacks(MainActivity.this);
            mWalkInProgressServiceBound = true;
            Log.d("ii", "onServiceConnected");
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            Log.d("ii", "onServiceDisconnected");
            mWalkInProgressServiceBound = false;
        }
    };

    // WalkInProgressServiceCallbacks
    // ----------------------


    @Override
    public void didReachTheCircle() {
        Log.d("ii", "didReachTheCircle callback recieved");
    }
}
