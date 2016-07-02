package com.evgenii.walktocircle.WalkMap;

import android.location.Location;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.evgenii.walktocircle.FragmentManager.WalkFragmentType;
import com.evgenii.walktocircle.MainActivity;
import com.evgenii.walktocircle.MainActivityState;
import com.evgenii.walktocircle.R;
import com.evgenii.walktocircle.Utils.WalkLocation;
import com.evgenii.walktocircle.WalkApplication;
import com.evgenii.walktocircle.WalkConstants;

public class StartButtonCountdown {
    CountDownTimer mCountdownTimer;
    int currentCountdownValue = 0;
    Location mPinLocation;

    public StartButtonCountdown(Location pinLocation) {
        mPinLocation = pinLocation;
    }

    void rotateAndShowInitialNumber() {
        cancelCountdownTimer();
        rotateRewindArrows();
        showInitialNumber();
    }

    void stopCountdown() {
        mPinLocation = null;
        cancelCountdownTimer();
    }

    private void showInitialNumber() {
        int countdownDurationSeconds = getCountdownDurationSeconds();
        updateCountdownValue(countdownDurationSeconds);
    }

    private void updateCountdownValue(int value) {
        final TextView textView = getCountdownTextView();
        if (textView == null) { return; }
        textView.setText(String.valueOf(value));
    }

    private void rotateRewindArrows() {
        final Animation animation = AnimationUtils.loadAnimation(MainActivity.instance, R.anim.rotate_continuously);
        final View view = getRewindArrows();
        view.clearAnimation();
        view.startAnimation(animation);
    }

    public void startCountdownTimer() {
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

                if (currentCountdownValue == 0) {
                    startWalking();
                }
            }

            public void onFinish() { }
        };

        mCountdownTimer.start();
    }

    private void startWalking() {
            MainActivityState.getInstance().savePinLocation(WalkLocation.latLngFromLocation(mPinLocation));
        WalkFragmentType.showWithAnimation();
    }

    private void cancelCountdownTimer() {
        currentCountdownValue = getCountdownDurationSeconds() + getDelayBeforeCountdownSeconds();
        if (mCountdownTimer == null) { return; }
        mCountdownTimer.cancel();
        mCountdownTimer = null;
    }

    private void playClickSound() {
        WalkApplication.getSounds().playSound(R.raw.click_sound,
                WalkConstants.mapStartButtonCountdownClickVolume);
    }

    private View getRewindArrows() {
        return MainActivity.instance.findViewById(R.id.rewindArrowsImageView);
    }

    private TextView getCountdownTextView() {
        return (TextView) MainActivity.instance.findViewById(R.id.countdownTextView);
    }

    private int getCountdownDurationSeconds() {
        return MainActivity.instance.getResources().getInteger(R.integer.map_countdown_duration_seconds);
    }

    private int getDelayBeforeCountdownSeconds() {
        return MainActivity.instance.getResources().getInteger(R.integer.map_delay_before_countdown_seconds);
    }
}
