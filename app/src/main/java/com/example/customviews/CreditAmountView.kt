package com.example.customviews

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.textfield.TextInputEditText
import android.graphics.PorterDuff
import android.text.Editable
import android.text.TextWatcher
import android.view.animation.AccelerateDecelerateInterpolator

import androidx.core.content.ContextCompat


class CreditAmountView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attributeSet, defStyleAttr) {

    private var isUpdatedFromSeekBar = false
    private var isUpdatedFromSeekBarAnimator = false
    private var isUpdatedFromEditText = false
    private val seekBarMax=1000

    var min = ""
        set(value) {
            field = value
            minLong = parseNumber(value)
            minTextView.text = field
        }


    var max = ""
        set(value) {
            field = value
            maxLong = parseNumber(value)
            maxTextView.text = field
        }

    var amount = ""
        set(value) {
            field = value
            amountLong = parseNumber(value)
            amountEditText.setText(amountLong.toString())
        }


    private var minLong: Long = 0
        private set(value) {
            field = value
            if (value < 0) field = 0
            updateAmountEditText((minLong+maxLong)/2)
        }
    private var maxLong: Long = 0
        private set(value) {
            field = value
            if (value < 0) field = 0
            updateAmountEditText((minLong+maxLong)/2)
        }

    var amountLong: Long = 0
        set(value) {
            field = checkedAmount(value)
            updateAmountEditText(field)
            updateSeekBar(value)
        }

    private val amountEditText: TextInputEditText
    private val seekBar: SeekBar
    var animator:ValueAnimator?=null

    private val minTextView: TextView
    private val maxTextView: TextView

    init {
        val v = inflate(context, R.layout.credit_amount_view, this)
        amountEditText =
            (v.findViewById(R.id.inputLayout) as ViewGroup).findViewById(R.id.creditAmount)
        minTextView = v.findViewById(R.id.min)
        maxTextView = v.findViewById(R.id.max)
        seekBar = v.findViewById(R.id.seekBar)
        setupComponents()
    }

    private fun setupComponents() {
        seekBar.max=seekBarMax
//        seekBar.progress=seekBarMax/2
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
            }

            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                amountEditText.requestFocus()
                if (!isUpdatedFromSeekBar) return
                updateValueFromSeekbar(p1)
            }

        })

        amountEditText.addTextChangedListener(object:TextWatcher{
            override fun afterTextChanged(p0: Editable?) {}

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (isUpdatedFromSeekBar) return
                isUpdatedFromEditText=true
                amountLong=parseNumber(p0.toString())
            }

        })
    }

    fun updateValueFromSeekbar(percent: Int) {
        val newValue = (maxLong - minLong) * percent / seekBarMax + minLong
        updateAmountEditText(newValue)
    }
    private fun parseNumber(value: String): Long {
        val stringWithOnlyDigits = value.filter { it.isDigit() }
        if (stringWithOnlyDigits.isEmpty()) return 0
        return stringWithOnlyDigits.toLong()
    }

    private fun updateAmountEditText(value: Long) {
        if (isUpdatedFromEditText) {
            isUpdatedFromEditText=false
            return
        }
        amountEditText.setText(value.toString())
    }

    private fun updateSeekBar(value: Long) {
        val percentVal = (value - minLong) * seekBarMax / (maxLong - minLong)
        updateAnimatedSeekBarValue(percentVal.toInt())
    }

    private fun updateAnimatedSeekBarValue(value: Int) {
        animator=ValueAnimator.ofInt(seekBar.progress,value)
        animator?.run{
            cancel()
            duration=500
            interpolator=AccelerateDecelerateInterpolator()
            addUpdateListener {
                seekBar.progress=it.animatedValue as Int
            }
            start()
        }
    }

    private fun checkedAmount(value: Long) =
        when {
            value < minLong -> minLong
            value > maxLong -> maxLong
            else -> value
        }


}


//private fun updateAmount(text: CharSequence) {
//    if (text.isEmpty()) return
//    amountLong = text.toString().toLong()
//}
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
//////    Long index0 = 0;
//////    @StyleableRes
//////    Long index1 = 1;
//////    @StyleableRes
//////    Long index2 = 2
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
//class ColorUnderlineSpan(private val underlineColor: Long) : UnderlineSpan() {
//
//
//
//    override fun updateDrawState(ds: TextPaLong) {
//        super.updateDrawState(ds)
//        ds.color = underlineColor
//    }
//
//    override fun getSpanTypeId(): Long {
//        return super.getSpanTypeId()
//    }
//}
//
//class DashedUnderlineSpan(color:Long, private val spannedText: String) : ReplacementSpan(){
//
//    private val paLong=PaLong()
//
//    private var isLengthIsCached=false
//    private var spanLength:Float=0f
//    init {
//        paLong.color = Color.GREEN
////        paLong.style=PaLong.Style.STROKE
//        paLong.style=PaLong.Style.FILL
//        paLong.pathEffect=DashPathEffect(floatArrayOf(4f, 1f), 0f)
//        paLong.strokeWidth=1f
//    }
//
//    override fun getSize(
//        paLong: PaLong,
//        text: CharSequence?,
//        start: Long,
//        end: Long,
//        fm: PaLong.FontMetricsLong?
//    ): Long {
//
//        return (ceil(paLong.measureText(text, start, end)).toLong())
//    }
//
//    override fun draw(
//        canvas: Canvas,
//        text: CharSequence,
//        start: Long,
//        end: Long,
//        x: Float,
//        top: Long,
//        y: Long,
//        bottom: Long,
//        paLong: PaLong
//    ) {
//        Log.i("ADSF",text.toString())
//        canvas.drawText(text, start, end, x, y.toFloat(), paLong)
////        val paLong1=PaLong()
////        canvas.drawLine(0f,0f,500f,500f,paLong1)
////        if (!isLengthIsCached)
////             spanLength = paLong.measureText(spannedText)
////
////        // https://code.google.com/p/android/issues/detail?id=29944
////        // canvas.drawLine can't draw dashes when hardware acceleration is enabled,
////        // but canvas.drawPath can
////        val path = Path()
////        path.moveTo(x, y + 0f)
////        path.lineTo(x + spanLength, y + 0f)
////        canvas.drawPath(path, paLong)
//    }

//}