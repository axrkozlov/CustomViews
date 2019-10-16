package com.example.customviews.customview

import android.content.Context
import androidx.appcompat.widget.AppCompatEditText
import android.text.Editable
import android.text.InputType
import android.text.TextUtils
import android.text.TextWatcher
import android.text.method.DigitsKeyListener
import android.util.AttributeSet
import android.util.Log
import com.example.customviews.R
import kotlin.math.max
import kotlin.math.min


class PhoneInputEditText : AppCompatEditText {

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, R.attr.editTextStyle)

    constructor(context: Context) : this(context, null)

    private var isRunning = false
    private var isDeleting = false

    private var lastDigitsCount = 0

    init {
        inputType = InputType.TYPE_CLASS_NUMBER
        keyListener = DigitsKeyListener.getInstance("0123456789 ().-_+")
        setText(StringBuilder("+7 (7__) ___-__-__"))

        addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(x: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                isDeleting = count > after
            }

            override fun onTextChanged(x: CharSequence?, start: Int, before: Int, count: Int) {
                if (isRunning) {
                    return
                }
                isRunning = true
                if (x != null && x.length > 0) {
                    val basePos = selectionEnd
                    val digitsOnly = x.filter { TextUtils.isDigitsOnly(it.toString()) }
                    val digitCount = getDigitCountBeforeCursor(x.toString(), basePos)

                    lastDigitsCount = digitsOnly.length

                    val array = Array(11) { '_' }

                    for (i in array.indices) {
                        if (i >= digitsOnly.length) break
                        array[i] = digitsOnly[i]
                    }

                    val finalString = String.format("+%c (%c%c%c) %c%c%c-%c%c-%c%c", *array)
                    setText(finalString)

                    var finalPos = findNewCursorPosition(finalString, digitCount)
                    finalPos = min(length(), finalPos)
                    this@PhoneInputEditText.setSelection(finalPos)
//                     digitsOnly = digitsOnly.replace(Regex("^.."),"")
//
//                    if (isDeleting && digitsOnly.length == lastDigitsCount) {
//                        if (digitsOnly.isNotEmpty()) {
//                            digitsOnly = digitsOnly.substring(1, digitsOnly.length - 1)
//                        }
//                    }
//                    var skipSymbols = 0
//                    var wrongSymbols = mutableListOf<Char>()
//
//
//                    for ((i, v) in digitsOnly.withIndex()) {
//
//                        if (v != '7')
//                        if (!isFirstSevenFounded ) {
//                            if (v != '7') {
//                                wrongSymbols.add(v)
//                                continue
//                            }
//                                    v != '7'
//                        }
//                        digitsList.add(v)
//                    }
//                    digitsList.addAll(2,wrongSymbols)


//                    for (i in 0..10) {
//                        if (i<digitsOnly.length){
//                            var d=digitsOnly[i]
//                            if (i < 2) {
//                                if (d != '7') {
//                                    digitsList.add('7')
//                                    if (!isDeleting) {
//                                        wrongSymbols.add(d)
//                                        skipSymbols++
//                                        continue
//                                    }
//                                }
//                            } else {
//                                if (skipSymbols>0) {
//                                    skipSymbols--
//                                    if (d=='7') continue
//                                }
//                                digitsList.add(d)
//                                digitsList.addAll(wrongSymbols)
//                                wrongSymbols.clear()
//                            }
//                        }


//                        {
//                            if (digitsList.size>2) digitsList.removeAt(i)
//                            else digitsList[i]='7'
//                            digitsList.add(d)
//                        } else break
//                    }
//                    var added:Char?=null
//                    val digitsList = mutableListOf<Char>()
//                    for ((i, v) in digitsOnly.withIndex()) {
//                        if (i >= digitsOnly.length) break
//                        val d = digitsOnly[i]
//
//
//                        if (i<2 && d!='7') {
//                            added=v
//                            continue
//                        }
//                            digitsList.add(v)
//
//                    }
//                    added?.let { digitsList.add(2,added) }
//                    Log.i("TAG", "$digitsList")

//                    Log.i("TAG", "$digitsOnly")

//                    x.replace(0, x.length, )

//                    val finalString = x.replace(0, x.length, "+_ (___) ___-__-__ ")

//                    for ((i, c) in digitsOnly.withIndex()) {
//                        if (i == 0) {
//                            finalString.replace(1, 2, c.toString())
//                        }
//                        if (i in 1..3) {
//                            finalString.replace(3 + i, 4 + i, c.toString())
//                        }
//                        if (i in 4..6) {
//                            finalString.replace(5 + i, 6 + i, c.toString())
//                        }
//                        if (i in 7..8) {
//                            finalString.replace(6 + i, 7 + i, c.toString())
//                        }
//                        if (i in 9..10) {
//                            finalString.replace(7 + i, 8 + i, c.toString())
//                        }
//                    }
//
//                    if (digitsOnly.length == 0) {
//                        lastDigitsCount = 1
//                    }

//                    val newFinalSize = finalString.length



//                    val cursor: Int
//                    when (lastDigitsCount) {
//                        1, 2, 3 -> cursor = digitsOnly.length + 3
//                        4, 5, 6 -> cursor = digitsOnly.length + 5
//                        7, 8    -> cursor = digitsOnly.length + 6
//                        else -> cursor = min(digitsOnly.length + 7, 18)
//                    }
//                    this@PhoneInputEditText.setSelection(cursor)
                }
                isRunning = false
            }

        })
    }


    private fun getDigitCountBeforeCursor(string: String?, cursorPosition: Int): Int {
        if (string == null) return 0
        var digitNumber = 0
        var c = 0
        for (s in string) {
            if (c == cursorPosition) return digitNumber
            if (s.isDigit()) {
                digitNumber += 1
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
            if (s.isDigit()) {
                digit += 1
            }
        }
        return c
    }

    override fun onSelectionChanged(selStart: Int, selEnd: Int) {
        if (selStart == selEnd && selStart < 4) setSelection(min(4, length()))
        super.onSelectionChanged(selStart, selEnd)
    }
}