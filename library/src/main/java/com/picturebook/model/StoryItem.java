package com.picturebook.model;

import java.util.Objects;

public final class StoryItem {
    public enum Type {
        IMAGE,
        TEXT,
        MIXED
    }

    private final Type type;
    private final String imageUrl;
    private final String text;

    public StoryItem(Type type, String imageUrl, String text) {
        this.type = type == null ? Type.TEXT : type;
        this.imageUrl = imageUrl;
        this.text = text;
    }

    public Type getType() {
        return type;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getText() {
        return text;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof StoryItem)) {
            return false;
        }
        StoryItem storyItem = (StoryItem) object;
        return type == storyItem.type
                && Objects.equals(imageUrl, storyItem.imageUrl)
                && Objects.equals(text, storyItem.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, imageUrl, text);
    }
}
