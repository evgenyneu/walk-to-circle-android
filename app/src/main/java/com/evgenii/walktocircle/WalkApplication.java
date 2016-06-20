package com.evgenii.walktocircle;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.evgenii.walktocircle.Utils.WalkSounds;

public class WalkApplication extends Application {

    private static Context context;
    private static WalkSounds walkSounds;

    public void onCreate() {
        super.onCreate();

//        Log.d("ii", "Screen density: " + getResources().getDisplayMetrics().density);
        WalkApplication.context = getApplicationContext();
        walkSounds = new WalkSounds(WalkApplication.context);
    }

    public static Context getAppContext() {
        return WalkApplication.context;
    }
    public static WalkSounds getSounds() {
        return walkSounds;
    }

}