package com.evgenii.walktocircle.utils;

import android.view.View;

public class WalkCameraDistance {
    public static void setFragmentCameraDistance(View view) {
        float scale = view.getResources().getDisplayMetrics().density;
        view.setCameraDistance(5000 * scale);
    }
}
