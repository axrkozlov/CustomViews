package com.example.customviews;

import android.graphics.*;
import android.text.style.ReplacementSpan;
import android.util.Log;

class DottedUnderlineSpan extends ReplacementSpan {
    private final float mDashPathEffect=15;
    private final float mStrokeWidth=5;
    private Paint p;
    private int mWidth;

    private int mHeight;

    private String mSpan;

    private float mSpanLength;
    private boolean mLengthIsCached = false;
    private float density;

    public DottedUnderlineSpan(int _color, String _spannedText, Float density){
this.density=density;
        p = new Paint();
        p.setColor(Color.BLUE);
        p.setStyle(Paint.Style.STROKE);
        p.setPathEffect(new DashPathEffect(new float[]{mDashPathEffect, mDashPathEffect}, 0));
        p.setStrokeWidth(mStrokeWidth);
        mSpan = _spannedText;
        mWidth = (int) Math.ceil(16 * density);
        mHeight = (int) Math.ceil(16 * density);
    }

    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {

        /*
         * This method is where we make room for the drawing.
         * We are passed in a FontMetrics that we can check to see if there is enough space.
         * If we need to, we can alter these FontMetrics to suit our needs.
         */
        if (fm != null) {
            /*
             * Everything is measured from the baseline, so the ascent is a negative number,
             * and the top is an even more negative number.  We are going to make sure that
             * there is enough room between the top and the ascent line for the graphic.
             */
            if (- fm.top + fm.ascent < mHeight) {
                // if there is not enough room, "raise" the top
                fm.top = fm.ascent - mHeight;
            }
        }

        /*
         * the number returned is actually the width of the span.
         * you will want to make sure the span is wide enough for your graphic.
         */
        int textWidth = (int) Math.ceil(paint.measureText(text, start, end));
        Log.i("BLE", "getSize: textWidth"+ textWidth+" mWidth "+ mWidth);
        return Math.max(textWidth, mWidth);
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {

        Log.i("ewrt", "draw: "+mSpanLength);
        canvas.drawText(text, start, end, x, y, paint);
        if(!mLengthIsCached)
            mSpanLength = paint.measureText(text, start, end);
        // https://code.google.com/p/android/issues/detail?id=29944
        // canvas.drawLine can't draw dashes when hardware acceleration is enabled,
        // but canvas.drawPath can
        Path path = new Path();
        int mOffsetY=10;
        path.moveTo(x, y + mOffsetY);
        path.lineTo(x + mSpanLength, y + mOffsetY);
        canvas.drawPath(path, this.p);
    }
}

