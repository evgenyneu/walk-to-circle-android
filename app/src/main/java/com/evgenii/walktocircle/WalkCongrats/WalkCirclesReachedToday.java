package com.evgenii.walktocircle.walkCongrats;

import com.evgenii.walktocircle.MainActivityState;
import com.evgenii.walktocircle.R;
import com.evgenii.walktocircle.utils.WalkString;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class WalkCirclesReachedToday {
    /**
     * Increments the number of circles reached today by one.
     */
    public static void increment() {
        MainActivityState.saveCirclesReachedToday(numberOfCirclesReachedToday() + 1);
        MainActivityState.saveLastCircleReachedDate(getCurrentYearMonthDay());
    }

    public static String numberOfCirclesReachedTodayPhrase() {
        int circlesReachedToday = numberOfCirclesReachedToday();

        if (circlesReachedToday == 1) {
            return "" + WalkString.fromResource(R.string.congrats_reached_first_circle_today);
        } else {
            return circlesReachedToday + " " +  WalkString.fromResource(R.string.congrats_circles_reached_today);
        }
    }

    /**
     * @return number of circles reached today
     */
    private static int numberOfCirclesReachedToday() {
        String lastDate = MainActivityState.getInstance().getLastCircleReachedDate();

        if (lastDate != null && lastDate.equals(getCurrentYearMonthDay())) {
            return MainActivityState.getInstance().getCirclesReachedToday();
        }

        return 0;
    }

    /**
     * @return converts the date to string, example: 2017.02.21
     */
    public static String getYearMonthDay(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd", Locale.US);
        return dateFormat.format(date);
    }

    private static String getCurrentYearMonthDay() {
        return getYearMonthDay(new Date());
    }
}
