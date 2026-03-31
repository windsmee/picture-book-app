package com.picturebook.sample;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.picturebook.model.StoryItem;
import com.picturebook.view.PictureBookView;

import java.util.ArrayList;
import java.util.List;

public final class MainActivity extends AppCompatActivity {
    private PictureBookView pictureBookView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pictureBookView = findViewById(R.id.pictureBookView);
        pictureBookView.setAutoScroll(true);
        pictureBookView.setLoop(true);
        pictureBookView.setInterval(2500L);
        pictureBookView.setAnimationDuration(300L);
        pictureBookView.setData(createSampleItems());
    }

    @Override
    protected void onStart() {
        super.onStart();
        pictureBookView.start();
    }

    @Override
    protected void onStop() {
        pictureBookView.stop();
        super.onStop();
    }

    private List<StoryItem> createSampleItems() {
        List<StoryItem> items = new ArrayList<>();
        items.add(new StoryItem(StoryItem.Type.TEXT, null, "Welcome to PictureBook SDK"));
        items.add(new StoryItem(StoryItem.Type.TEXT, null, "Auto-scroll and loop are enabled"));
        items.add(new StoryItem(StoryItem.Type.TEXT, null, "Swipe manually to test interaction"));
        return items;
    }
}
