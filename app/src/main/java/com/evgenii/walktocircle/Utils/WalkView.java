package com.evgenii.walktocircle.Utils;

import android.view.View;

public class WalkView {
    public static void toggleView(View view, boolean show) {
        view.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}
