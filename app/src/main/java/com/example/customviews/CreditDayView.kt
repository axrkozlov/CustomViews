package com.example.customviews

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.widget.TextViewCompat


class CreditDayView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attributeSet, defStyleAttr) {


    val titleTextView:TextView
    val salesTextView:TextView
    val repaymentTextView:TextView
    val overpaymentTextView:TextView

    init {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null)

        val v = inflate(context, R.layout.credit_day_view, this)

        titleTextView=v.findViewById(R.id.title)
        salesTextView=v.findViewById(R.id.sales)
        repaymentTextView=v.findViewById(R.id.repayment)
        overpaymentTextView=v.findViewById(R.id.overpayment)
    }

}

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