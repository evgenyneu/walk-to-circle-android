package com.evgenii.walktocircle.Utils;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

public class WalkSounds {
    MediaPlayer mPlayer;
    Context mContext;

    public WalkSounds(Context context) {
        mContext = context;
    }

    public void destroy() {
        // Stop the sound
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer = null;
        }
    }

    public void playSound(int soundId) {
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.reset();
        }

        mPlayer = MediaPlayer.create(mContext, soundId);
        mPlayer.start();
    }
}
