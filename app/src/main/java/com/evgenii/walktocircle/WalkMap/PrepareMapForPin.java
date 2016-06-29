package com.evgenii.walktocircle.WalkMap;

import android.graphics.Point;
import android.location.Location;
import com.evgenii.walktocircle.Utils.WalkLocation;
import com.evgenii.walktocircle.WalkConstants;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

public class PrepareMapForPin {
    public void prepare(Location userLocation, final Location pinLocation, final GoogleMap map,
                        final Point mapSizePixels, final Runnable callback) {

        animateCameraToUserLocation(userLocation, map, new Runnable() {
            @Override
            public void run() {
                animateCameraToShowPin(pinLocation, map, mapSizePixels, callback);
            }
        });
    }

    /**
     * Animates the camera to current user's location with default zoom level.
     * If user's location is already shown on the map with proper zoom the camera animation
     * is not performed and the callback is called right away.
     * @param userLocation location which is used to animate the camera to
     * @param map the map object
     * @param callback called after camera animation is complete
     */
    public void animateCameraToUserLocation(Location userLocation, GoogleMap map, final Runnable callback) {
        CameraUpdate update = getUserCenteredCameraUpdate(userLocation, map);

        if (update == null) {
            // No camera update is necessary
            callback.run();
        } else {
            map.animateCamera(update, WalkConstants.mapPositionAnimationDurationMilliseconds, new GoogleMap.CancelableCallback() {
                @Override
                public void onFinish() {
                    callback.run();
                }

                @Override
                public void onCancel() { }
            });
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

        // Center the map on user location if it is not visible on the map
        LatLngBounds bounds = map.getProjection().getVisibleRegion().latLngBounds;
        if (!bounds.contains(userLatLng)) {
            cameraPositionBuilder.target(userLatLng);
            didChangeCamera = true;
        }

        // Zoom
        float currentZoom = map.getCameraPosition().zoom;
        if (Math.abs(WalkConstants.mapInitialZoom - currentZoom) > WalkConstants.mapZoomLevelDelta) {
            cameraPositionBuilder.zoom(WalkConstants.mapInitialZoom);
            didChangeCamera = true;
        }

        // Rotate the map
        if (map.getCameraPosition().bearing > WalkConstants.mapMaxBearing
                && map.getCameraPosition().bearing < (360 - WalkConstants.mapMaxBearing)) {
            cameraPositionBuilder.bearing(0);
            didChangeCamera = true;
        }

        // Remove map camera tilt
        if (map.getCameraPosition().tilt > WalkConstants.mapMaxTilt) {
            cameraPositionBuilder.tilt(0);
            didChangeCamera = true;
        }

        if (!didChangeCamera) { return null; }

        CameraPosition newCameraPosition = cameraPositionBuilder.build();
        return CameraUpdateFactory.newCameraPosition(newCameraPosition);
    }

    /**
     * Move the camera to the position such that the pin and its circle are fully visible.
     * @param pinLocation location of the pin
     * @param map map object
     * @param mapSizePixels size of the map in pixels
     * @param callback called when camera update animation is finished
     */
    private void animateCameraToShowPin(Location pinLocation, GoogleMap map, Point mapSizePixels, final Runnable callback) {
        CameraUpdate update = getCameraUpdateToShowPin(pinLocation, map, mapSizePixels);

        if (update == null) {
            // No camera update is necessary
            callback.run();
        } else {
            map.animateCamera(update, WalkConstants.mapPositionAnimationDurationMilliseconds, new GoogleMap.CancelableCallback() {
                @Override
                public void onFinish() {
                    callback.run();
                }

                @Override
                public void onCancel() {
                }
            });
        }
    }

    /**
     * Returns the update for the camera needed to show the pin.
     * Returns null if no camera update is necessary to show the pin.
     * @param pinLocation the location of the pin
     * @param map map object
     * @param mapSizePixels the size of the map in pixels
     * @return Returns the update for the camera needed to show the pin. Returns null if no camera update is necessary to show the pin.
     */
    private CameraUpdate getCameraUpdateToShowPin(Location pinLocation, GoogleMap map, Point mapSizePixels) {
        Projection projection = map.getProjection();
        Point startPoint = projection.toScreenLocation(WalkLocation.latLngFromLocation(pinLocation));

        float scrollX = 0;
        float circleSizeInPixels = (float)WalkLocation.fromMetersToMapPixels(map, WalkConstants.mCircleRadiusMeters) *
                WalkConstants.mapPaddingMultiplierFromCircleToMapEdgePixels;
        float scrollY = 0;

        if (startPoint.x < circleSizeInPixels) {
            scrollX = startPoint.x - circleSizeInPixels;
        }

        if (startPoint.x > (mapSizePixels.x - circleSizeInPixels)) {
            scrollX = startPoint.x - (mapSizePixels.x - circleSizeInPixels);
        }

        if (startPoint.y < circleSizeInPixels) {
            scrollY = startPoint.y - circleSizeInPixels;
        }

        if (startPoint.y > (mapSizePixels.y - circleSizeInPixels)) {
            scrollY = startPoint.y - (mapSizePixels.y - circleSizeInPixels);
        }

        if (startPoint.x == 0 && startPoint.y == 0) {
            // No camera update is necessary
            return null;
        }

        return CameraUpdateFactory.scrollBy(scrollX, scrollY);
    }
}
