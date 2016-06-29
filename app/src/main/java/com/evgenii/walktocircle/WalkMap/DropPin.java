package com.evgenii.walktocircle.WalkMap;
import com.evgenii.walktocircle.R;

import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Point;
import android.location.Location;
import android.support.v4.content.ContextCompat;
import android.util.Property;
import android.util.Size;
import android.view.animation.Interpolator;

import com.evgenii.walktocircle.Libs.BounceInterpolator;
import com.evgenii.walktocircle.Utils.WalkGeo;
import com.evgenii.walktocircle.Utils.WalkLocation;
import com.evgenii.walktocircle.WalkApplication;
import com.evgenii.walktocircle.WalkConstants;
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
    private Activity mActivity;


    public DropPin(Activity activity) {
        mActivity = activity;
    }

    public void dropPin(Location pinLocation, GoogleMap map) {
        removePreviousMarker();

        LatLng latLng = WalkLocation.latLngFromLocation(pinLocation);

        MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("Walk to Circle");
        mPreviousMarker = map.addMarker(markerOptions);
        dropMarker(mPreviousMarker, map);
        addCircle(latLng, map);
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
                .fillColor(ContextCompat.getColor(mActivity, R.color.mapPinFillColor))
                .strokeColor(ContextCompat.getColor(mActivity, R.color.mapPinStrokeColor))
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
