package com.example.newsapp;

import android.graphics.Rect;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

public class CenterSpacingItemDecoration extends RecyclerView.ItemDecoration {
    private final int spacing;

    public CenterSpacingItemDecoration(int spacing) {
        this.spacing = spacing;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        int itemCount = parent.getAdapter().getItemCount();

        int half = spacing / 2;
        if (position == 0) {
            outRect.set(spacing, 0, half, 0);
        } else if (position == itemCount - 1) {
            outRect.set(half, 0, spacing, 0);
        } else {
            outRect.set(half, 0, half, 0);
        }
    }
}
