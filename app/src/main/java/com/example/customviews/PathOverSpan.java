package com.example.customviews;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ReplacementSpan;
import android.text.style.TypefaceSpan;
import android.util.Log;

public class PathOverSpan extends ReplacementSpan {

    private static final String TAG = "PathOverSpan";

    private float mDensity;

    private Path mPath;

    private int mWidth;

    private int mHeight;

    private Paint mPaint;

    public PathOverSpan(float density) {

        mDensity = density;
        mPath = new Path();
        mWidth = (int) Math.ceil(16 * mDensity);
        mHeight = (int) Math.ceil(16 * mDensity);
        // we will make a small triangle
        mPath.moveTo(mWidth/2, 0);
        mPath.lineTo(mWidth, mHeight);
        mPath.lineTo(0, mHeight);
        mPath.close();

        /*
         * set up a paint for our shape.
         * The important things are the color and style = fill
         */
        mPaint = new Paint();
        mPaint.setColor(Color.GREEN);
        mPaint.setStyle(Paint.Style.FILL);
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
//                fm.top=-103;
//                fm.ascent=-61;
                Log.i(TAG, "getSize: fm.top=" + fm.top + "; fm.ascent=" + fm.ascent + "; mHeight=" + mHeight);
            }
        }

        /*
         * the number returned is actually the width of the span.
         * you will want to make sure the span is wide enough for your graphic.
         */
        int textWidth = (int) Math.ceil(paint.measureText(text, start, end));
        Log.i(TAG, "getSize: textWidth="+ textWidth+"; mWidth "+ mWidth + "; text="+text+"; start="+ start+ "; end="+ end);
        return Math.max(textWidth, mWidth);
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        Log.d(TAG, "draw(), x = " + x + ", top = " + top + ", y = " + y + ", bottom = " + bottom);

        // first thing we do is draw the text that is not drawn because it is being "replaced"
        // you may have to adjust x if the graphic is wider and you want to center-align
        canvas.drawText(text, start, end, x, y, paint);

//         calculate an offset to center the shape
        int textWidth = (int) Math.ceil(paint.measureText(text, start, end));
        int offset = 0;
        if (textWidth > mWidth) {
            offset = (textWidth - mWidth) / 2;
        }

        // we set the bounds relative to upper left corner of the span
        canvas.translate(x + offset, top);
        canvas.drawPath(mPath, mPaint);
        canvas.translate(-x - offset, -top);
    }
}