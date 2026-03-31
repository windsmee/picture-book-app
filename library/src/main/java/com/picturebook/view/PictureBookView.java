package com.picturebook.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.picturebook.R;
import com.picturebook.adapter.StoryAdapter;
import com.picturebook.controller.CarouselController;
import com.picturebook.model.StoryItem;

import java.util.List;

public final class PictureBookView extends FrameLayout {
    private final StoryAdapter adapter;
    private final CarouselController controller;
    private final RecyclerView recyclerView;

    public PictureBookView(@NonNull Context context) {
        this(context, null);
    }

    public PictureBookView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PictureBookView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.view_picture_book, this, true);
        recyclerView = findViewById(R.id.storyRecyclerView);
        adapter = new StoryAdapter();
        controller = new CarouselController(adapter);
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setOverScrollMode(OVER_SCROLL_NEVER);
        new LinearSnapHelper().attachToRecyclerView(recyclerView);
        controller.bindRecyclerView(recyclerView);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    controller.pause();
                } else if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    controller.resume();
                }
            }
        });
    }

    /**
     * Enable or disable auto scroll.
     */
    public void setAutoScroll(boolean enabled) {
        controller.setAutoScrollEnabled(enabled);
    }

    /**
     * Set auto scroll interval.
     */
    public void setInterval(long millis) {
        controller.setInterval(millis);
    }

    /**
     * Enable or disable loop playback.
     */
    public void setLoop(boolean enabled) {
        controller.setLoopEnabled(enabled);
    }

    /**
     * Set smooth scroll animation duration.
     */
    public void setAnimationDuration(long duration) {
        controller.setAnimationDuration(duration);
    }

    /**
     * Submit story data to the carousel.
     */
    public void setData(List<StoryItem> data) {
        controller.setData(data);
    }

    /**
     * Start carousel playback.
     */
    public void start() {
        controller.start();
    }

    /**
     * Stop carousel playback.
     */
    public void stop() {
        controller.stop();
    }

    @Override
    protected void onDetachedFromWindow() {
        controller.stop();
        super.onDetachedFromWindow();
    }
}
