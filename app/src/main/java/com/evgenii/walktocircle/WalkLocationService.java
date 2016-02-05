package com.evgenii.walktocircle;

import android.location.Location;
import android.util.Log;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class WalkLocationService {
    com.google.android.gms.location.LocationListener locationListener;

    public void startLocationUpdates(com.google.android.gms.location.LocationListener inLocationListener,
                                               long updateIntervalMilliseconds) {

        if (locationListener != null) { return; } // Already updating
        if (!WalkGoogleApiClient.isConnected()) { return; }
        if (!WalkLocationPermissions.getInstance().hasLocationPermission()) { return; }

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(updateIntervalMilliseconds);
        mLocationRequest.setFastestInterval(updateIntervalMilliseconds);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        this.locationListener = inLocationListener;

        LocationServices.FusedLocationApi.requestLocationUpdates(
                WalkGoogleApiClient.getInstance().getClient(), mLocationRequest, inLocationListener);

        Log.d("ii", "startLocationUpdates");
    }

    public void stopLocationUpdates() {
        if (locationListener == null) { return; } // Not updating
        if (!WalkGoogleApiClient.isConnected()) { return; }
        if (!WalkLocationPermissions.getInstance().hasLocationPermission()) { return; }

        LocationServices.FusedLocationApi.removeLocationUpdates(WalkGoogleApiClient.getInstance().getClient(),
                locationListener);

        locationListener = null;
        Log.d("ii", "stopLocationUpdates");
    }
}
