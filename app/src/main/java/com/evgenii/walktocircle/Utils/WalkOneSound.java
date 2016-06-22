package com.evgenii.walktocircle.Utils;

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

    public void play() {
        stop();
        mPlayer = MediaPlayer.create(mContext, mSoundId);
        mPlayer.start();
    }
}
