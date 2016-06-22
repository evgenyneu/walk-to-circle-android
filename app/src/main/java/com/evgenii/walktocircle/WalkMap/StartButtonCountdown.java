package com.evgenii.walktocircle.WalkMap;

import android.app.Activity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.evgenii.walktocircle.R;

public class StartButtonCountdown {
    Activity mActivity;

    public StartButtonCountdown(Activity activity) {
        mActivity = activity;
    }

    void startCountdown() {
        rotateRewindArrows();
    }

    private void rotateRewindArrows() {
        final Animation animation = AnimationUtils.loadAnimation(mActivity, R.anim.rotate_continuously);
        final View view = getRewindArrows();
        view.clearAnimation();
        view.startAnimation(animation);
    }

    private View getRewindArrows() {
        return (View) mActivity.findViewById(R.id.rewindArrowsImageView);
    }
}
