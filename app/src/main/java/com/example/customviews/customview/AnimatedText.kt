package com.example.customviews.customview

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import android.graphics.Shader
import androidx.core.content.ContextCompat
import android.graphics.LinearGradient
import android.util.Log
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
        val pf = progress.toFloat() / 1000
        val animWidth=50
        val k0=-animWidth*(1-pf)
        val k1=animWidth*(pf)

        val x0 = pf * width + k0
        val x1 = pf * width + k1
        Log.i("TAG" , "width=$width, k0=$k0, k1=$k1, x0=$x0, x1=$x1")

        val p1 = 0f  //+ 0.9f * pf.pow(5f)
        val p2 = pf * 0.1f //+ 0.9f * pf * pf.pow(5f)
        val p3 = pf * 0.3f //+ 0.9f * pf * pf.pow(5f)
        val p4 = pf * 0.8f //+ 0.2f * pf.pow(5f)
        val p5 = 1f  +0.2f //+ 0.1f * pf.pow(2f)

        val gradient = LinearGradient(
            x0, 0f, x1, 10f,
            intArrayOf(
                ContextCompat.getColor(context, R.color.colorStart),
//                ContextCompat.getColor(context, R.color.colorMiddle1),
//                ContextCompat.getColor(context, R.color.colorMiddle2),
//                ContextCompat.getColor(context, R.color.colorMiddle3),
                ContextCompat.getColor(context, R.color.colorEnd)
            ),
            floatArrayOf(
                p1,
//                p2,
//                p3,
//                p4,
                p5),
            Shader.TileMode.CLAMP
        )
        val i: Int = ContextCompat.getColor(context, R.color.colorStart)
        paint.shader = gradient


        invalidate()
    }

}