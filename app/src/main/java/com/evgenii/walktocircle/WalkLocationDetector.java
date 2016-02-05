package com.evgenii.walktocircle;

import android.location.Location;

import com.evgenii.walktocircle.Utils.WalkLocation;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class WalkLocationDetector {
    private static ArrayList<WalkPosition> walkPositions = new ArrayList<WalkPosition>();

    private static WalkLocationDetector ourInstance = new WalkLocationDetector();

    public static WalkLocationDetector getInstance() {
        return ourInstance;
    }

    private WalkLocationDetector() {
    }

    public ArrayList<WalkPosition> getPositions() {
        if (walkPositions.size() == 0) {
            walkPositions.add(
                    new WalkPosition("Grey/Fitzroy intersection", new LatLng(-37.859686, 144.977517))
            );

            walkPositions.add(
                    new WalkPosition("House on Dalgety", new LatLng(-37.860610, 144.978924))
            );

            walkPositions.add(
                    new WalkPosition("House on Fitzroy", new LatLng(-37.858986, 144.979785))
            );
        }

        return walkPositions;
    }

    // Reach position detector
    // ----------------------

    void checkReachedPosition(Location location) {
        final WalkPosition position = reachedPosition(location, WalkConstants.mCircleRadiusMeters);

        clearReachedPositions(location,
                WalkConstants.mCircleRadiusMeters + WalkConstants.mReachPositionVariationMeters);

        if (position == null) { return; }
        if (position.reached) { return; }
        position.reached = true;
        //(new WalkNotification()).sendNotification("Reached circle", position.name);
    }

    WalkPosition reachedPosition(Location userLocation, double distance) {
        for(WalkPosition position: walkPositions) {
            Location circleLocation = WalkLocation.locationFromLatLng(position.latLng);

            if (circleLocation.distanceTo(userLocation) < distance) {
                return position;
            }
        }

        return null;
    }

    void clearReachedPositions(Location userLocation, double distance) {
        for(WalkPosition position: walkPositions) {
            Location circleLocation = WalkLocation.locationFromLatLng(position.latLng);

            if (circleLocation.distanceTo(userLocation) > distance) {
                position.reached = false;
            }
        }
    }

}
