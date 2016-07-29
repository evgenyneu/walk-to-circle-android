package com.evgenii.walktocircle;

import android.location.Location;

import com.evgenii.walktocircle.fragmentManager.WalkFragmentType;
import com.evgenii.walktocircle.utils.WalkLocation;
import com.evgenii.walktocircle.walkCongrats.WalkCirclesReachedToday;
import com.evgenii.walktocircle.walkCongrats.WalkCongratsPhrase;
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

    /**
     * A pretty important method. It is called when user reaches the circle.
     */
    void circleReached() {
        if (MainActivityState.getInstance().getCurrentCircleLocation() == null) { return; }
        MainActivityState.saveCurrentCircleLocation(null);
        WalkApplication.getLocationService().stopLocationUpdatesIfNeeded();
        WalkCongratsPhrase.mCurrentPhrase = null; // Forget the previous "Congrats!" phrase and show a new one
        MainActivityState.saveShowCongratulationsScreen(true);
        MainActivityState.saveIsTutorialMode(false); // Circle reached, we are no longer in tutorial mode.
        MainActivityState.saveCurrentQuote(null);
        WalkCirclesReachedToday.increment(); // Increase the number of circles reached today.
        (new WalkNotification()).showNotification("You reached your circle.", "Well done!");
        WalkFragmentType.showWithAnimation();
    }

    /**
     * @return returns true if the user reached the circle
     */
    boolean reachedTheCircle(Location userLocation, Location circleLocation, double circleRadiusMeters) {
        // User for manual testing
        if (WalkTestReachCircle.getInstance().testReached) {
            WalkTestReachCircle.getInstance().testReached = false;
            return true;
        }

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
