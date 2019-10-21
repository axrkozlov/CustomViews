package com.example.customviews.customview

import android.animation.TimeInterpolator
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import android.graphics.Shader
import androidx.core.content.ContextCompat
import android.graphics.LinearGradient
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator
import com.example.customviews.R


class AnimatedText @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : TextView(context, attributeSet, defStyleAttr) {
    var animator: ValueAnimator? = null
    private var oldvisibility = 0

//    override fun setVisibility(visibility: Int) {
//        if (oldvisibility==visibility) return
//        startAnimatedVisibilityChange(visibility == View.VISIBLE,visibility)
//       super.setVisibility(visibility)
//        oldvisibility=visibility
//    }


    fun startAnimatedVisibilityChange(isShown: Boolean,visibility: Int) {

        val start = if (isShown) 0 else 100
        val end = if (!isShown) 0 else 100

        val interpolator: TimeInterpolator = if (!isShown)
            AccelerateInterpolator(1.0f)
        else LinearInterpolator()

        animator?.cancel()
        animator = ValueAnimator.ofInt(start, end)
        animator?.duration = if (!isShown) 350 else 150


        animator?.interpolator = interpolator
        animator?.addUpdateListener {
            val value=it.animatedValue as Int
            changeColor(value)
//            if (value==100)     this.visibility=visibility
        }
        animator?.start()

    }

    fun changeColor(progress: Int) {
        val pf = progress.toFloat() / 100
        val animWidth = 50
        val k0 = -animWidth * (1 - pf)
        val k1 = animWidth * (pf)
        val x0 = pf * width + k0
        val x1 = pf * width + k1

        val gradient = LinearGradient(
            x0, 0f, x1, 20f,
            intArrayOf(
                ContextCompat.getColor(context, R.color.colorStart),
                ContextCompat.getColor(context, R.color.colorEnd)
            ),
            floatArrayOf(0f, 1f),
            Shader.TileMode.CLAMP
        )
        paint.shader = gradient
        invalidate()
    }

}

//
//    fun changeColor(progress: Int) {
//        val pf = progress.toFloat() / 1000
//        val animWidth = 50
//        val k0 = -animWidth * (1 - pf)
//        val k1 = animWidth * (pf)
//
//        val x0 = pf * width + k0
//        val x1 = pf * width + k1
//        Log.i("TAG", "width=$width, k0=$k0, k1=$k1, x0=$x0, x1=$x1")
//
//        val p1 = 0f  //+ 0.9f * pf.pow(5f)
//        val p2 = pf * 0.1f //+ 0.9f * pf * pf.pow(5f)
//        val p3 = pf * 0.3f //+ 0.9f * pf * pf.pow(5f)
//        val p4 = pf * 0.8f //+ 0.2f * pf.pow(5f)
//        val p5 = 1f + 0.2f //+ 0.1f * pf.pow(2f)
//
//        val gradient = LinearGradient(
//            x0, 0f, x1, 10f,
//            intArrayOf(
//                ContextCompat.getColor(context, R.color.colorStart),
////                ContextCompat.getColor(context, R.color.colorMiddle1),
////                ContextCompat.getColor(context, R.color.colorMiddle2),
////                ContextCompat.getColor(context, R.color.colorMiddle3),
//                ContextCompat.getColor(context, R.color.colorEnd)
//            ),
//            floatArrayOf(
//                p1,
////                p2,
////                p3,
////                p4,
//                p5
//            ),
//            Shader.TileMode.CLAMP
//        )
//        val i: Int = ContextCompat.getColor(context, R.color.colorStart)
//        paint.shader = gradient
//
//
//        invalidate()
//    }