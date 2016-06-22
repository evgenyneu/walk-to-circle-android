package com.evgenii.walktocircle.WalkMap;
import com.evgenii.walktocircle.R;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.Activity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.evgenii.walktocircle.Libs.BounceInterpolator;
import com.evgenii.walktocircle.WalkApplication;


public class StartButton {
    private Activity mActivity;
    private StartButtonCountdown mStartButtonCountdown;

    public StartButton(Activity activity) {
        mActivity = activity;
    }

    // Show the start button with animation and sound
    public void show() {
        final Animation animation = AnimationUtils.loadAnimation(mActivity, R.anim.bounce);

        // Use bounce animation with amplitude and frequency
        BounceInterpolator interpolator = new BounceInterpolator(0.2, 20);
        animation.setInterpolator(interpolator);

        final View button = getStartImage();
        button.setVisibility(View.VISIBLE);
        button.startAnimation(animation);

        WalkApplication.getSounds().playSound(R.raw.blop);
    }

    // Rotates the start button and shows the countdown animation
    public void startCountdown() {
        if (mStartButtonCountdown != null) { stopCountdown();}
        mStartButtonCountdown = new StartButtonCountdown(mActivity);

        rotateStartButton180DegreesOut();
        rotateRewindButton180DegreesIn();
        mStartButtonCountdown.startCountdown();
    }

    public void stopCountdown() {
        if (mStartButtonCountdown == null) { return; }
        mStartButtonCountdown.stopCountdown();
        mStartButtonCountdown = null;
    }

    private void rotateStartButton180DegreesOut() {
        final View button = getStartImage();

        AnimatorSet animatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(mActivity,
                R.animator.rotate_180_degress_out);

        BounceInterpolator interpolator = getRotate180BounceInterpolator();
        animatorSet.setInterpolator(interpolator);
        animatorSet.setTarget(button);
        animatorSet.start();
    }

    private void rotateRewindButton180DegreesIn() {
        final View button = getRewindGroup();

        AnimatorSet animatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(mActivity,
                R.animator.rotate_180_degrees_in);

        BounceInterpolator interpolator = getRotate180BounceInterpolator();
        animatorSet.setInterpolator(interpolator);
        animatorSet.setTarget(button);
        animatorSet.start();
    }

    private BounceInterpolator getRotate180BounceInterpolator() {
        return new BounceInterpolator(0.2, 7);
    }

    private View getStartImage() {
        return (View) mActivity.findViewById(R.id.startImage);
    }

    private View getRewindGroup() {
        return (View) mActivity.findViewById(R.id.countdownFrameLayout);
    }
}
