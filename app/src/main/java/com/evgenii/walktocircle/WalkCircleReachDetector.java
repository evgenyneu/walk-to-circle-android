package com.evgenii.walktocircle;

import android.location.Location;

import com.evgenii.walktocircle.FragmentManager.WalkFragmentType;
import com.evgenii.walktocircle.Utils.WalkLocation;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class WalkCircleReachDetector {
    private static ArrayList<WalkPosition> walkPositions = new ArrayList<WalkPosition>();

    private static WalkCircleReachDetector ourInstance = new WalkCircleReachDetector();

    public static WalkCircleReachDetector getInstance() {
        return ourInstance;
    }

    private WalkCircleReachDetector() { } // Private constructor

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

    // Checks if we reached the circle
    // ----------------------

    void checkReachedPosition(Location location) {
        LatLng circleLatLng = MainActivityState.getInstance().getCurrentCircleLocation();
        if (circleLatLng == null) { return; }
        Location circleLocation = WalkLocation.locationFromLatLng(circleLatLng);

        if (reachedTheCircle(location, circleLocation, WalkConstants.mCircleRadiusMeters)) {
            circleReached();
        }
    }

    void circleReached() {
        if (MainActivityState.getInstance().getCurrentCircleLocation() == null) { return; }
        MainActivityState.savePreviouslyReachedCircleLocation(MainActivityState.getInstance().getCurrentCircleLocation());
        MainActivityState.saveCurrentCircleLocation(null);
        WalkApplication.getLocationService().stopLocationUpdatesIfNeeded();
        MainActivityState.saveShowCongratulationsScreen(true);
        (new WalkNotification()).sendNotification("You reached your circle. Well done!");
        WalkFragmentType.showWithAnimation();
    }

    /**
     * @return returns true if the user reached the circle
     */
    boolean reachedTheCircle(Location userLocation, Location circleLocation, double circleRadiusMeters) {
        return circleLocation.distanceTo(userLocation) < circleRadiusMeters;
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
