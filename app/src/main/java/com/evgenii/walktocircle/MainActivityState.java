package com.evgenii.walktocircle;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.gms.maps.model.LatLng;

// Saved state for the app
public class MainActivityState {
    // Contains the location of the currently dropped pin. If null, pin is not currently dropped.
    public LatLng currentPinLocation = null;

    // If true then we show Congratulations fragment
    public boolean showCongratulationsScreen = false;

    static final String CURRENT_LOCATION_LATITUDE = "currentLocationLatitude";
    static final String CURRENT_LOCATION_LONGITUDE = "currentLocationLongitude";
    static final String SHOW_CONTRATULATIONS_SCREEEN = "showCongratulationsScreen";

    private static MainActivityState mInstance;

    public static MainActivityState getInstance() {
        return mInstance;
    }

    public static void load() {
        mInstance = new MainActivityState();
        mInstance.loadState();
    }

    private void loadState() {
        SharedPreferences preferences = MainActivity.instance.getPreferences(0);

        double currentLocationLatitude = preferences.getFloat(CURRENT_LOCATION_LATITUDE, 0);
        double currentLocationLongitude =  preferences.getFloat(CURRENT_LOCATION_LONGITUDE, 0);

        if (currentLocationLatitude != 0 && currentLocationLongitude != 0) {
            currentPinLocation = new LatLng(currentLocationLatitude, currentLocationLongitude);
        }

        showCongratulationsScreen = preferences.getBoolean(SHOW_CONTRATULATIONS_SCREEEN, false);
    }

    public static void save() {
        if (mInstance != null) { mInstance.saveState(); }
    }

    private void saveState() {
        SharedPreferences preferences = MainActivity.instance.getPreferences(0);
        SharedPreferences.Editor editor = preferences.edit();

        if (currentPinLocation != null) {
            editor.putFloat(CURRENT_LOCATION_LATITUDE, (float)currentPinLocation.latitude);
            editor.putFloat(CURRENT_LOCATION_LONGITUDE, (float)currentPinLocation.longitude);
        }

        editor.putBoolean(SHOW_CONTRATULATIONS_SCREEEN, showCongratulationsScreen);

        editor.commit();
    }
}
