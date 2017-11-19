package de.carey.kotbbble.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import de.carey.kotbbble.R;

public class DividerItemDecoration extends RecyclerView.ItemDecoration {

    public static final int LIST_HORIZONTAL = 0;
    public static final int LIST_VERTICAL = 1;

    private int mDividerOrientation;
    private Paint mPaint;

    private int mLineWidth;

    public DividerItemDecoration(Context context, int orientation, int lineWidth) {
        mDividerOrientation = orientation;

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(ContextCompat.getColor(context, R.color.divider));
        mPaint.setStyle(Paint.Style.FILL);

        mLineWidth = lineWidth;
    }

    @Override
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        if (mDividerOrientation == LIST_HORIZONTAL) {
            drawVertical(canvas, parent);
        } else {
            drawHorizontal(canvas, parent);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (mDividerOrientation == LIST_VERTICAL) {
            outRect.set(0, 0, 0, mLineWidth);
        } else {
            outRect.set(0, 0, mLineWidth, 0);
        }
    }

    private void drawHorizontal(Canvas canvas, RecyclerView parent) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        int childCount = parent.getChildCount();

        for (int i = 1; i < childCount - 1; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            int top = child.getBottom() + layoutParams.bottomMargin;
            int bottom = top + mLineWidth;
            canvas.drawRect(left, top, right, bottom, mPaint);
        }
    }

    private void drawVertical(Canvas canvas, RecyclerView parent) {
        int top = parent.getPaddingTop();
        int bottom = parent.getHeight() - parent.getPaddingBottom();
        int childCount = parent.getChildCount();

        for (int i = 0; i < childCount - 1; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int left = child.getRight() + layoutParams.rightMargin;
            final int right = left + mLineWidth;
            canvas.drawRect(left, top, right, bottom, mPaint);
        }
    }
}