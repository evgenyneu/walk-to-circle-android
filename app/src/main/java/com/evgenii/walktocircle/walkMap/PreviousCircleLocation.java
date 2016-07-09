package com.evgenii.walktocircle.walkMap;

import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.graphics.Point;
import android.support.v4.content.ContextCompat;
import android.util.Property;
import android.view.animation.Interpolator;

import com.evgenii.walktocircle.MainActivityState;
import com.evgenii.walktocircle.R;
import com.evgenii.walktocircle.WalkApplication;
import com.evgenii.walktocircle.WalkConstants;
import com.evgenii.walktocircle.libs.BounceInterpolator;
import com.evgenii.walktocircle.utils.WalkString;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class PreviousCircleLocation {
    static private Circle mPreviousCircle;
    static private Marker mPreviousMarker;

    /**
     * Shows a circle and a marker at the location of the previously attempted (or reached) circle.
     * @param map
     */
    public static void showLastCircleLocation(GoogleMap map) {
        LatLng location = MainActivityState.getInstance().getPreviouslyReachedCircleLocation();
        if (location == null) { return; }
        addCircle(location, map);
        dropMarker(location, map);
    }

    public static void remove() {
        removePreviousMarker();
        removePreviousCircle();
        MainActivityState.savePreviouslyReachedCircleLocation(null);
    }

    // Marker
    // ----------------------

    private static void dropMarker(LatLng latLng, GoogleMap map) {
        removePreviousMarker();
        MarkerOptions markerOptions = new MarkerOptions()
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                .alpha((float)0.4)
                .position(latLng)
                .title(WalkString.fromResource(R.string.map_previous_circle_marker_title));

        mPreviousMarker = map.addMarker(markerOptions);
    }

    private static void removePreviousMarker() {
        if (mPreviousMarker == null) { return; }
        mPreviousMarker.remove();
        mPreviousMarker = null;
    }

    // Circle
    // ----------------------

    private static void addCircle(LatLng latLng, GoogleMap map) {
        removePreviousCircle();

        CircleOptions circleOptions = new CircleOptions()
                .center(latLng)
                .fillColor(ContextCompat.getColor(WalkApplication.getAppContext(), R.color.mapLastCircleFillColor))
                .strokeColor(ContextCompat.getColor(WalkApplication.getAppContext(), R.color.mapLastCircleStrokeColor))
                .strokeWidth(WalkConstants.mapPinStrokeWidth)
                .radius(WalkConstants.mCircleRadiusMeters);

        mPreviousCircle = map.addCircle(circleOptions);
    }

    private static void removePreviousCircle() {
        if (mPreviousCircle == null) { return; }
        mPreviousCircle.remove();
        mPreviousCircle = null;
    }
}
