package com.evgenii.walktocircle;

import android.location.Location;
import android.util.Log;

import com.evgenii.walktocircle.FragmentManager.WalkFragmentType;
import com.evgenii.walktocircle.Fragments.WalkMapFragment;
import com.evgenii.walktocircle.WalkWalk.WalkQuote;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class WalkLocationService implements  com.google.android.gms.location.LocationListener {
    private boolean isUpdating = false;

    private boolean mSendUpdatesToMap = false;

    public void startUpdatesForMap() {
        mSendUpdatesToMap = true;
        startLocationUpdates();
    }

    public void stopUpdatesForMap() {
        mSendUpdatesToMap = false;
        stopLocationUpdates();
    }

    private void startLocationUpdates() {
        if (!mSendUpdatesToMap) { return; } // Updates are not needed

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
        if (mSendUpdatesToMap) { return; } // Need updates

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
    }
}
