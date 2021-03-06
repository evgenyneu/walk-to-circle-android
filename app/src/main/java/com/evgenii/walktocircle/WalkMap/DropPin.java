package com.evgenii.walktocircle.walkMap;
import com.evgenii.walktocircle.MainActivity;
import com.evgenii.walktocircle.MainActivityState;
import com.evgenii.walktocircle.R;

import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.graphics.Point;
import android.location.Location;
import android.support.v4.content.ContextCompat;
import android.util.Property;
import android.view.animation.Interpolator;

import com.evgenii.walktocircle.libs.BounceInterpolator;
import com.evgenii.walktocircle.utils.WalkLocation;
import com.evgenii.walktocircle.WalkApplication;
import com.evgenii.walktocircle.WalkConstants;
import com.evgenii.walktocircle.utils.WalkString;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class DropPin {
    static private Marker mPreviousMarker;
    static private Circle mPreviousCircle;

    public void dropPin(Location pinLocation, GoogleMap map) {
        removePreviousMarker();

        LatLng latLng = WalkLocation.latLngFromLocation(pinLocation);
        addCircle(latLng, map);

        MarkerOptions markerOptions = new MarkerOptions()
                .position(latLng)
                .title(WalkString.fromResource(R.string.map_marker_marker_title));

        mPreviousMarker = map.addMarker(markerOptions);
        dropMarker(mPreviousMarker, map);
        playPinThrowAndDropSound();
    }

    private void playPinThrowAndDropSound() {
        WalkApplication.getSounds().playSound(R.raw.throw_and_drop_pin,
                WalkConstants.mapPinThrowAndDropVolume);
    }

    // Marker
    // ----------------------

    private void removePreviousMarker() {
        if (mPreviousMarker == null) { return; }
        mPreviousMarker.remove();
        mPreviousMarker = null;
    }

    private void dropMarker(final Marker marker, GoogleMap map) {
        if (MainActivityState.getInstance().isTutorialMode()) {
            // Show "Walk to circle" message above the pin in tutorial mode
            mPreviousMarker.showInfoWindow();
        }

        final LatLng finalPosition = new LatLng(marker.getPosition().latitude, marker.getPosition().longitude);

        Projection projection = map.getProjection();
        Point startPoint = projection.toScreenLocation(finalPosition);
        startPoint.y = 0;
        final LatLng startLatLng = projection.fromScreenLocation(startPoint);
        final Interpolator interpolator = new BounceInterpolator(
                WalkConstants.mapPinDropAnimationAmplitude, WalkConstants.mapPinDropAnimationFrequency);

        TypeEvaluator<LatLng> typeEvaluator = new TypeEvaluator<LatLng>() {
            @Override
            public LatLng evaluate(float fraction, LatLng startValue, LatLng endValue) {
                float t = interpolator.getInterpolation(fraction);
                double lng = t * finalPosition.longitude + (1 - t) * startLatLng.longitude;
                double lat = t * finalPosition.latitude + (1 - t) * startLatLng.latitude;
                return new LatLng(lat, lng);
            }
        };

        Property<Marker, LatLng> property = Property.of(Marker.class, LatLng.class, "position");
        ObjectAnimator animator = ObjectAnimator.ofObject(marker, property, typeEvaluator, finalPosition);
        animator.setDuration(WalkConstants.mapPinDropAnimationDuration);
        animator.start();
    }

    // Circle
    // ----------------------

    private void addCircle(LatLng latLng, GoogleMap map) {
        removePreviousCircle();

        CircleOptions circleOptions = new CircleOptions()
                .center(latLng)
                .fillColor(ContextCompat.getColor(WalkApplication.getAppContext(), R.color.mapPinFillColor))
                .strokeColor(ContextCompat.getColor(WalkApplication.getAppContext(), R.color.walkColorShade70))
                .strokeWidth(WalkConstants.mapPinStrokeWidth)
                .radius(WalkConstants.mCircleRadiusMeters);

        mPreviousCircle = map.addCircle(circleOptions);
    }

    private void removePreviousCircle() {
        if (mPreviousCircle == null) { return; }
        mPreviousCircle.remove();
        mPreviousCircle = null;
    }

}
