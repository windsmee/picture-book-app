package com.picturebook.util;

public final class LoopingHelper {
    private static final int LOOP_MULTIPLIER = 1000;

    private LoopingHelper() {
    }

    public static int getDisplayCount(int size, boolean loopEnabled) {
        if (size <= 0) {
            return 0;
        }
        if (!loopEnabled || size == 1) {
            return size;
        }
        return size * LOOP_MULTIPLIER;
    }

    public static int toRealPosition(int position, int size) {
        if (size <= 0) {
            return 0;
        }
        int result = position % size;
        return result < 0 ? result + size : result;
    }

    public static int getInitialPosition(int size) {
        if (size <= 1) {
            return 0;
        }
        int midpoint = (size * LOOP_MULTIPLIER) / 2;
        return midpoint - (midpoint % size);
    }
}
