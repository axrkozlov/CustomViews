package com.example.customviews

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.widget.SeekBar
import androidx.constraintlayout.widget.ConstraintLayout
import android.graphics.PorterDuff
import android.text.Editable
import android.text.TextWatcher
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.content.ContextCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.credit_amount_view.view.*



class CreditAmountView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attributeSet, defStyleAttr) {

    val creditAmountET:TextInputEditText by lazy { inputLayout.creditAmountET }

    private var isUpdatedFromSeekBar = false
    private var isUpdatedFromEditText = false
    var seekBarMaxPercent : Int = 1000


    var min = ""
        set(value) {
            field = value
            minLong = parseNumber(value)
            minTV.text = field
        }


    var max = ""
        set(value) {
            field = value
            maxLong = parseNumber(value)
            maxTV.text = field
        }

    var amount = ""
        set(value) {
            field = value
            amountLong = parseNumber(value)
            creditAmountET.setText(amountLong.toString())
        }

    var step = ""
        set(value) {
            field = value
            stepLong = parseNumber(value)
        }

    private var minLong: Long = 0
        private set(value) {
            field = value
            if (value < 0) field = 0
            adjustSeekBar()
        }
    private var maxLong: Long = 0
        private set(value) {
            field = value
            if (value < 0) field = 0
            adjustSeekBar()
        }

    private var amountLong: Long = 0
        set(value) {
            if (field == value) return
            field = checkedAmount(value)
            if (!isUpdatedFromEditText) updatecreditAmountET(field)
            if (!isUpdatedFromSeekBar) updateSeekBar(value)
        }

    private var stepLong:Long=1L
    set(value) {
        if (value<=0L) field=1L
        field=value
        adjustSeekBar()
    }


    private var animator: ValueAnimator? = null

    init {
        inflate(context, R.layout.credit_amount_view, this)
        setupComponents()
    }

    private fun setupComponents() {
        seekBar.max = seekBarMaxPercent
        seekBar.thumb.setColorFilter(
            ContextCompat.getColor(context, R.color.textColorLink),
            PorterDuff.Mode.SRC_IN
        )

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStopTrackingTouch(p0: SeekBar?) {
                isUpdatedFromSeekBar = false
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                isUpdatedFromSeekBar = true
                creditAmountET.requestFocus()
            }

            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                if (!isUpdatedFromSeekBar) return
                updateValueFromSeekbar(p1)
            }

        })

        creditAmountET.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (isUpdatedFromSeekBar) return
                isUpdatedFromEditText=true
                updateValueFromEditText(p0.toString())
            }

        })
    }

    fun updateValueFromSeekbar(percent: Int) {
        var newValue = (maxLong - minLong) * percent / seekBarMaxPercent + minLong
        amountLong = newValue
    }


    fun updateValueFromEditText(value: String) {
        amountLong = parseNumber(value)
        isUpdatedFromEditText=false
    }

    private fun parseNumber(value: String): Long {
        val stringWithOnlyDigits = value.filter { it.isDigit() }
        if (stringWithOnlyDigits.isEmpty()) return 0
        return stringWithOnlyDigits.toLong()
    }

    private fun adjustSeekBar(){
        amountLong=(minLong + maxLong) / 2 / stepLong * stepLong
    }

    private fun updatecreditAmountET(value: Long) {
        if (isUpdatedFromEditText) {
            isUpdatedFromEditText = false
            return
        }
        creditAmountET.setText(value.toString())
    }

    private fun updateSeekBar(value: Long) {
        var percentVal = (value - minLong) * seekBarMaxPercent / (maxLong - minLong)
        updateAnimatedSeekBarValue(percentVal.toInt())
    }

    private fun updateAnimatedSeekBarValue(value: Int) {
        animator = ValueAnimator.ofInt(seekBar.progress, value)
        animator?.run {
            cancel()
            duration = 500
            interpolator = AccelerateDecelerateInterpolator()
            addUpdateListener {
                seekBar.progress = it.animatedValue as Int
            }
            start()
        }
    }

    private fun checkedAmount(value: Long): Long {
        val newValue = value / stepLong * stepLong
        return when {
            newValue < minLong -> minLong
            newValue > maxLong -> maxLong
            else -> newValue
        }
    }


}
