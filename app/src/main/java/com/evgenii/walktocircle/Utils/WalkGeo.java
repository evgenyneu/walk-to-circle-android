package com.evgenii.walktocircle.Utils;

import android.location.Location;

public class WalkGeo {

    /**
     * @return random degree in the interval [0, 360)
     */
    public static Double randomBetween0and360Degrees() {
        Double min = 0.0;
        Double max = 360.0;

        return WalkRandom.randomDoubleBetween(min, max);
    }


    /**
     * Returns random coordinate
     * which is between minDistanceMeters and maxDistanceMeters from the start point.
     * @param fromLocation the start location
     * @param minDistanceMeters the minimum distance in meters from the location
     * @param maxDistanceMeters the maxumum distance in meters from the location
     * @return random coordinate which is between minDistanceMeters and maxDistanceMeters from the start point.
     */
    public static Location randomLocationAtDistanceRange(Location fromLocation,
                                                         Double minDistanceMeters,
                                                         Double maxDistanceMeters) {

        Double minDistanceMetersCorrected = minDistanceMeters;
        Double maxDistanceMetersCorrected = maxDistanceMeters;

        // Correct the distance by 0.25%.
        // That is amount of inaccuracy in distance calculation.
        // We want to make sure the returned coordinate is always inside min/max range.
        if (minDistanceMetersCorrected != maxDistanceMetersCorrected) {
            minDistanceMetersCorrected += minDistanceMetersCorrected * 0.0025;
            maxDistanceMetersCorrected -= maxDistanceMetersCorrected * 0.0025;
        }

        if (minDistanceMetersCorrected > maxDistanceMetersCorrected) {
            minDistanceMetersCorrected = maxDistanceMetersCorrected;
        }

        Double distanceMeters = WalkRandom.randomDoubleBetween(minDistanceMetersCorrected, maxDistanceMetersCorrected);
        Double bearingDegrees = WalkGeo.randomBetween0and360Degrees();

        return destination(fromLocation, distanceMeters, bearingDegrees);
    }

    /**
     * Calculates destination point given distance and bearing from start point.
     *
     * Formula:
     *
     * φ2 = asin( sin φ1 ⋅ cos δ + cos φ1 ⋅ sin δ ⋅ cos θ )
     * λ2 = λ1 + atan2( sin θ ⋅ sin δ ⋅ cos φ1, cos δ − sin φ1 ⋅ sin φ2 )
     *
     * Where:
     *  φ is latitude,
     *  λ is longitude,
     *  θ is the bearing (in radians, clockwise from north),
     *  δ is the angular distance (in radians) d/R;
     *  d being the distance travelled,
     *  R the earth’s radius
     *
     * Source: http://www.movable-type.co.uk/scripts/latlong.html
     *
     * @param fromLocation start location
     * @param distanceMeters distance from location in meters
     * @param bearingDegrees bearing from location in degress
     * @return new location
     */
    public static Location destination(Location fromLocation, Double distanceMeters, Double bearingDegrees) {
        Double φ1 = Math.toRadians(fromLocation.getLatitude());
        Double λ1 = Math.toRadians(fromLocation.getLongitude());

        Double θ = Math.toRadians(bearingDegrees);
        Double R = 6_371_000.0; // Earth mean radius
        Double δ = distanceMeters / R;

        Double φ2 = Math.asin( Math.sin(φ1) * Math.cos(δ) + Math.cos(φ1) * Math.sin(δ) * Math.cos(θ) );
        Double λ2 = λ1 + Math.atan2( Math.sin(θ) * Math.sin(δ) * Math.cos(φ1), Math.cos(δ) - Math.sin(φ1) * Math.sin(φ2) );

        return WalkLocation.fromLatLng(Math.toDegrees(φ2), Math.toDegrees(λ2));
    }
}
