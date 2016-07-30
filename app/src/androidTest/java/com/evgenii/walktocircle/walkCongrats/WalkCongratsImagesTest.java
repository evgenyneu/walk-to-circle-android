package com.evgenii.walktocircle.walkCongrats;

import android.support.test.runner.AndroidJUnit4;

import com.evgenii.walktocircle.R;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class WalkCongratsImagesTest {
    @Test
    public void getImageForCirclesReached() {
        int result = WalkCongratsImages.getImageForCirclesReached(0);
        assertEquals(R.drawable.congrats_optimized_1, result);

        result = WalkCongratsImages.getImageForCirclesReached(1);
        assertEquals(R.drawable.congrats_optimized_1, result);

        result = WalkCongratsImages.getImageForCirclesReached(2);
        assertEquals(R.drawable.congrats_optimized_2, result);

        result = WalkCongratsImages.getImageForCirclesReached(3);
        assertEquals(R.drawable.congrats_optimized_3, result);

        result = WalkCongratsImages.getImageForCirclesReached(4);
        assertEquals(R.drawable.congrats_optimized_4, result);

        result = WalkCongratsImages.getImageForCirclesReached(5);
        assertEquals(R.drawable.congrats_optimized_5, result);

        result = WalkCongratsImages.getImageForCirclesReached(6);
        assertEquals(R.drawable.congrats_optimized_6, result);

        result = WalkCongratsImages.getImageForCirclesReached(7);
        assertEquals(R.drawable.congrats_optimized_7, result);

        result = WalkCongratsImages.getImageForCirclesReached(8);
        assertEquals(R.drawable.congrats_optimized_8, result);

        result = WalkCongratsImages.getImageForCirclesReached(9);
        assertEquals(R.drawable.congrats_optimized_9, result);

        result = WalkCongratsImages.getImageForCirclesReached(10);
        assertEquals(R.drawable.congrats_optimized_10, result);

        result = WalkCongratsImages.getImageForCirclesReached(11);
        assertEquals(R.drawable.congrats_optimized_11, result);

        result = WalkCongratsImages.getImageForCirclesReached(12);
        assertEquals(R.drawable.congrats_optimized_12, result);

        result = WalkCongratsImages.getImageForCirclesReached(13);
        assertEquals(R.drawable.congrats_optimized_13, result);

        result = WalkCongratsImages.getImageForCirclesReached(14);
        assertEquals(R.drawable.congrats_optimized_13, result);

        result = WalkCongratsImages.getImageForCirclesReached(15);
        assertEquals(R.drawable.congrats_optimized_15, result);

        result = WalkCongratsImages.getImageForCirclesReached(16);
        assertEquals(R.drawable.congrats_optimized_16, result);

        result = WalkCongratsImages.getImageForCirclesReached(17);
        assertEquals(R.drawable.congrats_optimized_16, result);

        result = WalkCongratsImages.getImageForCirclesReached(18);
        assertEquals(R.drawable.congrats_optimized_18, result);

        result = WalkCongratsImages.getImageForCirclesReached(19);
        assertEquals(R.drawable.congrats_optimized_18, result);

        result = WalkCongratsImages.getImageForCirclesReached(20);
        assertEquals(R.drawable.congrats_optimized_20, result);

        result = WalkCongratsImages.getImageForCirclesReached(21);
        assertEquals(R.drawable.congrats_optimized_20, result);

        result = WalkCongratsImages.getImageForCirclesReached(22);
        assertEquals(R.drawable.congrats_optimized_20, result);

        result = WalkCongratsImages.getImageForCirclesReached(322);
        assertEquals(R.drawable.congrats_optimized_20, result);
    }
}
