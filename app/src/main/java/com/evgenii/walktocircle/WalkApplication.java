package com.evgenii.walktocircle;

import android.app.Application;
import android.content.Context;
import android.util.Log;

public class WalkApplication extends Application {

    private static Context context;

    public void onCreate() {
        super.onCreate();

//        Log.d("ii", "Screen density: " + getResources().getDisplayMetrics().density);
        WalkApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return WalkApplication.context;
    }
}
