package com.evgenii.walktocircle.walkWalk;

import android.support.test.runner.AndroidJUnit4;

import com.evgenii.walktocircle.utils.WalkRandomNumberGenerator;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class WalkRandomQuoteNumberGeneratorTest {

    @Test
    public void returnsRandomNumber() {
        WalkRandomNumberGenerator generator = new WalkRandomNumberGenerator();

        for (int i = 0; i < 100; i++) {
            int result = generator.getRandomIntUntil(5);

            assertThat("result", result, greaterThanOrEqualTo(0));
            assertThat("result", result, lessThan(5));
        }
    }

    @Test
    public void returnsRandomNumber_upperBoundaryIsOne() {
        WalkRandomNumberGenerator generator = new WalkRandomNumberGenerator();

        int result = generator.getRandomIntUntil(1);

        assertEquals(0, result);
    }

    @Test
    public void returnsRandomNumber_upperBoundaryIsZero() {
        WalkRandomNumberGenerator generator = new WalkRandomNumberGenerator();

        int result = generator.getRandomIntUntil(0);

        assertEquals(0, result);
    }
}
