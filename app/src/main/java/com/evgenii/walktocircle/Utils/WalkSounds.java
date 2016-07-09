package com.evgenii.walktocircle.utils;

import android.content.Context;

import java.util.HashMap;

public class WalkSounds {
    private HashMap<Integer, WalkOneSound> mSounds = new HashMap<Integer, WalkOneSound>();
    private Context mContext;

    // Does not play any sounds when true
    private Boolean silenced = false;

    public WalkSounds(Context context) {
        mContext = context;
    }

    public void unSilence() {
        silenced = false;
    }

    // Stops all currently playing sounds and prevents from playing sounds in the background
    // until unSilence() is called
    public void silence() {
        silenced = true;
        stop();
    }

    public void stop() {
        for (WalkOneSound oneSound : mSounds.values()) {
            oneSound.stop();
        }
        mSounds.clear();
    }

    public void playSound(int soundId, double volume) {
        if (silenced) { return; }

        WalkOneSound mOneSound = mSounds.get(soundId);

        if (mOneSound == null) {
            mOneSound = new WalkOneSound(mContext, soundId);
            mSounds.put(soundId, mOneSound);
        }

        mOneSound.play(volume);
    }
}
