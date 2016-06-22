package com.evgenii.walktocircle.WalkMap;
import com.evgenii.walktocircle.R;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.Activity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.evgenii.walktocircle.Libs.BounceInterpolator;
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

        final View button = getStartImage();
        button.setVisibility(View.VISIBLE);
        button.startAnimation(myAnim);

        WalkApplication.getSounds().playSound(R.raw.blop);
    }

    public void rotate180Degrees() {
        rotateStartButton180DegreesOut();
        rotateRewindButton180DegreesIn();
    }

    void rotateStartButton180DegreesOut() {
        final View button = getStartImage();

        AnimatorSet animatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(mActivity,
                R.animator.rotate_180_degress_out);

        BounceInterpolator interpolator = getRotate180BounceInterpolator();
        animatorSet.setInterpolator(interpolator);
        animatorSet.setTarget(button);
        animatorSet.start();
    }

    void rotateRewindButton180DegreesIn() {
        final View button = getRewindImage();

        AnimatorSet animatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(mActivity,
                R.animator.rotate_180_degrees_in);

        BounceInterpolator interpolator = getRotate180BounceInterpolator();
        animatorSet.setInterpolator(interpolator);
        animatorSet.setTarget(button);
        animatorSet.start();
    }

    BounceInterpolator getRotate180BounceInterpolator() {
        return new BounceInterpolator(0.2, 7);
    }

    View getStartImage() {
        return (ImageView) mActivity.findViewById(R.id.startImage);
    }

    View getRewindImage() {
        return (ImageView) mActivity.findViewById(R.id.rewindImage);
    }
}
