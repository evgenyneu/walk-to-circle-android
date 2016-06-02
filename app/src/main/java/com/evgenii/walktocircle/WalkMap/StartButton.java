package com.evgenii.walktocircle.WalkMap;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import com.evgenii.walktocircle.Libs.BounceInterpolator;

import com.evgenii.walktocircle.R;
import com.evgenii.walktocircle.WalkApplication;


public class StartButton {
    Activity mActivity;

    public StartButton(Activity activity) {
        mActivity = activity;
    }

    // Show the start button with animation and sound
    public void show() {
        final Animation myAnim = AnimationUtils.loadAnimation(mActivity, R.anim.bounce);

        // Use bounce animation with amplitude and frequency
        BounceInterpolator interpolator = new BounceInterpolator(0.2, 20);
        myAnim.setInterpolator(interpolator);

        Button button = (Button)mActivity.findViewById(R.id.startActivityButton);
        button.setVisibility(View.VISIBLE);
        button.startAnimation(myAnim);

        WalkApplication.getSounds().playSound(R.raw.blop);
    }
}
