package com.example.customviews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main3.*
import android.animation.ObjectAnimator
import android.animation.StateListAnimator
import android.animation.ValueAnimator
import android.content.Intent
import android.os.Build

import android.view.View
import android.view.animation.AccelerateInterpolator

import com.google.android.material.appbar.AppBarLayout

import android.view.animation.AnimationUtils.loadAnimation
import android.widget.SeekBar
import com.example.customviews.customview.AnimatedText


class Main3Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        val adapter=Test3Adapter()
        testRv.adapter=adapter
        val items= mutableListOf<Long>()
        for (i in 1..100L) {
            items.add(i)
        }
        adapter.updateItems(items)
//        mainAppbar.setLiftable(false)



        initialSeekBar()

        val navigate=Call{val i= Intent(this, Main5Activity::class.java)
            startActivity(i)}

        nextBt.setOnClickListener {
            navigate.once()
//            val i= Intent(this, Main5Activity::class.java)
//            startActivity(i)
        }



//        mainAppbar.bringToFront()
//        mainAppbar.elevation=0.1f
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            val stateListAnimator = StateListAnimator()
//            stateListAnimator.addState(
//                IntArray(0),
//                ObjectAnimator.ofFloat(mainAppbar, "elevation", 10f)
//            )
//            mainAppbar.setStateListAnimator(stateListAnimator)
//        }

        mainAppbar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, offset ->
            val maxScroll = appBarLayout.getTotalScrollRange()
            val percentage = Math.abs(offset).toFloat() / maxScroll.toFloat()
            if (percentage >= 0.01) {
                toolbar.elevation=10f
            } else toolbar.elevation=0f
//            handleAlphaOnTitle(percentage)
            handleToolbarTitleVisibility(percentage)

        })

//        mainAppbar.setLifted(false)
//        mainAppbar.setLiftOnScroll(true)
    }

    private fun initialSeekBar() {
        seekBar.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                changeTextColor(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })
    }

    fun changeTextColor(progress:Int){
        val p=progress
        animtext.changeColor(progress)
    }

    class Call(private val callback: (() -> Unit)?){
        var calledOnce=false
        fun once(){
            if (calledOnce) return
            callback?.invoke()
            calledOnce=true
        }
    }


    private val PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR = 0.6f
    private val PERCENTAGE_TO_HIDE_TITLE_AT_TOOLBAR = 0.6f
    private val PERCENTAGE_TO_HIDE_TITLE_DETAILS = 0.3f
    private val ALPHA_ANIMATIONS_DURATION = 200
    private var mIsTheTitleVisible = false
    private var mIsTheTitleContainerVisible = true

    private fun handleToolbarTitleVisibility(percentage: Float) {


        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {
            if (!mIsTheTitleVisible) {
                startAlphaAnimation(titleTv, ALPHA_ANIMATIONS_DURATION.toLong(), View.VISIBLE)
//                startAlpha1Animation(animtitle, ALPHA_ANIMATIONS_DURATION.toLong(), View.VISIBLE)
                mIsTheTitleVisible = true
            }

        } else if (percentage <= PERCENTAGE_TO_HIDE_TITLE_AT_TOOLBAR) {

            if (mIsTheTitleVisible) {
                startAlphaAnimation(titleTv, ALPHA_ANIMATIONS_DURATION.toLong(), View.INVISIBLE)
//                startAlpha1Animation(animtitle, ALPHA_ANIMATIONS_DURATION.toLong(), View.INVISIBLE)
                mIsTheTitleVisible = false
            }
        }
    }

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

    fun startAlphaAnimation(v: View, duration: Long, visibility: Int) {



//        val alphaAnimation = if (visibility == View.VISIBLE)
//            AlphaAnimation(0f, 1f)
//        else
//            AlphaAnimation(1f, 0f)
//
//        alphaAnimation.duration = duration
//        alphaAnimation.fillAfter = true
//        v.startAnimation(alphaAnimation)
//
        val aniSlide = if (visibility == View.VISIBLE)
            loadAnimation(applicationContext, R.anim.slide_up)
        else
            loadAnimation(applicationContext, R.anim.slide_down)

        v.startAnimation(aniSlide)
        v.visibility=visibility
    }

    fun startAlpha1Animation(v: AnimatedText, duration: Long, visibility: Int) {

val start=if (visibility == View.VISIBLE) 0 else 1000
        val end=if (visibility == View.INVISIBLE) 0 else 1000
        val animator=ValueAnimator.ofInt(start,end)
        animator.duration=800
        animator.interpolator=AccelerateInterpolator(0.5f)
        animator.addUpdateListener {
            v.changeColor(it.animatedValue as Int)
        }
        animator.start()



//        val alphaAnimation = if (visibility == View.VISIBLE)
//            AlphaAnimation(0f, 1f)
//        else
//            AlphaAnimation(1f, 0f)
//
//        alphaAnimation.duration = duration
//        alphaAnimation.fillAfter = true
//        v.startAnimation(alphaAnimation)
//
//        val aniSlide = if (visibility == View.VISIBLE)
//            loadAnimation(applicationContext, R.anim.slide_up)
//        else
//            loadAnimation(applicationContext, R.anim.slide_down)
//
//        v.startAnimation(aniSlide)
//        v.visibility=visibility
    }
}
