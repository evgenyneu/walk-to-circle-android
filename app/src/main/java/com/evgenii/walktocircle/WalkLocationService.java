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

    public void startUpdatesForMap() {
        mSendUpdatesToMap = true;
        startLocationUpdatesIfNeeded();
    }

    public void stopUpdatesForMap() {
        mSendUpdatesToMap = false;
        stopLocationUpdatesIfNeeded();
    }

    public void startLocationUpdatesIfNeeded() {
        if (!mSendUpdatesToMap && !areCircleUpdatesNeeded()) { return; } // Updates are not needed
        if (isUpdating) { return; } // Already updating location

        if (!WalkGoogleApiClient.isConnected()) { return; }
        if (!WalkLocationPermissions.getInstance().hasLocationPermission()) { return; }

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationServices.FusedLocationApi.requestLocationUpdates(
                WalkGoogleApiClient.getInstance().getClient(), mLocationRequest, this);

        Log.d("ii", "!!!!!! startLocationUpdates " + (mSendUpdatesToMap? "MAP" : "") + " " + (areCircleUpdatesNeeded() ? "CIRCLE": ""));

        isUpdating = true;
    }

    private boolean areCircleUpdatesNeeded() {
        return MainActivityState.getInstance().getCurrentCircleLocation() != null;
    }

    public void stopLocationUpdatesIfNeeded() {
        if (!isUpdating) { return; } // Not updating
        if (mSendUpdatesToMap || areCircleUpdatesNeeded()) { return; } // Location updates are still needed

        if (!WalkGoogleApiClient.isConnected()) { return; }
        if (!WalkLocationPermissions.getInstance().hasLocationPermission()) { return; }

        LocationServices.FusedLocationApi.removeLocationUpdates(
            WalkGoogleApiClient.getInstance().getClient(), this);

        Log.d("ii", "!!!!!! stopLocationUpdates " + (mSendUpdatesToMap? "MAP" : "") + " " + (areCircleUpdatesNeeded() ? "CIRCLE": ""));

        isUpdating = false;
    }

    @Override
    public void onLocationChanged(Location location) {
        if (mSendUpdatesToMap) {
            WalkMapFragment mapFragment = (WalkMapFragment) WalkFragmentType.Map.getFragmentIfCurrentlyVisibleAndShouldBeVisible();
            if (mapFragment != null) {
                mapFragment.didUpdateLocation(location);
            }
        }

        if (areCircleUpdatesNeeded()) {
            // Check if we reached the circle
            WalkCircleReachDetector.getInstance().checkReachedPosition(location);
        }
    }
}
