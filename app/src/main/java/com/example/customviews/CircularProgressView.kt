package com.example.customviews

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.annotation.FloatRange

class CircularProgressView
@JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View (context, attributeSet, defStyleAttr){

    private val rect = RectF()
    private val startAngle = -90f
    private val maxAngle = 360f
    private val maxProgress = 100
    private var diameter = 0f
    private var angle = 0f
    var progressColor: Int = Color.BLUE
        set(value){
            field=value
            update()
        }

    var progressBackgroundColor: Int = Color.WHITE
        set(value){
            field=value
            invalidate()
        }

    var progressStrokeWidth: Float=10.0f
        set(value){
            field=value
            updateRect()
        }

    var rounded: Boolean = true
        set(value){
            field=value
            update()
        }

    var progress: Float =0.0f
    set(@FloatRange(from = 0.0, to = 100.0) value) {
        field=value
        update()
    }

    private val progressPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
    }

    private val backgroundPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
    }

    private fun update(){
        progressPaint.strokeCap = if (rounded) Paint.Cap.ROUND else Paint.Cap.BUTT
        progressPaint.color=progressColor
        progressPaint.strokeWidth=progressStrokeWidth
        backgroundPaint.color=progressBackgroundColor
        backgroundPaint.strokeWidth=progressStrokeWidth
        angle = calculateAngle(progress)
        invalidate()

    }

    init {
        val attrs = context.theme.obtainStyledAttributes(
            attributeSet,
            R.styleable.CircularProgressView,
            0, 0
        )

        if (attrs.hasValue(R.styleable.CircularProgressView_progress)) {
            progress = attrs.getFloat(R.styleable.CircularProgressView_progress,0.0f)
        }

        if (attrs.hasValue(R.styleable.CircularProgressView_progressColor)) {
            progressColor = attrs.getColor(R.styleable.CircularProgressView_progressColor,0)
        }
        if (attrs.hasValue(R.styleable.CircularProgressView_progressBackgroundColor)) {
            progressBackgroundColor = attrs.getColor(R.styleable.CircularProgressView_progressBackgroundColor,0)
        }
        if (attrs.hasValue(R.styleable.CircularProgressView_progressStrokeWidth)) {
            progressStrokeWidth = attrs.getDimension(R.styleable.CircularProgressView_progressStrokeWidth,10.0f)
        }
        if (attrs.hasValue(R.styleable.CircularProgressView_roundedEnds)) {
            rounded = attrs.getBoolean(R.styleable.CircularProgressView_roundedEnds,true)
        }
        update()
    }

    override fun onDraw(canvas: Canvas) {
        drawCircle(maxAngle, canvas, backgroundPaint)
        drawCircle(angle, canvas, progressPaint)
    }

    override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
        diameter = Math.min(width, height).toFloat()
        updateRect()
    }

    private fun updateRect() {
        val strokeWidth = backgroundPaint.strokeWidth/2
        rect.set(strokeWidth, strokeWidth, diameter - strokeWidth, diameter - strokeWidth)
    }

    private fun drawCircle(angle: Float, canvas: Canvas, paint: Paint) {
        canvas.drawArc(rect, startAngle, angle, false, paint)
    }

    private fun calculateAngle(progress: Float) = maxAngle / maxProgress * progress

}