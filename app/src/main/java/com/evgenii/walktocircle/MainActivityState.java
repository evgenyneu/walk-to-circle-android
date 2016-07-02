package com.evgenii.walktocircle;

import android.os.Bundle;

import com.google.android.gms.maps.model.LatLng;

// Saved state for the main activity
public class MainActivityState {
    // Contains the location of the currently dropped pin. If null, pin is not currently dropped.
    public LatLng currentLocation = null;

    // If true then we show Congratulations fragment
    public boolean showCongratulationsScreen = false;

    static final String CURRENT_LOCATION_LATITUDE = "currentLocationLatitude";
    static final String CURRENT_LOCATION_LONGITUDE = "currentLocationLongitude";
    static final String SHOW_CONTRATULATIONS_SCREEEN = "showCongratulationsScreen";

    private static MainActivityState mInstance;

    public MainActivityState getInstance() {
        return mInstance;
    }

    public static void load(Bundle savedInstanceState) {
        mInstance = new MainActivityState();
        mInstance.load(Bundle savedInstanceState);
    }

    private void loadState(Bundle savedInstanceState) {
        double currentLocationLatitude = savedInstanceState.getDouble(CURRENT_LOCATION_LATITUDE);
        double currentLocationLongitude = savedInstanceState.getDouble(CURRENT_LOCATION_LONGITUDE);

        if (currentLocationLatitude != 0 && currentLocationLongitude != 0) {
            currentLocation = new LatLng(currentLocationLatitude, currentLocationLongitude);
        }

        showCongratulationsScreen = savedInstanceState.getBoolean(SHOW_CONTRATULATIONS_SCREEEN);
    }

    public static void save(Bundle bundle) {
        if (mInstance != null) { mInstance.saveState(bundle); }
    }

    private void saveState(Bundle bundle) {
        if (currentLocation != null) {
            bundle.putDouble(CURRENT_LOCATION_LATITUDE, currentLocation.latitude);
            bundle.putDouble(CURRENT_LOCATION_LONGITUDE, currentLocation.longitude);
        }

        bundle.putBoolean(SHOW_CONTRATULATIONS_SCREEEN, showCongratulationsScreen);
    }
}
