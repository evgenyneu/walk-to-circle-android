package com.evgenii.walktocircle;

import android.location.Location;
import android.util.Log;

import com.evgenii.walktocircle.FragmentManager.WalkFragmentType;
import com.evgenii.walktocircle.Fragments.WalkMapFragment;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class WalkLocationService implements  com.google.android.gms.location.LocationListener {
    private boolean isUpdating = false;

    // Send location updates to the map fragment
    private boolean mSendUpdatesToMap = false;

    // Send location updates to detect if the user is inside the circle
    private boolean mSendCircleUpdates = false;

    public void startUpdatesForMap() {
        mSendUpdatesToMap = true;
        startLocationUpdates();
    }

    public void stopUpdatesForMap() {
        mSendUpdatesToMap = false;
        stopLocationUpdates();
    }

    public void startCircleUpdates() {
        mSendCircleUpdates = true;
        startLocationUpdates();
    }

    public void stopCircleUpdates() {
        mSendCircleUpdates = false;
        stopLocationUpdates();
    }

    public void startLocationUpdates() {
        if (!mSendUpdatesToMap && !mSendCircleUpdates) { return; } // Updates are not needed
        if (isUpdating) { return; } // Already updating location

        if (!WalkGoogleApiClient.isConnected()) { return; }
        if (!WalkLocationPermissions.getInstance().hasLocationPermission()) { return; }

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationServices.FusedLocationApi.requestLocationUpdates(
                WalkGoogleApiClient.getInstance().getClient(), mLocationRequest, this);

        Log.d("ii", "!!!!!! startLocationUpdates");

        isUpdating = true;
    }

    private void stopLocationUpdates() {
        if (!isUpdating) { return; } // Not updating
        if (mSendUpdatesToMap || mSendCircleUpdates) { return; } // Location updates are still needed

        if (!WalkGoogleApiClient.isConnected()) { return; }
        if (!WalkLocationPermissions.getInstance().hasLocationPermission()) { return; }

        LocationServices.FusedLocationApi.removeLocationUpdates(
            WalkGoogleApiClient.getInstance().getClient(), this);

        Log.d("ii", "!!!!!! stopLocationUpdates");

        isUpdating = false;
    }

    @Override
    public void onLocationChanged(Location location) {
        if (mSendUpdatesToMap) {
            WalkMapFragment mapFragment = (WalkMapFragment) WalkFragmentType.Map.getFragmentIfCurrentlyVisible();
            if (mapFragment != null) {
                mapFragment.didUpdateLocation(location);
            }
        }

        if (mSendCircleUpdates) {
            // Check if we reached the circle
            WalkCircleReachDetector.getInstance().checkReachedPosition(location);
        }
    }
}
