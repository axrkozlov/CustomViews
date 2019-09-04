package com.example.customviews

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.SeekBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.textfield.TextInputEditText


class CreditAmountView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attributeSet, defStyleAttr) {

    private var isUpdaterFromSeekBar = false
//    sealed class UpdatedFrom {
//         EditText:UpdatedFrom(),
//         SeekBar:UpdatedFrom()
//    }
//

    val amountEditText: TextInputEditText
    val seekBar: SeekBar
    var min: Long = 0
        set(value) {
            field = value
            if (value < 0) field = 0
            minTextView.text = field.toString()
            updateAmountEditText(min)
        }
    var max: Long = 0
        set(value) {
            field = value
            if (value < 0) field = 0
            maxTextView.text = field.toString()
        }
    var amount: Long = 0
        set(value) {
            field = checkedAmount(value)
            updateSeekBar(value)
        }

    private fun updateAmountEditText(value: Long) {
        amountEditText.setText(value.toString())
        isUpdaterFromSeekBar = true
    }

    private fun updateSeekBar(value: Long) {
        val onePercent = (max - min) / 100.toDouble()
        val percents = (value - min) * 100 / (max - min)
        Log.i("updateSeekBar", "$onePercent $percents")
        seekBar.progress = percents.toInt()

    }

    val minTextView: TextView
    val maxTextView: TextView

    init {
        val v = inflate(context, R.layout.credit_amount_view, this)

        amountEditText =
            (v.findViewById(R.id.inputLayout) as ViewGroup).findViewById(R.id.creditAmount)
        minTextView = v.findViewById(R.id.min)
        maxTextView = v.findViewById(R.id.max)
//        amountEditText.requestFocus()

        amountEditText.setOnEditorActionListener { textView: TextView?, i: Int, _: KeyEvent? ->
            if (i == EditorInfo.IME_ACTION_DONE) {
                updateAmount(textView?.text ?: "")
            }
            false
        }

        seekBar = v.findViewById(R.id.seekBar)
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStopTrackingTouch(p0: SeekBar?) {
                isUpdaterFromSeekBar = false
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                isUpdaterFromSeekBar = true
            }

            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                if (!isUpdaterFromSeekBar) return
                updateValueFromSeekbar(p1)
            }

        })


    }

    private fun updateAmount(text: CharSequence) {
        if (text.isEmpty()) return
        amount = text.toString().toLong()
    }

    fun updateValueFromSeekbar(percent: Int) {
        val newValue = (max - min) * percent / 100 + min
        updateAmountEditText(newValue)

    }

    private fun checkedAmount(value: Long) =
        when {
            value < min -> min
            value > max -> max
            else -> value
        }


}


//    var salesViewActive=false
//        set(value) {
//            field=value
//            if (value) {
//                salesUnderlineImageView.visibility= View.VISIBLE
//                TextViewCompat.setTextAppearance(salesTextView, R.style.Body3Link)
//            }
//            else {
//                salesUnderlineImageView.visibility= View.INVISIBLE
//                TextViewCompat.setTextAppearance(salesTextView, R.style.Body3Style)
//            }
//
//        }
//
//    var titleViewActive=false
//        set(value) {
//            field=value
//            titleTextView.isActivated=field
//        }
//
//    var titleViewHidden=false
//        set(value) {
//            field=value
//            if (value) {
//                titleTextView.visibility= View.INVISIBLE
//            }
//            else {
//                titleTextView.visibility= View.VISIBLE
//            }
//        }
//}

////    @StyleableRes
//////    int index0 = 0;
//////    @StyleableRes
//////    int index1 = 1;
//////    @StyleableRes
//////    int index2 = 2
////
////    var sales:String? = null
////    set(value) {
////        field=value
////        val text=field ?:""
//////        val content = SpannableString(text)
//////        val density= DashedSpan(resources.displayMetrics.density)
//////        content.setSpan(DashedSpan(resources.displayMetrics.density), 0, text.length, 0)
////        salesTextView.text=text
////
////    }
////    var repayment:String? = null
////        set(value) {
////            field=value
////            val text=field// ?:""
////            repaymentTextView.text=text
////        }
////
////    var overpayment:String? = null
////        set(value) {
////            field=value
////            val text=field// ?:""
////            overpaymentTextView.text=text
////        }
//class ColorUnderlineSpan(private val underlineColor: Int) : UnderlineSpan() {
//
//
//
//    override fun updateDrawState(ds: TextPaint) {
//        super.updateDrawState(ds)
//        ds.color = underlineColor
//    }
//
//    override fun getSpanTypeId(): Int {
//        return super.getSpanTypeId()
//    }
//}
//
//class DashedUnderlineSpan(color:Int, private val spannedText: String) : ReplacementSpan(){
//
//    private val paint=Paint()
//
//    private var isLengthIsCached=false
//    private var spanLength:Float=0f
//    init {
//        paint.color = Color.GREEN
////        paint.style=Paint.Style.STROKE
//        paint.style=Paint.Style.FILL
//        paint.pathEffect=DashPathEffect(floatArrayOf(4f, 1f), 0f)
//        paint.strokeWidth=1f
//    }
//
//    override fun getSize(
//        paint: Paint,
//        text: CharSequence?,
//        start: Int,
//        end: Int,
//        fm: Paint.FontMetricsInt?
//    ): Int {
//
//        return (ceil(paint.measureText(text, start, end)).toInt())
//    }
//
//    override fun draw(
//        canvas: Canvas,
//        text: CharSequence,
//        start: Int,
//        end: Int,
//        x: Float,
//        top: Int,
//        y: Int,
//        bottom: Int,
//        paint: Paint
//    ) {
//        Log.i("ADSF",text.toString())
//        canvas.drawText(text, start, end, x, y.toFloat(), paint)
////        val paint1=Paint()
////        canvas.drawLine(0f,0f,500f,500f,paint1)
////        if (!isLengthIsCached)
////             spanLength = paint.measureText(spannedText)
////
////        // https://code.google.com/p/android/issues/detail?id=29944
////        // canvas.drawLine can't draw dashes when hardware acceleration is enabled,
////        // but canvas.drawPath can
////        val path = Path()
////        path.moveTo(x, y + 0f)
////        path.lineTo(x + spanLength, y + 0f)
////        canvas.drawPath(path, paint)
//    }

//}