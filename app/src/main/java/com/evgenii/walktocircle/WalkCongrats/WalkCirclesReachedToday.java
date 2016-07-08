package com.evgenii.walktocircle.WalkCongrats;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WalkCirclesReachedToday {
    public static int get(String lastCirclesReachedSavedDate, Date today, int circlesReached) {
        if (lastCirclesReachedSavedDate == getYearMonthDay(today)) {
            return circlesReached;
        }

        return 0;
    }

    /**
     * @return converts the date to string, example: 2017.02.21
     */
    public static String getYearMonthDay(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        return dateFormat.format(date);
    }

    public static String getCurrentYearMonthDay() {
        return getYearMonthDay(new Date());
    }
}
