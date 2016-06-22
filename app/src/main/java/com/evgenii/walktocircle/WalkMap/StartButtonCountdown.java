package com.evgenii.walktocircle.WalkMap;

import android.app.Activity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.evgenii.walktocircle.R;

public class StartButtonCountdown {
    Activity mActivity;

    public StartButtonCountdown(Activity activity) {
        mActivity = activity;
    }

    void startCountdown() {
        rotateRewindArrows();
        setInitialNumber();
    }

    private void setInitialNumber() {
        final TextView textView = getCountdownTextView();
        int countdownDuration = mActivity.getResources().getInteger(R.integer.map_countdown_duration_seconds);
        textView.setText(String.valueOf(countdownDuration));
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

    private TextView getCountdownTextView() {
        return (TextView) mActivity.findViewById(R.id.countdownTextView);
    }
}
