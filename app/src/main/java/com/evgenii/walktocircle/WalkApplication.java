package com.evgenii.walktocircle;

import android.app.Application;
import android.content.Context;

public class WalkApplication extends Application {

    private static Context context;

    public void onCreate() {
        super.onCreate();

        WalkApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return WalkApplication.context;
    }
}
