package com.evgenii.walktocircle.Utils;

import android.graphics.Point;
import android.location.Location;

import com.evgenii.walktocircle.WalkConstants;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.Projection;
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

    /**
     * Convert the distance in meters to pixels on the map
     * @param map map object.
     * @param meters distance in meters.
     * @return distance in pixels on the map.
     */
    public static double fromMetersToMapPixels(GoogleMap map, double meters) {
        Location locationSource = fromLatLng(35, 139); // Arbitrary location
        Location locationDestination = WalkGeo.destination(locationSource, meters, 90.0);
        Projection projection = map.getProjection();
        Point locationSourcePixels = projection.toScreenLocation(WalkLocation.latLngFromLocation(locationSource));
        Point locationDestinationPixels = projection.toScreenLocation(WalkLocation.latLngFromLocation(locationDestination));

        return locationDestinationPixels.x - locationSourcePixels.x;
    }

    public static LatLng latLngFromLocation(Location location) {
        return new LatLng(location.getLatitude(), location.getLongitude());
    }
}
