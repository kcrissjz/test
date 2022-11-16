package com.bdpp.common.recylcerview;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class GridDivider extends RecyclerView.ItemDecoration {

    //    private int columns;
    private float halfStrokeWidth;
    private Paint paint;
    private boolean includeEdge;

    public GridDivider(/*int columns,*/ int color, float strokeWidth, boolean includeEdge) {
//        this.columns = columns;
        this.halfStrokeWidth = strokeWidth / 2f;
        this.paint = new Paint();
        this.includeEdge = includeEdge;
        paint.setColor(color);
        paint.setStrokeWidth(strokeWidth);
    }

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
//            int position = parent.getChildAdapterPosition(child);

//            int row = position / columns;
//            int column = position % columns;

            int width = child.getWidth();
            int height = child.getHeight();
            float x = child.getX();
            float y = child.getY();

            float leftX = x + halfStrokeWidth;
            float rightX = x + width - halfStrokeWidth;
            float topY = y + halfStrokeWidth;
            float bottomY = y + height - halfStrokeWidth;

            //horizontal lines
            if (includeEdge || y + height != parent.getHeight())
                c.drawLine(leftX, bottomY, leftX + width, bottomY, paint);

//            if (row == 0) {
            if (includeEdge && y == 0) {
                c.drawLine(leftX, topY, leftX + width, topY, paint);
            }

            //vertical lines
            if (includeEdge || x + width != parent.getWidth())
                c.drawLine(rightX, y, rightX, y + height, paint);

//            if (column == 0) {
            if (includeEdge && x == 0) {
                c.drawLine(leftX, y, leftX, y + height, paint);
            }
        }
    }
}
