package com.evgenii.walktocircle.WalkMap;

import android.animation.TypeEvaluator;
import android.graphics.Point;
import android.location.Location;
import android.os.Handler;
import android.os.SystemClock;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.evgenii.walktocircle.Libs.BounceInterpolator;
import com.evgenii.walktocircle.Utils.WalkGeo;
import com.evgenii.walktocircle.Utils.WalkLocation;
import com.evgenii.walktocircle.WalkConstants;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class DropPin {
    static private Marker mPreviousMarker;

    public void dropPin(Location currentLocation, GoogleMap map) {
        removePreviousMarker();

        Location pinLocation = WalkGeo.randomLocationAtDistanceRange(currentLocation,
                WalkConstants.minCircleDistanceFromCurrentLocationMeters,
                WalkConstants.maxCircleDistanceFromCurrentLocationMeters);

        LatLng latLng = WalkLocation.latLngFromLocation(pinLocation);
        MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("Walk to Circle");
        mPreviousMarker = map.addMarker(markerOptions);
        animateMarker(mPreviousMarker, map);
    }

    private void removePreviousMarker() {
        if (mPreviousMarker == null) { return; }
        mPreviousMarker.remove();
    }

    private void animateMarker(final Marker marker, GoogleMap map) {
        final LatLng target = new LatLng(marker.getPosition().latitude, marker.getPosition().longitude);
        Projection projection = map.getProjection();
        Point startPoint = projection.toScreenLocation(target);
        startPoint.y = 0;
        final LatLng startLatLng = projection.fromScreenLocation(startPoint);

        final long duration = 400;
        final long startTime = SystemClock.uptimeMillis();
        final Interpolator interpolator = new BounceInterpolator(0.1, 2);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - startTime;
                float t = interpolator.getInterpolation((float) elapsed / duration);
                double lng = t * target.longitude + (1 - t) * startLatLng.longitude;
                double lat = t * target.latitude + (1 - t) * startLatLng.latitude;
                marker.setPosition(new LatLng(lat, lng));
                if (elapsed < duration) {
                    // Post again 10ms later.
                    handler.postDelayed(this, 5);
                } else {
                    marker.setPosition(target);
                }
            }
        });
    }

    private void animateMarkerTwo(final Marker marker, GoogleMap map) {
//        final Interpolator interpolator = new AccelerateDecelerateInterpolator();
//
//        TypeEvaluator<LatLng> typeEvaluator = new TypeEvaluator<LatLng>() {
//            @Override
//            public LatLng evaluate(float fraction, LatLng startValue, LatLng endValue) {
//                return latLngInterpolator.interpolate(fraction, startValue, endValue);
//            }
//        };
//        Property<Marker, LatLng> property = Property.of(Marker.class, LatLng.class, "position");
//        ObjectAnimator animator = ObjectAnimator.ofObject(marker, property, typeEvaluator, finalPosition);
//        animator.setDuration(3000);
//        animator.start();
    }
}
