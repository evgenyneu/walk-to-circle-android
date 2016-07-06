package com.evgenii.walktocircle;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.evgenii.walktocircle.Utils.WalkSounds;

public class WalkApplication extends Application {

    private static Context context;
    private static WalkSounds walkSounds;
    private static WalkLocationService locationService = new WalkLocationService();
    private static Boolean mIsActive = false; // true if app is in the foreground.

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
        mIsActive = true;
        walkSounds.unSilence();
        Log.d("ii", "!!!!! App resumed");
    }

    public static void activityPaused() {
        mIsActive = false;
        walkSounds.silence();
        Log.d("ii", "!!!!! App paused");
    }

    /**
     *
     * @return true if application is in the foreground.
     */
    public static Boolean isActive() {
        return mIsActive;
    }

    public static WalkLocationService getLocationService() {
        return locationService;
    }
}
