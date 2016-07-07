package com.evgenii.walktocircle;

public class WalkConstants {
    public static double mReachPositionVariationMeters = 50;

    // The height of the status bar in pixels. Used to avoid the circle from overlapping with the status bar.
    public static float statusBarHeightPixels = 50;

    // Map position and zoom
    // -----------

    // The duration of the animation that centers the map on current user location
    // before dropping the pin, in milliseconds.
    public static int mapPositionAnimationDurationMilliseconds = 400;

    // The default zoom level of the map. The map is zoomed it this level before the pin is dropped.
    public static double mapInitialZoom = 16.1;

    // The maximum allowed difference between the current map zoom level and the "mapInitialZoom"
    // setting. If the difference is greater the map is zoomed at the "mapInitialZoom" level.
    // Units: the GoogleMap zoom level units.
    public static double mapZoomLevelDelta = 1;

    // The maximum allowed bearing from zero degrees. If bearing is greater the map's bearing
    // is set back to zero.
    public static float mapMaxBearing = 20;

    // The maximum allowed tilt of the map in degrees. If the map tilt is greater
    // the tilt is restored to zero.
    public static float mapMaxTilt = 10;

    // The multiplier to get the padding from the edge of the circle to the edge of the map on screen.
    // Example, if it is 1.3 and the radius of the circle is 100 pixels, the distance from circle center
    // to the map screen will be 130 pixels.
    public static float mapPaddingMultiplierFromCircleToMapEdgePixels = (float)1.3;


    // Map pin and circle
    // -----------

    public static float mapPinStrokeWidth = 4;
    public static long mapPinDropAnimationDuration = 700;
    public static double mapPinDropAnimationAmplitude = 0.11;
    public static double mapPinDropAnimationFrequency = 4.6;
    public static double mCircleRadiusMeters = 90;

    // Minimum distance of a pin from the current location, in meters
    public static double minCircleDistanceFromCurrentLocationMeters = 300;

    // Maximum distance of a pin from the current location, in meters
    public static double maxCircleDistanceFromCurrentLocationMeters = 500;

    // Sounds
    // -----------

    public static double mapPinThrowAndDropVolume = 1.0;
    public static double mapStartButtonBlopVolume = 0.4;
    public static double mapShowWalkScreenVolume = 0.2;
    public static double mapStartButtonCountdownClickVolume = 0.4;
}
