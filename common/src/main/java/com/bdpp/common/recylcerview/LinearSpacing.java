package com.bdpp.common.recylcerview;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class LinearSpacing extends RecyclerView.ItemDecoration {

    private int left, top, right, bottom;
    private int orientation;
    private boolean includeEdge;

    public LinearSpacing(int orientation, int horizontalOffset, int verticalOffset, boolean includeEdge) {
        this(orientation, horizontalOffset, verticalOffset, horizontalOffset, verticalOffset, includeEdge);
    }

    public LinearSpacing(int orientation, int left, int top, int right, int bottom, boolean includeEdge) {
        this.orientation = orientation;
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        this.includeEdge = includeEdge;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        int totalCount = parent.getAdapter().getItemCount();
        boolean isFirstOne = position == 0;
        boolean isLastOne = position == totalCount - 1;

        if (orientation == LinearLayoutManager.VERTICAL) {
            outRect.left = left;
            outRect.right = right;
            outRect.bottom = bottom;
            if (isFirstOne && includeEdge) {
                outRect.top = top;
            }
            if (isLastOne && !includeEdge)
                outRect.bottom = 0;
        } else if (orientation == LinearLayoutManager.HORIZONTAL) {
            outRect.top = top;
            outRect.bottom = bottom;
            outRect.right = right;
            if (isFirstOne && includeEdge) {
                outRect.left = left;
            }
            if (isLastOne && !includeEdge)
                outRect.right = 0;
        }
    }
}
