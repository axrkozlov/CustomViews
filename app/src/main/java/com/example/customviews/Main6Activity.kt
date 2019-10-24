package com.example.customviews

import android.animation.ObjectAnimator
import android.animation.StateListAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main6.*
import android.animation.ValueAnimator
import android.content.Intent
import android.os.Build
import android.view.View
import android.view.animation.AccelerateInterpolator

import com.google.android.material.appbar.AppBarLayout

import android.view.animation.AnimationUtils.loadAnimation
import android.view.animation.LinearInterpolator
import android.widget.SeekBar
import com.example.customviews.customview.AnimatedText


class Main6Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main6)

        val adapter=Test3Adapter()
        testRv.adapter=adapter
        val items= mutableListOf<Long>()
        for (i in 1..100L) {
            items.add(i)
        }
        adapter.updateItems(items)
//        mainAppbar.setLiftable(false)

//        appBarLayout.setLifted(false)
//        appBarLayout.setLiftable(false)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            val stateListAnimator = StateListAnimator()
//            stateListAnimator.addState(
//                IntArray(0),
//                ObjectAnimator.ofFloat(appBarLayout, "elevation", 0.1f)
//            )
//            appBarLayout.setStateListAnimator(stateListAnimator)
//        }


    }


}
