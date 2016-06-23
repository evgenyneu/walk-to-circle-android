package com.evgenii.walktocircle;

import android.location.Location;
import android.support.test.runner.AndroidJUnit4;

import com.evgenii.walktocircle.Utils.WalkLocation;

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
}