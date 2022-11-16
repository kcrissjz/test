package com.bdpp.common.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import com.bdpp.common.R;


public class RoundCornerImageView extends RatioImageView {

    private Paint paint;
    private PorterDuffXfermode porterDuffXfermode;
    private float roundCornerRadius;
    private float topLeftRadius;
    private float topRightRadius;
    private float bottomLeftRadius;
    private float bottomRightRadius;
    private float strokeWidth;
    private int strokeColor;
    private Bitmap roundCornerBitmap;
    private RectF strokeRect;

    public RoundCornerImageView(Context context) {
        this(context, null);
    }

    public RoundCornerImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundCornerImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        porterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundCornerImageView);
            roundCornerRadius = typedArray.getDimension(R.styleable.RoundCornerImageView_roundCornerRadius, 0);
            topLeftRadius = typedArray.getDimension(R.styleable.RoundCornerImageView_rcTopLeftRadius, 0);
            topRightRadius = typedArray.getDimension(R.styleable.RoundCornerImageView_rcTopRightRadius, 0);
            bottomLeftRadius = typedArray.getDimension(R.styleable.RoundCornerImageView_rcBottomLeftRadius, 0);
            bottomRightRadius = typedArray.getDimension(R.styleable.RoundCornerImageView_rcBottomRightRadius, 0);
            strokeWidth = typedArray.getDimension(R.styleable.RoundCornerImageView_rciStrokeWidth, 0);
            strokeColor = typedArray.getColor(R.styleable.RoundCornerImageView_rciStrokeColor, Color.BLACK);
            typedArray.recycle();
        }
    }

    public void setRoundCornerRadius(float radius) {
        this.roundCornerRadius = radius;
        createMask();
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        createMask();
    }

    private void createMask() {
        int w = getWidth();
        int h = getHeight();
        if (roundCornerRadius < 0)
            roundCornerRadius = h / 2f;

        if (roundCornerBitmap != null) roundCornerBitmap.recycle();
        if (w > 0 && h > 0) {
            if (roundCornerRadius > 0) {
                roundCornerBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
                new Canvas(roundCornerBitmap).drawRoundRect(0, 0, w, h,
                        roundCornerRadius, roundCornerRadius, paint);

                strokeRect = null;
                if (strokeWidth > 0f) {
                    float halfStrokeWidth = strokeWidth / 2f;
                    strokeRect = new RectF(halfStrokeWidth, halfStrokeWidth, w - halfStrokeWidth, h - halfStrokeWidth);
                }
            } else if (hasIrregularCorner()) {
                //stroke unusable

                roundCornerBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
                Path circles = new Path();
                circles.addCircle(topLeftRadius, topLeftRadius, topLeftRadius, Path.Direction.CW);
                circles.addCircle(w - topRightRadius, topRightRadius, topRightRadius, Path.Direction.CW);
                circles.addCircle(bottomLeftRadius, h - bottomLeftRadius, bottomLeftRadius, Path.Direction.CW);
                circles.addCircle(w - bottomRightRadius, h - bottomRightRadius, bottomRightRadius, Path.Direction.CW);

                Path polygon = new Path();
                polygon.moveTo(topLeftRadius, 0);
                polygon.lineTo(w - topRightRadius, 0);
                polygon.lineTo(w, topRightRadius);
                polygon.lineTo(w, h - bottomRightRadius);
                polygon.lineTo(w - bottomRightRadius, h);
                polygon.lineTo(bottomLeftRadius, h);
                polygon.lineTo(0, h - bottomLeftRadius);
                polygon.lineTo(0, topLeftRadius);
                polygon.close();

                circles.addPath(polygon);
                strokeRect = null;

                new Canvas(roundCornerBitmap).drawPath(circles, paint);
            }
        }
    }

    private boolean hasIrregularCorner() {
        return topLeftRadius > 0 || topRightRadius > 0
                || bottomLeftRadius > 0 || bottomRightRadius > 0;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int layer = canvas.saveLayer(0, 0, getWidth(),
                getHeight(), null, Canvas.ALL_SAVE_FLAG);
        super.onDraw(canvas);
        if ((roundCornerRadius > 0 || hasIrregularCorner()) && roundCornerBitmap != null && !roundCornerBitmap.isRecycled()) {
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.BLACK);
            paint.setXfermode(porterDuffXfermode);
            canvas.drawBitmap(roundCornerBitmap, 0, 0, paint);
            paint.setXfermode(null);
        }
        canvas.restoreToCount(layer);

        if (strokeRect != null) {
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(strokeWidth);
            paint.setColor(strokeColor);
            canvas.drawRoundRect(strokeRect, roundCornerRadius, roundCornerRadius, paint);
        }
    }
}
