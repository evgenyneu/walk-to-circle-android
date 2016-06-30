package com.evgenii.walktocircle.WalkMap;
import com.evgenii.walktocircle.R;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.Activity;
import android.graphics.Point;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.evgenii.walktocircle.Libs.BounceInterpolator;
import com.evgenii.walktocircle.WalkApplication;
import com.evgenii.walktocircle.WalkConstants;


public class StartButton {
    private Activity mActivity;
    private StartButtonCountdown mStartButtonCountdown;
    private boolean isStartButtonVisible = true;

    // Will be called when the countdown timer reaches zero.
    private Runnable mDidFinishCountdown;

    public StartButton(Activity activity, Runnable didFinishCountdown) {
        mActivity = activity;
        mDidFinishCountdown = didFinishCountdown;
    }

    // Show the start button with animation and sound
    public void show() {
        final View button = getStartImage();
        if (button.getVisibility() == View.VISIBLE) { return; } // Start button already visible
        button.setVisibility(View.VISIBLE);

        final Animation animation = AnimationUtils.loadAnimation(mActivity, R.anim.bounce);

        // Use bounce animation with amplitude and frequency
        BounceInterpolator interpolator = new BounceInterpolator(0.2, 20);
        animation.setInterpolator(interpolator);
        button.startAnimation(animation);

        WalkApplication.getSounds().playSound(R.raw.blop, WalkConstants.mapStartButtonBlopVolume);
    }

    // Rotates the start button and shows the countdown animation
    public void rotateAndShowInitialNumber() {
        stopCountdown();
        mStartButtonCountdown = new StartButtonCountdown(mActivity, mDidFinishCountdown);

        if (isStartButtonVisible) {
            // Showing the start button, flip it around to show the countdown.
            rotateStartButton180DegreesOut();
            rotateRewindButton180DegreesIn();
        } else {
            // Showing the countdown, rotate the button 360 degrees
            rotateRewindButton360Degrees();
        }

        mStartButtonCountdown.rotateAndShowInitialNumber();
        isStartButtonVisible = false;
    }

    public void startCountdown() {
        mStartButtonCountdown.startCountdownTimer();
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

    private void rotateRewindButton360Degrees() {
        final View button = getRewindGroup();

        AnimatorSet animatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(mActivity,
                R.animator.rotate_360_degrees);

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

    /**
     * @return the size in pixels of the start button
     */
    public Point getSizePixels() {
        View image = getStartImage();
        return new Point(image.getWidth(), image.getHeight());
    }
}
