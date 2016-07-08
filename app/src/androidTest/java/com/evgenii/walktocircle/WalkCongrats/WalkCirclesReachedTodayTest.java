package com.evgenii.walktocircle.WalkCongrats;

import android.support.test.runner.AndroidJUnit4;

import com.evgenii.walktocircle.WalkWalk.WalkRandomQuoteNumberGenerator;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.GregorianCalendar;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class WalkCirclesReachedTodayTest {

    @Test
    public void returnsCirclesReachedToday_lastDateMatchesToday() {
        Date date = new GregorianCalendar(2021, 4, 21).getTime();

        int result = WalkCirclesReachedToday.get("2021.05.21", date, 2);

        assertEquals(2, result);
    }

    @Test
    public void returnsYearMonthDateAsText() {
        Date date = new GregorianCalendar(2021, 4, 21).getTime();
        String result = WalkCirclesReachedToday.getYearMonthDay(date);

        assertEquals("2021.05.21", result);
    }
}
