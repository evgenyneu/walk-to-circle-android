package com.evgenii.walktocircle.WalkMap;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import com.evgenii.walktocircle.Libs.BounceInterpolator;

import com.evgenii.walktocircle.R;

import java.util.Timer;
import java.util.TimerTask;


public class StartButton {
    Activity mActivity;
    Timer mShowTimer;

    public StartButton(Activity activity) {
        mActivity = activity;
    }

    public void destroy() {
        cancelShowTimer();
        Log.d("ii", "!!!!!!!cancelShowTimer");
    }

    // Show the start button with animation and sound
    public void show() {
        cancelShowTimer();
        mShowTimer = new Timer();
        mShowTimer.schedule(new ShowTimerTask(), 3000);
    }

    private class ShowTimerTask extends TimerTask{
        @Override
        public void run() {
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showNow();
                }
            });
        }
    }

    private void cancelShowTimer() {
        if (mShowTimer != null) {
            mShowTimer.cancel();
            mShowTimer = null;
        }
    }

    private void showNow() {
        final Animation myAnim = AnimationUtils.loadAnimation(mActivity, R.anim.bounce);

        // Use bounce animation with amplitude and frequency
        BounceInterpolator interpolator = new BounceInterpolator(0.2, 20);
        myAnim.setInterpolator(interpolator);

        Button button = (Button)mActivity.findViewById(R.id.startActivityButton);
        button.setVisibility(View.VISIBLE);
        button.startAnimation(myAnim);
    }
}
