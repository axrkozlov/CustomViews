package com.example.customviews

import android.content.Context
import androidx.appcompat.widget.AppCompatEditText
import android.text.Editable
import android.text.InputType
import android.text.TextUtils
import android.text.TextWatcher
import android.text.method.DigitsKeyListener
import android.util.AttributeSet
import java.math.BigDecimal
import kotlin.math.max
import kotlin.math.min

class AmountInputEditText : AppCompatEditText {

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, R.attr.editTextStyle)

    constructor(context: Context) : this(context, null)

    private var currency: String = "KZT"
    private var lastValue: String = ""

//    var oldPos = 0
//    var oldFinalSize = 0

    init {
        inputType = InputType.TYPE_CLASS_NUMBER
        keyListener = DigitsKeyListener.getInstance("0123456789 .,")


        addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//                oldPos = selectionEnd

            }

            override fun onTextChanged(x: CharSequence?, start: Int, before: Int, count: Int) {
                var basePos = selectionEnd
//                val d = when {
//                    basePos < oldPos -> -1
//                    basePos > oldPos -> 1
//                    else -> 0
//                }
                val digitCount = getDigitCountBeforeCursor(x?.toString(), basePos)
                val finalString = x.toString().pretty(currency, truncZeros = lastValue.isNullOrEmpty())
                val newFinalSize = finalString.length

                if (x.toString() != finalString) {
                    lastValue = x.toString()
                    this@AmountInputEditText.setText(finalString, BufferType.EDITABLE)

//                    var finalPos = when {
//                        oldFinalSize == newFinalSize -> basePos
//                        else -> findNewCursorPosition(finalString, digitCount)
//                    }
                    var finalPos = findNewCursorPosition(finalString, digitCount)
                    finalPos = min(newFinalSize - 2, finalPos)
                    finalPos = max(1, finalPos)

                    this@AmountInputEditText.setSelection(finalPos)

                }
//                oldFinalSize = finalString.length
            }

        })
    }

    private fun getDigitCountBeforeCursor(string: String?, cursorPosition: Int): Int {
        if (string == null) return 0
        var digitNumber = 0
        var c = 0
        for (s in string) {
            if (c == cursorPosition) return digitNumber
            if (s.isDigit() || s == ',' || s == '.') {
                digitNumber += 1
                if (c==0&&(s == ',' || s == '.')) digitNumber += 1
            }
            c += 1
        }
        return digitNumber
    }


    private fun findNewCursorPosition(string: String?, digitCount: Int): Int {
        if (string == null) return 0
        var c = 0
        var digit = 0
        for (s in string) {
            if (digit == digitCount) return c
            c += 1
            if (s.isDigit() || s == ',' || s == '.') {
                digit += 1
            }

        }
        return c
    }


    fun setCurrency(currency: String) {
        this.currency = currency
        text = this.text
    }


//                    this@AmountInputEditText.setSelection(position)
//                    this@AmountInputEditText.setSelection(Math.max(0, finalString.length - 2))

//    val position=selectionEnd
////                position.toInt()
//    Timber.i("onTextChanged, x: ${x?.length}")
//
//    Timber.i("onTextChanged, x: ${finalString.length}")


    val sum: Double
        get() {
            val digits =
                this.text?.filter { TextUtils.isDigitsOnly(it.toString()) || it.toString() == "," || it.toString() == "." }
            val digitAsString = digits.toString().replace(" ", "").replace(",", ".")
            return try {
                digitAsString.toDouble()
            } catch (e: Exception) {
                0.0
            }
        }


    val sumAsLong: Long
        get() {
            val digits =
                this.text?.filter { TextUtils.isDigitsOnly(it.toString()) || it.toString() == "," || it.toString() == "." }
            val digitAsString = digits.toString().replace(" ", "").replace(",", ".").toString()
            return try {
                (digitAsString.toDouble() * 100).toLong()
            } catch (e: Exception) {
                0L
            }
        }


    companion object {

        fun sum(s: String?): BigDecimal? {
            if (s == null) {
                return null
            }
            val digits =
                s.filter { TextUtils.isDigitsOnly(it.toString()) || it.toString() == "," || it.toString() == "." }
            val digitAsString = digits.replace(" ", "").replace(",", ".")
            return try {
                digitAsString.toBigDecimal()
            } catch (e: Exception) {
                null
            }
        }

    }
}
