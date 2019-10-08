package com.example.customviews


import android.animation.ValueAnimator
import android.os.Bundle
import android.text.Editable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextWatcher
import android.util.Log
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.credit_amount_view.view.*

class MainActivity : AppCompatActivity() {

    lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val creditMonthView = findViewById<CreditMonthView>(R.id.creditRow)
//        creditMonthView.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
//        creditMonthView.title="1 мес"
//        creditMonthView.sales="500"
//        creditMonthView.repayment="600"
//        creditMonthView.overpayment="700"


        creditMonthView.setOnClickListener {

            creditMonthView.salesViewActive = !creditMonthView.salesViewActive
//            creditMonthView.titleViewActive=!creditMonthView.titleViewActive
            creditMonthView.titleViewHidden = !creditMonthView.titleViewHidden
        }


        textView = findViewById(R.id.text)
//        creditDayRow.title="1 мес"
//        creditDayRow.sales="500"
//        creditDayRow.repayment="600"
//        creditDayRow.overpayment="700"
//        val text = SpannableString("Voglio sottolineare solo questa parola")

        Log.i("MainActivity", "onCreate (line 30): ")

        val udata = "UNDERLINEDTEXT"
        val content = SpannableString(udata)
//        content.setSpan(DottedUnderlineSpan(0,udata,resources.displayMetrics.density),0,udata.length, 0)
        content.setSpan(
            PathOverSpan(resources.displayMetrics.density),
            0,
            udata.length,
            Spanned.SPAN_MARK_MARK
        )
        textView.setText(content)
        Log.i("MainActivity", "onCreate (line 35): ${textView.width}")


        val creditAmountView = findViewById<CreditAmountView>(R.id.creditAmountET)
//        cpv.rounded=true
//        cpv.progressColor=ContextCompat.getColor(this,R.color.colorAccent)
//        cpv.progressStrokeWidth=25F
        amountET.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                factor = amountET.sum.toFloat()
                updateProgress(false)
            }

        })

        cpv.setOnClickListener { updateProgress(true) }


//        creditAmountView.min="-50а ₸"
//
//        creditAmountView.max="99_000_000"
//
//        creditAmountView.step="1000ggg"

//        creditAmountView.amount="  500 000"

        val x = 2

        when (x) {
//            2->{
//                Log.i("ASDF","2")
//            }
            in 1..3 -> Log.i("ASDF", "1..3")
        }

    }

    var animator: ValueAnimator? = null
    var factor: Float = 1.0f
    private fun updateProgress(done: Boolean) {
        val start=when (done) {
            false -> 0.0f
            true -> cpv.progress
        }
        animator?.cancel()
        animator = ValueAnimator.ofFloat(start, 100.0f)
        animator?.run {

            duration = when (done) {
                false -> 25000
                true -> 1500
            }

            interpolator = when (done) {
                false -> DecelerateInterpolator(factor)
                true -> DecelerateInterpolator(factor)
            }

            addUpdateListener {
                cpv.progress = it.animatedValue as Float

            }
            start()
        }
    }


}

