package com.evgenii.walktocircle;

import android.location.Location;
import android.support.test.runner.AndroidJUnit4;

import com.evgenii.walktocircle.utils.WalkLocation;
import com.google.android.gms.maps.model.LatLng;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class WalkLocationUnitTest {
    @Test
    public void returnsLocationWithLatitudeAndLongitude() {
        Location result = WalkLocation.fromLatLng(-37.817728, 144.968108);

        assertEquals(-37.817728, result.getLatitude(), 0.00000001);
        assertEquals(144.968108, result.getLongitude(), 0.00000001);
    }

    @Test
    public void returnsLatLngFromLocation() {
        Location location = WalkLocation.fromLatLng(-37.817728, 144.968108);

        LatLng result = WalkLocation.latLngFromLocation(location);

        assertEquals(-37.817728, result.latitude, 0.00001);
        assertEquals(144.968108, result.longitude, 0.00001);

    }
}