package com.evgenii.walktocircle;

import java.util.Timer;
import java.util.TimerTask;

public class WalkTestReachCircle {

    private Timer mTimer;

    public boolean testReached = false;

    private static WalkTestReachCircle ourInstance = new WalkTestReachCircle();

    public static WalkTestReachCircle getInstance() {
        return ourInstance;
    }

    private WalkTestReachCircle() { } // Private constructor

    public void testCircleReachedAfterSeconds(int seconds) {
        cancelTimer();

        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                testReached = true;
            }

        }, seconds * 1000);
    }

    private void cancelTimer() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }
}
