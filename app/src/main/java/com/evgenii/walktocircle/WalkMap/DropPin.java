package com.evgenii.walktocircle.WalkMap;

import android.location.Location;

import com.evgenii.walktocircle.Utils.WalkGeo;
import com.evgenii.walktocircle.Utils.WalkLocation;
import com.evgenii.walktocircle.WalkConstants;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class DropPin {
    static private Marker mPreviousMarker;

    public void dropPin(Location currentLocation, GoogleMap map) {
        removePreviousMarker();

        Location pinLocation = WalkGeo.randomLocationAtDistanceRange(currentLocation,
                WalkConstants.minCircleDistanceFromCurrentLocationMeters,
                WalkConstants.maxCircleDistanceFromCurrentLocationMeters);

        LatLng latLng = WalkLocation.latLngFromLocation(pinLocation);
        MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("Walk to Circle");
        mPreviousMarker = map.addMarker(markerOptions);
    }

    private void removePreviousMarker() {
        if (mPreviousMarker == null) { return; }
        mPreviousMarker.remove();
    }
}
