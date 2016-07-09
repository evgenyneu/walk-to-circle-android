package com.evgenii.walktocircle.WalkMap;

import android.location.Location;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
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

import java.util.Timer;
import java.util.TimerTask;

public class StartButtonCountdown {
    private Timer mTimer;

    int currentCountdownValue = 0;

    void rotateAndShowInitialNumber() {
        cancelCountdownTimer();
        rotateRewindArrows();
        showInitialNumber();
    }

    void stopCountdown() {
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
        currentCountdownValue = getCountdownDurationSeconds() + getDelayBeforeCountdownSeconds();

        mTimer = new Timer();

        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                MainActivity.instance.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        didTimerTick();
                    }
                });
            }

        }, 500, 1000);
    }

    private void didTimerTick() {
        currentCountdownValue -= 1;

        if (currentCountdownValue < getCountdownDurationSeconds()) {
            updateCountdownValue(currentCountdownValue);
            playClickSound();
        }

        if (currentCountdownValue == 0) {
            cancelCountdownTimer();
            startWalking();
        }
    }

    private void startWalking() {
        WalkFragmentType.showWithAnimation();
        WalkApplication.getSounds().playSound(R.raw.large_door, WalkConstants.mapShowWalkScreenVolume);
    }

    private void cancelCountdownTimer() {
        if (mTimer == null) { return; }
        mTimer.cancel();
        mTimer = null;
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
