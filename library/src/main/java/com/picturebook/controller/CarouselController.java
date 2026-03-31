package com.picturebook.controller;

import android.os.Handler;
import android.os.Looper;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.picturebook.adapter.StoryAdapter;
import com.picturebook.animation.PictureBookScroller;
import com.picturebook.model.StoryItem;
import com.picturebook.util.LoopingHelper;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public final class CarouselController {
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final AutoScrollTask autoScrollTask = new AutoScrollTask(this);
    private final StoryAdapter adapter;

    private WeakReference<RecyclerView> recyclerViewRef;
    private boolean autoScrollEnabled = true;
    private boolean loopEnabled = true;
    private boolean running;
    private long intervalMillis = 3000L;
    private long animationDurationMillis = 350L;

    public CarouselController(StoryAdapter adapter) {
        this.adapter = adapter;
    }

    public void bindRecyclerView(RecyclerView recyclerView) {
        recyclerViewRef = new WeakReference<>(recyclerView);
    }

    public void setAutoScrollEnabled(boolean enabled) {
        autoScrollEnabled = enabled;
        restartIfNeeded();
    }

    public void setLoopEnabled(boolean enabled) {
        loopEnabled = enabled;
        adapter.setLoopEnabled(enabled);
        resetToInitialPosition();
        restartIfNeeded();
    }

    public void setInterval(long millis) {
        intervalMillis = Math.max(500L, millis);
        restartIfNeeded();
    }

    public void setAnimationDuration(long duration) {
        animationDurationMillis = Math.max(100L, duration);
    }

    public void setData(List<StoryItem> data) {
        adapter.submitList(data == null ? new ArrayList<StoryItem>() : new ArrayList<>(data));
        resetToInitialPosition();
        restartIfNeeded();
    }

    public void start() {
        if (running) {
            return;
        }
        running = true;
        scheduleNext();
    }

    public void stop() {
        running = false;
        handler.removeCallbacks(autoScrollTask);
    }

    public void pause() {
        handler.removeCallbacks(autoScrollTask);
    }

    public void resume() {
        scheduleNext();
    }

    private void restartIfNeeded() {
        if (!running) {
            return;
        }
        pause();
        scheduleNext();
    }

    private void scheduleNext() {
        if (!running || !autoScrollEnabled || adapter.getCurrentList().size() <= 1) {
            return;
        }
        handler.removeCallbacks(autoScrollTask);
        handler.postDelayed(autoScrollTask, intervalMillis);
    }

    private void resetToInitialPosition() {
        RecyclerView recyclerView = getRecyclerView();
        if (recyclerView == null || adapter.getCurrentList().isEmpty()) {
            return;
        }
        recyclerView.scrollToPosition(LoopingHelper.getInitialPosition(adapter.getCurrentList().size()));
    }

    private void scrollToNext() {
        RecyclerView recyclerView = getRecyclerView();
        if (recyclerView == null) {
            stop();
            return;
        }
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (!(layoutManager instanceof LinearLayoutManager)) {
            scheduleNext();
            return;
        }
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
        int currentPosition = linearLayoutManager.findFirstVisibleItemPosition();
        if (currentPosition == RecyclerView.NO_POSITION) {
            scheduleNext();
            return;
        }
        int targetPosition = currentPosition + 1;
        if (!loopEnabled && targetPosition >= adapter.getItemCount()) {
            stop();
            return;
        }
        PictureBookScroller scroller = new PictureBookScroller(recyclerView, animationDurationMillis);
        scroller.setTargetPosition(targetPosition);
        layoutManager.startSmoothScroll(scroller);
        scheduleNext();
    }

    private RecyclerView getRecyclerView() {
        return recyclerViewRef == null ? null : recyclerViewRef.get();
    }

    private static final class AutoScrollTask implements Runnable {
        private final WeakReference<CarouselController> controllerRef;

        private AutoScrollTask(CarouselController controller) {
            controllerRef = new WeakReference<>(controller);
        }

        @Override
        public void run() {
            CarouselController controller = controllerRef.get();
            if (controller != null) {
                controller.scrollToNext();
            }
        }
    }
}
