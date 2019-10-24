package com.example.customviews

import android.animation.ObjectAnimator
import android.animation.StateListAnimator
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import android.view.View

import com.google.android.material.appbar.AppBarLayout

import android.view.animation.AnimationUtils.loadAnimation
import kotlinx.android.synthetic.main.activity_main4.*
import kotlin.math.*


class Main4Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)

        val adapter = Test3Adapter()
        testRv.adapter = adapter
        val items = mutableListOf<Long>()
        for (i in 1..100L) {
            items.add(i)
        }
        adapter.updateItems(items)
        
//        mainAppbar.setLiftable(false)
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            val stateListAnimator = StateListAnimator()
//            stateListAnimator.addState(
//                IntArray(0),
//                ObjectAnimator.ofFloat(mainAppbar, "elevation", 0.1f)
//            )
//            mainAppbar.setStateListAnimator(stateListAnimator)
//        }

        mainAppbar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, offset ->
            val maxScroll = appBarLayout.getTotalScrollRange()
            val percentage = abs(offset).toFloat() / maxScroll.toFloat()
//            if (percentage >= 0.01) {
//                toolbar.elevation=3f
//            } else toolbar.elevation=0f
//            handleAlphaOnTitle(percentage)
            handleToolbarTitleVisibility(percentage)

        })

    }

    private fun handleToolbarTitleVisibility(percentage: Float) {
        val alpha = 1.0f - 3 * percentage.pow(3f)
        bigTitle.alpha = alpha
        titleTv.alpha = 2 * (percentage).pow(3)
        titleTv.translationY = 10f - 10f * percentage
        val elevation = min(percentage * 25, 5f)

        Log.i("TAG", "$elevation")

        var translation=6f * percentage.pow(0.5f)//+7f* percentage.pow(10f)
        if (percentage>0.8) translation=6-6*percentage.pow(2f)
        toolbar.translationZ = translation


        mainAppbar.translationZ = 8f * percentage.pow(20f)

    }
    //without line
//    private fun handleToolbarTitleVisibility(percentage: Float) {
//        val alpha = 1.0f - 5 * percentage.pow(1f)
//        val scale = 1.0f - 1 * percentage.pow(1f)
//        bigTitle.alpha = alpha
//
//        bigTitle.pivotX=100f
//        bigTitle.pivotY=0f
//        bigTitle.scaleX=scale
//        bigTitle.scaleY=scale
//
//        titleTv.alpha = 2 * (percentage).pow(3)
//        titleTv.translationY = 20f - 20f * percentage
//        val elevation = min(percentage * 25, 5f)
//
//        Log.i("TAG", "$elevation")
////        toolbar.translationZ = 3f * percentage.pow(0.5f)//+7f* percentage.pow(10f)
//        mainAppbar.translationZ = 15f * percentage.pow(10f)
//    }

//    private val PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR = 0.1f
//    private val PERCENTAGE_TO_HIDE_TITLE_AT_TOOLBAR = 0.1f
//    private val PERCENTAGE_TO_SHOW_TITLE_DETAILS = 0.01f
//    private val PERCENTAGE_TO_HIDE_TITLE_DETAILS = 0.01f
//    private val ALPHA_ANIMATIONS_DURATION = 200
//    private var mIsTheTitleVisible = false
//    private var mIsTheBigTitleVisible = false
//    private var mIsTheTitleContainerVisible = true
//        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {
//
//                startToolbarAlphaAnimation(titleTv, ALPHA_ANIMATIONS_DURATION.toLong(), View.VISIBLE)
//                mIsTheTitleVisible = true
//            }
//
//        } else if (percentage <= PERCENTAGE_TO_HIDE_TITLE_AT_TOOLBAR) {
//
//            if (mIsTheTitleVisible) {
//                startToolbarAlphaAnimation(titleTv, ALPHA_ANIMATIONS_DURATION.toLong(), View.INVISIBLE)
//
//                mIsTheTitleVisible = false
//            }
//        }

//        toolbar.elevation = elevation
////
//        if (alpha <= 0.1) {
//            if (!mIsTheTitleVisible) {
//                startToolbarAlphaAnimation(
//                    titleTv,
//                    ALPHA_ANIMATIONS_DURATION.toLong(),
//                    View.VISIBLE
//                )
//                mIsTheTitleVisible = true
//            }
//        } else if (alpha >= 0.1) {
//
//            if (mIsTheTitleVisible) {
//                startToolbarAlphaAnimation(
//                    titleTv,
//                    ALPHA_ANIMATIONS_DURATION.toLong(),
//                    View.INVISIBLE
//                )
//
//                mIsTheTitleVisible = false
//            }
//        }


//        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_DETAILS) {
//            if (!mIsTheBigTitleVisible) {
//                startTitleAlphaAnimation(bigTitle, ALPHA_ANIMATIONS_DURATION.toLong(), View.INVISIBLE)
//                mIsTheBigTitleVisible = true
//            }
//
//        } else if (percentage <= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
//
//            if (mIsTheBigTitleVisible) {
//                startTitleAlphaAnimation(bigTitle, ALPHA_ANIMATIONS_DURATION.toLong(), View.VISIBLE)
//                mIsTheBigTitleVisible = false
//            }
//        }
//    }

//    private fun handleAlphaOnTitle(percentage: Float) {
//        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
//            if (mIsTheTitleContainerVisible) {
//                //                startToolbarAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
//                mIsTheTitleContainerVisible = false
//            }
//
//        } else {
//
//            if (!mIsTheTitleContainerVisible) {
//                //                startToolbarAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
//                mIsTheTitleContainerVisible = true
//            }
//        }
//    }
//
//    fun startToolbarAlphaAnimation(v: View, duration: Long, visibility: Int) {
//
//        val aniSlide = if (visibility == View.VISIBLE)
//            loadAnimation(applicationContext, R.anim.slide_up)
//        else
//            loadAnimation(applicationContext, R.anim.slide_down)
//
//        v.startAnimation(aniSlide)
//        v.visibility = visibility
//    }
//
//    fun startTitleAlphaAnimation(v: View, duration: Long, visibility: Int) {
//
//        val ani = if (visibility == View.VISIBLE)
//            loadAnimation(applicationContext, R.anim.fade_in)
//        else
//            loadAnimation(applicationContext, R.anim.fade_out)
//
//        v.startAnimation(ani)
//        v.visibility = visibility
//    }
}
