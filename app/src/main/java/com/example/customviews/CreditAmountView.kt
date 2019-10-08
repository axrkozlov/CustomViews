package com.example.customviews

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.widget.SeekBar
import androidx.constraintlayout.widget.ConstraintLayout
import android.graphics.PorterDuff
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.TextViewCompat
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.credit_amount_view.view.*
import timber.log.Timber
import java.lang.NumberFormatException


class CreditAmountView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attributeSet, defStyleAttr) {

    val creditAmountET: TextInputEditText by lazy { inputLayout.creditAmountET }
    var creditAmountChangeCallback: ((Long) -> Unit)? = null

    private var isUpdatedFromSeekBar = false
    private var isUpdatedFromEditText = false
    private var hasError=false
    var seekBarMaxPercent: Int = 1000


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
            setSeekBarToMiddle()
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

        }
    private var maxLong: Long = 0
        private set(value) {
            field = value
            if (value < 0) field = 0
        }

    private var amountLong: Long = 0
        set(value) {
            field = checkedAmount(value)
            creditAmountChangeCallback?.invoke(field)
            if (!isUpdatedFromEditText) updatecreditAmountET(field)
            if (!isUpdatedFromSeekBar) updateSeekBar(value)
        }

    private var stepLong: Long = 1L
        set(value) {
            if (value <= 0L) field = 1L
            field = value
            setSeekBarToMiddle()
        }


    private var animator: ValueAnimator? = null

    init {
        inflate(context, R.layout.credit_amount_view, this)
        val attrs = context.theme.obtainStyledAttributes(
            attributeSet,
            R.styleable.CreditAmountView,
            0, 0
        )
        if (attrs.hasValue(R.styleable.CreditAmountView_min)) {
            min = attrs.getString(R.styleable.CreditAmountView_min) ?: ""
        }
        if (attrs.hasValue(R.styleable.CreditAmountView_max)) {
            max = attrs.getString(R.styleable.CreditAmountView_max) ?: ""
        }
        if (attrs.hasValue(R.styleable.CreditAmountView_amount)) {
            amount = attrs.getString(R.styleable.CreditAmountView_amount) ?: ""
        }
        if (attrs.hasValue(R.styleable.CreditAmountView_seekStep)) {
            step = attrs.getString(R.styleable.CreditAmountView_seekStep) ?: ""
        }
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
                isUpdatedFromEditText = true
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
        isUpdatedFromEditText = false
    }

    private fun parseNumber(value: String): Long {
        val stringWithOnlyDigits = value.filter { it.isDigit() }
        if (stringWithOnlyDigits.isEmpty()) return 0

        return try {
            stringWithOnlyDigits.toLong()
        } catch (e: NumberFormatException) {
            Timber.e("parseNumber, value: $value")
            maxLong
        }
    }

    private fun setSeekBarToMiddle() {
        if (amount.isNotEmpty()) return
        if (minLong==maxLong) return
        amountLong = (minLong + maxLong) / 2 / stepLong * stepLong
    }

    private fun updatecreditAmountET(value: Long) {
        if (isUpdatedFromEditText) {
            isUpdatedFromEditText = false
            return
        }
        creditAmountET.setText(value.toString())
    }

    private fun updateSeekBar(value: Long) {
        var percentVal: Int = ((value - minLong) * seekBarMaxPercent / (maxLong - minLong)).toInt()
        if (percentVal > seekBarMaxPercent) percentVal = seekBarMaxPercent
        if (percentVal < 0) return
        updateAnimatedSeekBarValue(percentVal)
    }

    private fun updateAnimatedSeekBarValue(value: Int) {
        animator?.cancel()
        animator = ValueAnimator.ofInt(seekBar.progress, value)
        animator?.run {
            duration = 500
            interpolator = AccelerateDecelerateInterpolator()
            addUpdateListener {
                seekBar.progress = it.animatedValue as Int
            }
            start()
        }
    }

    private fun checkedAmount(value: Long): Long {
         when {
            value < minLong -> {
                showError()
                return  minLong
            }
            value > maxLong -> {
                showError()
                return  maxLong
            }
            else -> {
                hideError()
                var newValue=value / stepLong * stepLong
                if (newValue<minLong) newValue=minLong
                if (newValue>maxLong) newValue=maxLong
                return newValue


            }
        }
    }

    private fun showError() {
        if (hasError) return
        inputLayout.boxStrokeColor =
            ContextCompat.getColor(context, R.color.creditViewInputLayoutError)
        inputLayout.setHintTextAppearance(R.style.HintErrorStyle)
        Toast.makeText(
            context,
            context.getString(R.string.creditAmountError, min, max),
            Toast.LENGTH_LONG
        ).show()
        hasError=true
//        if (creditAmountET.text!=null && creditAmountET.text.toString().isNotEmpty()) inputLayout.hint=context.getString(R.string.titleCreditAmount).plus(context.getString(R.string.titleCreditAmountUnavailable))
    }

    private fun hideError() {
        inputLayout.boxStrokeColor = ContextCompat.getColor(context, R.color.creditViewInputLayout)
        inputLayout.setHintTextAppearance(R.style.HintStyle)
        hasError=false
//        inputLayout.hint=context.getString(R.string.titleCreditAmount)
    }


}
