package com.evgenii.walktocircle.utils;

import android.content.Context;
import android.media.MediaPlayer;

public class WalkOneSound {
    private Context mContext;
    private int mSoundId;
    private MediaPlayer mPlayer;


    public WalkOneSound(Context context, int soundId) {
        mContext = context;
        mSoundId = soundId;
    }

    public void stop() {
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.reset();
            mPlayer = null;
        }
    }

    public void play(double volume) {
        stop();
        mPlayer = MediaPlayer.create(mContext, mSoundId);
        mPlayer.setVolume((float)volume, (float)volume);
        mPlayer.start();
    }
}
