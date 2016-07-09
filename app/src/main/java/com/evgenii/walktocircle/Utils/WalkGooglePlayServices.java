package com.evgenii.walktocircle.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

public class WalkGooglePlayServices {
    public static int getGooglePlayServicesVersion(Context context) {
        try {
            return context.getPackageManager().getPackageInfo("com.google.android.gms", 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            return 0;
        }
    }

    public static void logGooglePlayServicesVersion(Context context) {
        int version = getGooglePlayServicesVersion(context);
        if (version > 0) {
            Log.d("ii", "Google Play Services version: " + version);
        } else {
            Log.d("ii", "Google Play Services NOT FOUND.");
        }
    }
}
