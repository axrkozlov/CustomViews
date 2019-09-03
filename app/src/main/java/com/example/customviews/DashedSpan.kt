//package com.example.customviews
//
//import android.graphics.Canvas
//import android.graphics.Color
//import android.graphics.Paint
//import android.graphics.Path
//import android.graphics.drawable.Drawable
//import android.text.style.ReplacementSpan
//import android.util.Log
//import kotlin.math.ceil
//import kotlin.math.max
//import android.graphics.DashPathEffect
//
//
//
//class DashedSpan(private val mDensity: Float) : ReplacementSpan() {
//
//    private var mPath: Path
////
//    private val mWidth: Int
//
//    private val mHeight: Int
//
//    private val mPaint: Paint
//    private var spanLength:Float=0f
//    var textWidth:Int=0
//    init {
//        mPath = Path()
//        mWidth = ceil((16 * mDensity).toDouble()).toInt()
//        mHeight = ceil((16 * mDensity).toDouble()).toInt()
//
////        // we will make a small triangle
//
//
//        /*
//         * set up a paint for our shape.
//         * The important things are the color and style = fill
//         */
//        mPaint = Paint()
//        mPaint.color = Color.GREEN
//        mPaint.style = Paint.Style.STROKE
//        mPaint.strokeWidth=10f
//        val effects = DashPathEffect(floatArrayOf(14f, 12f, 14f, 12f), 5f)
//        mPaint.pathEffect=effects
//    }
//
//    override fun getSize(
//        paint: Paint,
//        text: CharSequence,
//        start: Int,
//        end: Int,
//        fm: Paint.FontMetricsInt?
//    ): Int {
//
//        /*
//         * This method is where we make room for the drawing.
//         * We are passed in a FontMetrics that we can check to see if there is enough space.
//         * If we need to, we can alter these FontMetrics to suit our needs.
//         */
//        if (fm != null) {
////            /*
////             * Everything is measured from the baseline, so the ascent is a negative number,
////             * and the top is an even more negative number.  We are going to make sure that
////             * there is enough room between the top and the ascent line for the graphic.
////             */
//            if (-fm.top + fm.ascent < mHeight) {
//                // if there is not enough room, "raise" the top
//                fm.top = fm.ascent - mHeight
//            }
//        }
//
//        /*
//         * the number returned is actually the width of the span.
//         * you will want to make sure the span is wide enough for your graphic.
//         */
//        textWidth = ceil(paint.measureText(text, start, end).toDouble()).toInt()
//
//        return textWidth
////        return ceil(paint.measureText(text, start, end)).toInt()
//    }
//
//    override fun draw(
//        canvas: Canvas,
//        text: CharSequence,
//        start: Int,
//        end: Int,
//        x: Float,
//        top: Int,
//        y: Int,
//        bottom: Int,
//        paint: Paint
//    ) {
//        Log.d(TAG, "draw(), x = $x, top = $top, y = $y, bottom = $bottom")
//        canvas.translate(1f , 1.5f)
//        // first thing we do is draw the text that is not drawn because it is being "replaced"
//        // you may have to adjust x if the graphic is wider and you want to center-align
//        canvas.drawText(text, start, end, x, y.toFloat(), paint)
//
////        // calculate an offset to center the shape
//
//
////        mPath.moveTo(0f, 0f)
////        mPath.lineTo(mWidth.toFloat(), mHeight.toFloat())
////        mPath.lineTo(0f, mHeight.toFloat())
////        mPath.close()
//
//        val h=mHeight.toFloat()
//        val w=textWidth.toFloat()
//        mPath = Path()
//        mPath.moveTo(0f, h+0.2f*h)
//        mPath.lineTo(w, mHeight.toFloat())
//        mPath.close()
//
//        // we set the bounds relative to upper left corner of the span
//
//
//
//        canvas.drawPath(mPath, mPaint)
////        canvas.translate(0f , 10f)
////        canvas.translate(x , (top).toFloat()*0.2f)
//
////        if (!isLengthIsCached)
////             spanLength = paint.measureText(textWidth)
////                val path = Path()
////        path.moveTo(x, y.toFloat())
////        path.lineTo(x + textWidth, y.toFloat())
////        canvas.drawPath(path, paint)
//
//    }
//
//    companion object {
//
//        private val TAG = "PathOverSpan"
//    }
//}
//
