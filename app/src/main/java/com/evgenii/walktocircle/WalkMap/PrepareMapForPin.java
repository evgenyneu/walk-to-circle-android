package com.evgenii.walktocircle.WalkMap;

import android.location.Location;

import com.evgenii.walktocircle.Utils.WalkLocation;
import com.evgenii.walktocircle.WalkConstants;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

public class PrepareMapForPin {
    public void prepare(Location userLocation, Location pinLocation, GoogleMap map, GoogleMap.CancelableCallback callback) {
        animateCameraToUserLocation(userLocation, map, callback);
    }

    /**
     * Animates the camera to current user's location with default zoom level.
     * If user's locatino is already shown on the map with proper zoom the camera animation
     * is not performed and the callback is called right away.
     * @param userLocation location which is used to animate the camera to
     * @param map the map object
     * @param callback called after camera animation is complete
     */
    public void animateCameraToUserLocation(Location userLocation, GoogleMap map, GoogleMap.CancelableCallback callback) {
        CameraUpdate update = getUserCenteredCameraUpdate(userLocation, map);

        if (update != null) {
            map.animateCamera(update, WalkConstants.mapPositionAnimationDurationMilliseconds, callback);
        } else {
            callback.onFinish();
        }
    }

    /**
     *
     * @return Returns the update for the camera centered at the user at the right zoom level.
     * Returns null if no camera update is necessary.
     */
    private CameraUpdate getUserCenteredCameraUpdate(Location userLocation, GoogleMap map) {
        LatLng userLatLng = WalkLocation.latLngFromLocation(userLocation);

        CameraPosition.Builder cameraPositionBuilder = new CameraPosition.Builder(map.getCameraPosition());
        boolean didChangeCamera = false;

        Location mapCenter =  WalkLocation.getMapCenter(map);
        if (mapCenter.distanceTo(userLocation) > WalkConstants.mapMaxDistanceFromMapCenterMeters) {
            cameraPositionBuilder.target(userLatLng);
            didChangeCamera = true;
        }

        float currentZoom = map.getCameraPosition().zoom;
        if (Math.abs(WalkConstants.mapInitialZoom - currentZoom) > WalkConstants.mapZoomLevelDelta) {
            cameraPositionBuilder.zoom(WalkConstants.mapInitialZoom);
            didChangeCamera = true;
        }

        if (map.getCameraPosition().bearing > WalkConstants.mapMaxBearing
                && map.getCameraPosition().bearing < (360 - WalkConstants.mapMaxBearing)) {
            cameraPositionBuilder.bearing(0);
            didChangeCamera = true;
        }

        if (map.getCameraPosition().tilt > WalkConstants.mapMaxTilt) {
            cameraPositionBuilder.tilt(0);
            didChangeCamera = true;
        }

        if (!didChangeCamera) { return null; }

        CameraPosition newCameraPosition = cameraPositionBuilder.build();
        return CameraUpdateFactory.newCameraPosition(newCameraPosition);
    }
}
