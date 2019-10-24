package com.example.customviews.customview

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.TimeInterpolator
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import android.graphics.Shader
import androidx.core.content.ContextCompat
import android.graphics.LinearGradient
import android.util.Log
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator
import com.example.customviews.R
import kotlin.math.min


class AnimatedText @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : TextView(context, attributeSet, defStyleAttr) {

    var animator: ValueAnimator? = null
    var newVisibility = VISIBLE
    var startProcess = -1
    fun switchAnimatedVisibility(visibility: Int) {
        if (visibility == newVisibility) return
        newVisibility = visibility

        if (startProcess < 0) startProcess = if (visibility == VISIBLE) 0 else 100
        val endProgress = if (visibility == INVISIBLE) 0 else 100

        val interpolator: TimeInterpolator = if (visibility == VISIBLE)
            AccelerateInterpolator(0.5f)
        else LinearInterpolator()

        animator?.removeAllListeners()
        animator?.cancel()
        animator = ValueAnimator.ofInt(startProcess, endProgress)

        animator?.duration = if (visibility == VISIBLE) 250 else 100
        animator?.interpolator = interpolator



        animator?.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {}

            override fun onAnimationEnd(animation: Animator?) {
                this@AnimatedText.visibility = visibility
            }

            override fun onAnimationCancel(animation: Animator?) {}

            override fun onAnimationStart(animation: Animator?) {
                if (visibility == VISIBLE) this@AnimatedText.visibility = visibility
            }
        })

        animator?.start()
        animator?.addUpdateListener {
            val value = it.animatedValue as Int
            startProcess = value
            changeColor(value)
        }

    }
    var gradient:LinearGradient? =null

     fun changeColor(progress: Int) {
        paint.shader = gradient
        val pf = progress.toFloat() / 100
        val animWidth = 40
        val k0 = -animWidth * (1 - pf)
        val k1 = animWidth * (pf)
        val x0 = pf * width + k0
        val x1 = pf * width + k1
        gradient= LinearGradient(
            x0, 10f, x1, 0f,
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