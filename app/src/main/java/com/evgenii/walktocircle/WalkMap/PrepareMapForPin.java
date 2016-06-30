package com.evgenii.walktocircle.WalkMap;

import android.app.Activity;
import android.graphics.Point;
import android.location.Location;
import android.util.Log;

import com.evgenii.walktocircle.Utils.WalkLocation;
import com.evgenii.walktocircle.Utils.WalkView;
import com.evgenii.walktocircle.WalkConstants;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

public class PrepareMapForPin {

    private Activity mActivity;

    public PrepareMapForPin(Activity activity) {
        mActivity = activity;
    }

    public void prepare(Location userLocation, final Location pinLocation,
                        final GoogleMap map, final Point mapSizePixels,
                        final Point startButtonSizePixels, final Runnable callback) {

        animateCameraToUserLocation(userLocation, map, new Runnable() {
            @Override
            public void run() {
                animateCameraToShowPin(pinLocation, map, mapSizePixels, startButtonSizePixels, callback);
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

        // Center the map on user location if it is not visible on the map
        LatLngBounds bounds = map.getProjection().getVisibleRegion().latLngBounds;
        if (!bounds.contains(userLatLng) || didChangeCamera) {
            cameraPositionBuilder.target(userLatLng);
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
     * @param startButtonSizePixels size of the start button in pixels
     * @param callback called when camera update animation is finished
     */
    private void animateCameraToShowPin(Location pinLocation, GoogleMap map, Point mapSizePixels,
                                        Point startButtonSizePixels, final Runnable callback) {
        CameraUpdate update = getCameraUpdateToShowPin(pinLocation, map, mapSizePixels, startButtonSizePixels);

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
    private CameraUpdate getCameraUpdateToShowPin(Location pinLocation, GoogleMap map,
                                                  Point mapSizePixels, Point startButtonSizePixels) {

        Projection projection = map.getProjection();
        Point pinScreenLocation = projection.toScreenLocation(WalkLocation.latLngFromLocation(pinLocation));

        Point scroll = new Point(0, 0);
        float circleRadiusInPixels = (float)WalkLocation.fromMetersToMapPixels(pinLocation, map, WalkConstants.mCircleRadiusMeters) *
                WalkConstants.mapPaddingMultiplierFromCircleToMapEdgePixels;

        // Circle is beyond the left edge of the screen
        float beyondLeftEdge = pinScreenLocation.x - circleRadiusInPixels;
        if (beyondLeftEdge < 0) {
            scroll.x = (int) beyondLeftEdge;
        }

        // Circle is beyond the right edge of the screen
        float beyondRightEdge = pinScreenLocation.x - (mapSizePixels.x - circleRadiusInPixels);
        if (beyondRightEdge > 0) {
            scroll.x = (int) beyondRightEdge;
        }

        // Circle is beyond the top edge of the screen
        float beyondToEdge = pinScreenLocation.y - circleRadiusInPixels - WalkView.getStatusBarHeight(mActivity);
        if (beyondToEdge < 0) {
            scroll.y = (int) beyondToEdge;
        }

        // Circle is beyond the button edge of the screen
        float beyondBottomEdge = pinScreenLocation.y - (mapSizePixels.y - circleRadiusInPixels);
        if (beyondBottomEdge > 0) {
            scroll.y = (int) beyondBottomEdge;
        }

        scroll = correctScrollForStartButton(scroll, pinScreenLocation, mapSizePixels, startButtonSizePixels);

        if (scroll.x == 0 && scroll.y == 0) {
            // No camera update is necessary
            return null;
        }

        return CameraUpdateFactory.scrollBy(scroll.x, scroll.y);
    }

    /**
     * Correct the scroll of the camera in order to avoid the pin showing on top of the start button.
     */
    private Point correctScrollForStartButton(Point scroll, Point pinScreenLocation, Point mapSizePixels, Point startButtonSizePixels) {
        pinScreenLocation = new Point(pinScreenLocation.x + scroll.x, pinScreenLocation.y - scroll.y);

        float yCorrection = (mapSizePixels.y - pinScreenLocation.y) - (startButtonSizePixels.y * (float)1.3);


        if (yCorrection < 0) {

            float xCorrection = Math.abs(mapSizePixels.x / 2 - pinScreenLocation.x) - startButtonSizePixels.x;
            if (xCorrection < 0) {
                if ((mapSizePixels.x / 2) > pinScreenLocation.x) {
                    xCorrection *= -1;
                }

                scroll.x += xCorrection;
                scroll.y -= yCorrection;
            }
        }

        return scroll;
    }
}
