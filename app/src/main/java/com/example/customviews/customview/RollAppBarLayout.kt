package com.example.customviews.customview

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.example.customviews.R
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import kotlin.math.abs

class RollAppBarLayout
@JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : AppBarLayout(context, attributeSet) {
    private val switchVisibilityPercentage = 0.6f
    private var toolbar: Toolbar?=null
    private var animatedTitle:AnimatedText?=null
    private val upElevation:Float

    init {
        val attrs = context.theme.obtainStyledAttributes(
            attributeSet,
            R.styleable.RollAppBarLayout,
            0, 0
        )

        upElevation= attrs.getDimensionPixelSize(R.styleable.RollAppBarLayout_toolbar_elevation, 0).toFloat()

        addOnOffsetChangedListener(OnOffsetChangedListener { appBarLayout, offset ->
            val maxScroll = appBarLayout.totalScrollRange
            val percentage = abs(offset).toFloat() / maxScroll.toFloat()
            handleToolbarTitleVisibility(percentage)
        })
    }

    private fun handleToolbarTitleVisibility(percentage: Float) {

        if (!ensureAnimatedTitle()) return

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (percentage >= 0.01) {
                toolbar!!.translationZ= upElevation
            } else toolbar!!.translationZ=0f
        }
//        if (percentage >= 0.01) {
//            toolbar!!.elevation=10f
//        } else toolbar!!.elevation=0f

        if (percentage > switchVisibilityPercentage) {
            animatedTitle!!.switchAnimatedVisibility(View.VISIBLE)

        } else if (percentage < switchVisibilityPercentage) {
            animatedTitle!!.switchAnimatedVisibility(View.INVISIBLE)
        }
    }

    private fun ensureAnimatedTitle() :Boolean{
        if (animatedTitle!=null) return true
        for (i in 0 until childCount){
            val appBarChild=getChildAt(i)
            if (appBarChild is CollapsingToolbarLayout){
                for (j in 0 until appBarChild.childCount){
                    val collapsingLayoutChild=appBarChild.getChildAt(j)
                    if (collapsingLayoutChild is Toolbar) {
                        toolbar=collapsingLayoutChild
                        for (k in 0 until collapsingLayoutChild.childCount){
                            val toolbarChild=collapsingLayoutChild.getChildAt(k)
                            if (toolbarChild is AnimatedText){
                                animatedTitle=toolbarChild
                                return true
                            }
                        }
                    }
                }
            }
        }
        return false
    }

}