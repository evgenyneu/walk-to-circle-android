package com.evgenii.walktocircle;

import android.location.Location;
import android.support.test.runner.AndroidJUnit4;

import com.evgenii.walktocircle.Utils.WalkGeo;
import com.evgenii.walktocircle.Utils.WalkLocation;
import com.google.android.gms.maps.model.LatLng;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.hamcrest.Matchers.*;


@RunWith(AndroidJUnit4.class)
public class WalkGeoUnitTest {
    @Test
    public void returnsRandomDegreeBetween0And360() {
        Double result = WalkGeo.randomBetween0and360Degrees();
        assertTrue(result >= 0 && result < 360);
    }

    @Test
    public void returnsRandomLocationAtDistanceRange() {
        for (int i = 0; i < 1000; i++)
        {
            Location fromLocation = WalkLocation.fromLatLng(-37.817728, 144.968108);

            Location result = WalkGeo.randomLocationAtDistanceRange(fromLocation, 1000.0, 5000.0);

            Float distance = fromLocation.distanceTo(result);

            assertThat("distance", distance, greaterThanOrEqualTo((float)1000.0));
            assertThat("distance", distance, lessThanOrEqualTo((float)5000.0));
        }
    }

    @Test
    public void returnsDestinationWithDistanceAndBearing() {
        Location fromLocation = WalkLocation.fromLatLng(-37.817728, 144.968108);
        Location result = WalkGeo.destination(fromLocation, 500.0, 65.0);

        assertEquals(-37.81582, result.getLatitude(), 0.0001);
        assertEquals(144.97326, result.getLongitude(), 0.0001);
    }

    @Test
    public void returnsNormalizedZoomLevel() {
        double result = WalkGeo.normalizedZoomLevelForLatitude(0, 15);
        assertEquals(15.0, result, 0.0001);

        // Latitude +- 37
        // ------------------

        result = WalkGeo.normalizedZoomLevelForLatitude(37, 15);
        assertEquals(14.7748, result, 0.0001);

        result = WalkGeo.normalizedZoomLevelForLatitude(-37, 15);
        assertEquals(14.7748, result, 0.0001);

        // Latitude +- 80
        // ------------------

        result = WalkGeo.normalizedZoomLevelForLatitude(80, 15);
        assertEquals(12.7244, result, 0.0001);

        result = WalkGeo.normalizedZoomLevelForLatitude(-80, 15);
        assertEquals(12.7244, result, 0.0001);
    }
}
