package com.evgenii.walktocircle.Utils;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

import java.util.HashMap;

public class WalkSounds {
    private HashMap<Integer, WalkOneSound> mSounds = new HashMap<Integer, WalkOneSound>();
    private Context mContext;

    public WalkSounds(Context context) {
        mContext = context;
    }

    public void stop() {
        for (WalkOneSound oneSound : mSounds.values()) {
            oneSound.stop();
        }
        mSounds.clear();
    }

    public void playSound(int soundId) {
        WalkOneSound mOneSound = mSounds.get(soundId);

        if (mOneSound == null) {
            mOneSound = new WalkOneSound(mContext, soundId);
            mSounds.put(soundId, mOneSound);
        }

        mOneSound.play();
    }
}
