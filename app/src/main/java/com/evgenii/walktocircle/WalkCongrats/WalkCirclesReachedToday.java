package com.evgenii.walktocircle.WalkCongrats;

import com.evgenii.walktocircle.MainActivityState;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WalkCirclesReachedToday {
    /**
     * Increments the number of circles reached today by one.
     */
    public static void increment() {
        MainActivityState.saveCirclesReachedToday(numberOfCirclesReachedToday() + 1);
        MainActivityState.saveLastCircleReachedDate(getCurrentYearMonthDay());
    }

    /**
     * @return number of circles reached today
     */
    public static int numberOfCirclesReachedToday() {
        if (MainActivityState.getInstance().getLastCircleReachedDate() == getCurrentYearMonthDay()) {
            return MainActivityState.getInstance().getCirclesReachedToday();
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
