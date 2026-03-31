package com.picturebook.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.picturebook.R;
import com.picturebook.model.StoryItem;
import com.picturebook.util.LoopingHelper;

public final class StoryAdapter extends ListAdapter<StoryItem, RecyclerView.ViewHolder> {
    private static final DiffUtil.ItemCallback<StoryItem> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<StoryItem>() {
                @Override
                public boolean areItemsTheSame(@NonNull StoryItem oldItem, @NonNull StoryItem newItem) {
                    return oldItem.equals(newItem);
                }

                @Override
                public boolean areContentsTheSame(@NonNull StoryItem oldItem, @NonNull StoryItem newItem) {
                    return oldItem.equals(newItem);
                }
            };

    private boolean loopEnabled;

    public StoryAdapter() {
        super(DIFF_CALLBACK);
        setHasStableIds(true);
    }

    public void setLoopEnabled(boolean loopEnabled) {
        int oldCount = getItemCount();
        this.loopEnabled = loopEnabled;
        int newCount = getItemCount();
        int changedCount = Math.min(oldCount, newCount);
        if (changedCount > 0) {
            notifyItemRangeChanged(0, changedCount);
        }
        if (newCount > oldCount) {
            notifyItemRangeInserted(oldCount, newCount - oldCount);
        } else if (oldCount > newCount) {
            notifyItemRangeRemoved(newCount, oldCount - newCount);
        }
    }

    @Override
    public long getItemId(int position) {
        StoryItem item = getRealItem(position);
        return item == null ? RecyclerView.NO_ID : item.hashCode();
    }

    @Override
    public int getItemCount() {
        return LoopingHelper.getDisplayCount(getCurrentList().size(), loopEnabled);
    }

    @Override
    public int getItemViewType(int position) {
        StoryItem item = getRealItem(position);
        return item == null ? StoryItem.Type.TEXT.ordinal() : item.getType().ordinal();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        StoryItem.Type type = StoryItem.Type.values()[viewType];
        if (type == StoryItem.Type.IMAGE) {
            return new ImageViewHolder(inflater.inflate(R.layout.item_story_image, parent, false));
        }
        if (type == StoryItem.Type.MIXED) {
            return new MixedViewHolder(inflater.inflate(R.layout.item_story_mixed, parent, false));
        }
        return new TextViewHolder(inflater.inflate(R.layout.item_story_text, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        StoryItem item = getRealItem(position);
        if (item == null) {
            return;
        }
        if (holder instanceof ImageViewHolder) {
            ((ImageViewHolder) holder).bind(item);
            return;
        }
        if (holder instanceof MixedViewHolder) {
            ((MixedViewHolder) holder).bind(item);
            return;
        }
        ((TextViewHolder) holder).bind(item);
    }

    private StoryItem getRealItem(int adapterPosition) {
        int size = getCurrentList().size();
        if (size == 0) {
            return null;
        }
        return getCurrentList().get(LoopingHelper.toRealPosition(adapterPosition, size));
    }

    private static final class ImageViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;

        private ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.storyImage);
        }

        private void bind(StoryItem item) {
            Glide.with(imageView).load(item.getImageUrl()).into(imageView);
        }
    }

    private static final class TextViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;

        private TextViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.storyText);
        }

        private void bind(StoryItem item) {
            textView.setText(item.getText());
        }
    }

    private static final class MixedViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;
        private final TextView textView;

        private MixedViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.storyImage);
            textView = itemView.findViewById(R.id.storyText);
        }

        private void bind(StoryItem item) {
            textView.setText(item.getText());
            if (TextUtils.isEmpty(item.getImageUrl())) {
                imageView.setImageDrawable(null);
                return;
            }
            Glide.with(imageView).load(item.getImageUrl()).into(imageView);
        }
    }
}
