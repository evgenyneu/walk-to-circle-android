package com.evgenii.walktocircle;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.Log;

public class WalkLocationPermissions {
    private static final String[] INITIAL_PERMS={
            Manifest.permission.ACCESS_FINE_LOCATION,
    };

    private static final int INITIAL_REQUEST=1337;
    private static final int LOCATION_REQUEST =INITIAL_REQUEST+1;

    private static WalkLocationPermissions ourInstance = new WalkLocationPermissions();

    public static WalkLocationPermissions getInstance() {
        return ourInstance;
    }

    private WalkLocationPermissions() {
    }

    public Runnable didGrantCallback;
    public Runnable didDenyCallback;


    /**
     *
     * @return true if the app should show UI with rationale for requesting a location permission.
     * False, if one of the following cases
     * 1) Location permission has not been requested by the app yet.
     * 2) Location permission has been requested and the user denied it with "do not ask again" option.
     * 3) The app already has location permission.
     * 4) The Android cation older than Marshmallow, which means the request permission has
     * been granted when the app was installed.
     */
    public boolean shouldShowRequestPermissionRationale() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!hasLocationPermission()) {
                return MainActivity.instance.shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION);
            }
        }

        return false;
    }

    public void requestLocationPermissionIfNotGranted(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!hasLocationPermission()) {
                activity.requestPermissions(INITIAL_PERMS, LOCATION_REQUEST);
            }
        }
    }

    public boolean hasLocationPermission() {
        return ContextCompat.checkSelfPermission(WalkApplication.getAppContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch(requestCode) {
            case LOCATION_REQUEST:
                if (grantResults.length == 0) { return; }
                int grantResult = grantResults[0];

                if (grantResult == PackageManager.PERMISSION_GRANTED) {
                    if (didGrantCallback != null) {
                        didGrantCallback.run();
                    }
                } else if (grantResult == PackageManager.PERMISSION_DENIED) {
                    if (didDenyCallback != null) {
                        didDenyCallback.run();
                    }
                }

                break;
        }
    }
}
