package com.evgenii.walktocircle;

import android.location.Location;
import android.util.Log;

import com.evgenii.walktocircle.fragmentManager.WalkFragmentType;
import com.evgenii.walktocircle.fragments.WalkMapFragment;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.Date;

public class WalkLocationService implements  com.google.android.gms.location.LocationListener {
    private boolean isUpdating = false;

    // The last time location updates has been started.
    // This is use to check the time period location updates are running.
    // Location updates are stopped if they have been running for too long to conserve the battery.
    private static Date mLastLocationUpdateStartTime = new Date();

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

        mLastLocationUpdateStartTime = new Date();
        if (isUpdating) { return; } // Already updating location

        if (!WalkGoogleApiClient.isConnected()) { return; }
        if (!WalkLocationPermissions.getInstance().hasLocationPermission()) { return; }

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationServices.FusedLocationApi.requestLocationUpdates(
                WalkGoogleApiClient.getInstance().getClient(), mLocationRequest, this);

        isUpdating = true;
    }

    private boolean areCircleUpdatesNeeded() {
        return MainActivityState.getInstance().getCurrentCircleLocation() != null;
    }

    public void stopLocationUpdatesIfNeeded() {
        if (!isUpdating) { return; } // Not updating
        if (mSendUpdatesToMap || areCircleUpdatesNeeded()) { return; } // Location updates are still needed
        stopLocationUpdates();
    }

    private void stopLocationUpdates() {
        if (!WalkGoogleApiClient.isConnected()) { return; }
        if (!WalkLocationPermissions.getInstance().hasLocationPermission()) { return; }

        LocationServices.FusedLocationApi.removeLocationUpdates(
                WalkGoogleApiClient.getInstance().getClient(), this);

        isUpdating = false;
    }

    @Override
    public void onLocationChanged(Location location) {
        if (areLocationUpdatesRunningForTooLongAndDrainingBattery()) {
            // Location updates are running for too long.
            // Stop the updates to conserve the battery.
            // User has probably abandoned the app.
            // Location updates will be restarted when the app is brought to front again.
            stopLocationUpdates();
            return;
        }

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

    private boolean areLocationUpdatesRunningForTooLongAndDrainingBattery() {
        long locationRunningTimeMilliseconds = new Date().getTime() - mLastLocationUpdateStartTime.getTime();
        return locationRunningTimeMilliseconds > (WalkConstants.maximumLocationUpdatesRunningTimeSeconds * 1000);
    }
}
