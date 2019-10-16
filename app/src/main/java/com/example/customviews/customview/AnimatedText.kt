package com.example.customviews.customview

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import android.graphics.Shader
import androidx.core.content.ContextCompat
import android.graphics.LinearGradient
import com.example.customviews.R


class AnimatedText @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : TextView(context, attributeSet, defStyleAttr) {


    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }

    fun changeColor(progress: Int) {

        val x0 = progress.toFloat() / 500 * width - 200
        val x1 = progress.toFloat() / 500 * width + 200

        val pf = progress.toFloat() / 1000
        val p1 = pf * 0.0f //+ 0.9f * pf.pow(5f)
        val p2 = pf * 0.1f //+ 0.9f * pf * pf.pow(5f)
        val p3 = pf * 0.3f //+ 0.9f * pf * pf.pow(5f)
        val p4 = pf * 0.8f //+ 0.2f * pf.pow(5f)
        val p5 = pf * 0.9f //+ 0.1f * pf.pow(2f)

        val gradient = LinearGradient(
            x0, 0f, x1, 20f,
            intArrayOf(
                ContextCompat.getColor(context, R.color.colorStart),
                ContextCompat.getColor(context, R.color.colorMiddle1),
                ContextCompat.getColor(context, R.color.colorMiddle2),
                ContextCompat.getColor(context, R.color.colorMiddle3),
                ContextCompat.getColor(context, R.color.colorEnd)
            ),
            floatArrayOf(p1, p2, p3, p4,p5),
            Shader.TileMode.CLAMP
        )
        val i: Int = ContextCompat.getColor(context, R.color.colorStart)
        paint.shader = gradient


        invalidate()
    }

}