package com.evgenii.walktocircle.WalkMap;
import com.evgenii.walktocircle.R;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.Activity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
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

        final Button button = getStartButton();
        button.setEnabled(false); // Disable button to improve animation performance
        button.setVisibility(View.VISIBLE);

        myAnim.setAnimationListener(new AnimationListener() {
                    @Override
                    public void onAnimationEnd(Animation animation) {
                        button.setEnabled(true);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) { }

                    @Override
                    public void onAnimationStart(Animation animation) { }
                });

        button.startAnimation(myAnim);

        WalkApplication.getSounds().playSound(R.raw.blop);
    }

    public void rotate180Degrees() {
        final Button button = getStartButton();
        button.setEnabled(false); // Disable button to improve animation performance

        AnimatorSet animatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(mActivity,
                R.animator.rotate_start_button_180);

        BounceInterpolator interpolator = new BounceInterpolator(0.2, 7);
        animatorSet.setInterpolator(interpolator);
        animatorSet.setTarget(button);

        animatorSet.addListener(
        new Animator.AnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
                button.setEnabled(true);
            }

            @Override
            public void onAnimationCancel(Animator animation) { }

            @Override
            public void onAnimationRepeat(Animator animation) { }

            @Override
            public void onAnimationStart(Animator animation) { }
        });

        animatorSet.start();
    }

    public Button getStartButton() {
        return (Button) mActivity.findViewById(R.id.startActivityButton);
    }
}
