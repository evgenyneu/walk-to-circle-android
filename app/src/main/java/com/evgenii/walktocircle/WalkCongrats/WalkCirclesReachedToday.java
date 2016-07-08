package com.evgenii.walktocircle.WalkCongrats;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WalkCirclesReachedToday {
    public static int get() {
        return 1;
    }

    public static String getYearMonthDay(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        return dateFormat.format(date);
    }
}
