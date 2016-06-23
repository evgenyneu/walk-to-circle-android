package com.evgenii.walktocircle.Utils;

import android.location.Location;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

public class WalkLocation {
    public static Location locationFromLatLng(LatLng latLng) {
        Location location = new Location("any string");
        location.setLatitude(latLng.latitude);
        location.setLongitude(latLng.longitude);

        return location;
    }

    public static Location fromLatLng(double latitude, double longitude) {
        Location location = new Location("any string");
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        return location;
    }

    public static Location getMapCenter(GoogleMap map) {
        LatLng latLng = map.getCameraPosition().target;
        return locationFromLatLng(latLng);
    }
}
