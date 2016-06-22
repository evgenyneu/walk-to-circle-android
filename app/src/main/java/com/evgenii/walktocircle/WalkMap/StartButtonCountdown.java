package com.evgenii.walktocircle.WalkMap;

import android.app.Activity;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.evgenii.walktocircle.R;
import com.evgenii.walktocircle.WalkApplication;

public class StartButtonCountdown {
    Activity mActivity;
    CountDownTimer mCountdownTimer;
    int currentCountdownValue = 0;

    public StartButtonCountdown(Activity activity) {
        mActivity = activity;
    }

    void startCountdown() {
        rotateRewindArrows();
        setInitialNumber();
        startCountdownTimer();
    }

    void stopCountdown() {
        cancelCountdownTimer();
    }

    private void setInitialNumber() {
        int countdownDurationSeconds = getCountdownDurationSeconds();
        updateCountdownValue(countdownDurationSeconds);
    }

    private void updateCountdownValue(int value) {
        final TextView textView = getCountdownTextView();
        if (textView == null) { return; }
        textView.setText(String.valueOf(value));
    }

    private void rotateRewindArrows() {
        final Animation animation = AnimationUtils.loadAnimation(mActivity, R.anim.rotate_continuously);
        final View view = getRewindArrows();
        view.clearAnimation();
        view.startAnimation(animation);
    }

    private void startCountdownTimer() {
        cancelCountdownTimer();

        int delayBeforeCountdownSeconds = getDelayBeforeCountdownSeconds();

        int countdownDurationMilliseconds = (getCountdownDurationSeconds() +
                delayBeforeCountdownSeconds + 1) * 1000;

        mCountdownTimer = new CountDownTimer(countdownDurationMilliseconds, 1000) {
            public void onTick(long millisUntilFinished) {
                currentCountdownValue -= 1;

                if (currentCountdownValue < getCountdownDurationSeconds()) {
                    updateCountdownValue(currentCountdownValue);
                    playClickSound();
                }

                if (currentCountdownValue == 0) {}
            }

            public void onFinish() { }
        };

        mCountdownTimer.start();
    }

    private void cancelCountdownTimer() {
        currentCountdownValue = getCountdownDurationSeconds() + getDelayBeforeCountdownSeconds();
        if (mCountdownTimer == null) { return; }
        mCountdownTimer.cancel();
        mCountdownTimer = null;
    }

    private void playClickSound() {
        WalkApplication.getSounds().playSound(R.raw.click_sound);
    }

    private View getRewindArrows() {
        return mActivity.findViewById(R.id.rewindArrowsImageView);
    }

    private TextView getCountdownTextView() {
        return (TextView) mActivity.findViewById(R.id.countdownTextView);
    }

    private int getCountdownDurationSeconds() {
        return mActivity.getResources().getInteger(R.integer.map_countdown_duration_seconds);
    }

    private int getDelayBeforeCountdownSeconds() {
        return mActivity.getResources().getInteger(R.integer.map_delay_before_countdown_seconds);
    }
}
