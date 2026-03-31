package com.picturebook.animation;

import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

public final class PictureBookScroller extends LinearSmoothScroller {
    private final long durationMillis;

    public PictureBookScroller(RecyclerView recyclerView, long durationMillis) {
        super(recyclerView.getContext());
        this.durationMillis = Math.max(1L, durationMillis);
    }

    @Override
    protected int calculateTimeForScrolling(int dx) {
        return (int) Math.min(Integer.MAX_VALUE, durationMillis);
    }
}
