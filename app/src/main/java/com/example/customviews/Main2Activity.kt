package com.example.customviews

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import kotlinx.android.synthetic.main.activity_main2.*

class Main2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        changeView.min="-50а ₸"

        changeView.max="99_000_000"

        changeView.step="10000ggg"

        changeView.amount="  50 000"

        val adapter=Test3Adapter()
        testRv.adapter=adapter

        changeView.creditAmountChangeCallback={
            val items= listOf(it,it*2,it*3,it*4,it*5,it*6,it,it*2,it*3,it*4,it*5,it*6,it,it*2,it*3,it*4,it*5,it*6,it,it*2,it*3,it*4,it*5,it*6,it,it*2,it*3,it*4,it*5,it*6)
            adapter.updateItems(items)
        }






    }




}
