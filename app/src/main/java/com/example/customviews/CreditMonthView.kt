package com.example.customviews

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet

class CreditMonthView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attributeSet, defStyleAttr) {


    init {
        val v = inflate(context,R.layout.credit_month_view, this)
    }


}