package com.evgenii.walktocircle;

public class WalkConstants {
    public static double mReachPositionVariationMeters = 50;
    public static float mapInitialZoom = 17;

    // Map pin and circle
    // -----------

    public static float mapPinStrokeWidth = 4;
    public static long mapPinDropAnimationDuration = 700;
    public static double mapPinDropAnimationAmplitude = 0.11;
    public static double mapPinDropAnimationFrequency = 4.6;
    public static double mCircleRadiusMeters = 90;
    public static double minCircleDistanceFromCurrentLocationMeters = 300;
    public static double maxCircleDistanceFromCurrentLocationMeters = 500;

    // Sounds
    // -----------

    public static double mapPinThrowAndDropVolume = 1.0;
    public static double mapStartButtonBlopVolume = 0.4;
    public static double mapStartButtonCountdownClickVolume = 0.6;
}
