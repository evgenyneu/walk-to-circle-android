package com.evgenii.walktocircle;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.evgenii.walktocircle.Utils.WalkSounds;

public class WalkApplication extends Application {

    private static Context context;
    private static WalkSounds walkSounds;
    private static WalkLocationService locationService = new WalkLocationService();

    public void onCreate() {
        super.onCreate();

        WalkApplication.context = getApplicationContext();
        walkSounds = new WalkSounds(WalkApplication.context);
    }

    public static Context getAppContext() {
        return WalkApplication.context;
    }

    public static WalkSounds getSounds() {
        return walkSounds;
    }

    public static void activityResumed() {
        walkSounds.unSilence();
    }

    public static void activityPaused() {
        walkSounds.silence();
    }

    public static WalkLocationService getLocationService() {
        return locationService;
    }
}
