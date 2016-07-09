package com.evgenii.walktocircle.utils;

import com.evgenii.walktocircle.WalkApplication;

public class WalkString {
    public static String fromResource(int id) {
        return WalkApplication.getAppContext().getResources().getString(id);
    }
}
