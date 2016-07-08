package com.evgenii.walktocircle.Utils;

import com.evgenii.walktocircle.R;
import com.evgenii.walktocircle.WalkApplication;

public class WalkString {
    public static String fromResource(int id) {
        return WalkApplication.getAppContext().getResources().getString(id);
    }
}
