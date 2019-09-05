package com.example.customviews


import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val creditMonthView = findViewById<CreditMonthView>(R.id.creditRow)
//        creditMonthView.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        creditMonthView.title="1 мес"
        creditMonthView.sales="500"
        creditMonthView.repayment="600"
        creditMonthView.overpayment="700"



        creditMonthView.setOnClickListener {

            creditMonthView.salesViewActive=!creditMonthView.salesViewActive
//            creditMonthView.titleViewActive=!creditMonthView.titleViewActive
            creditMonthView.titleViewHidden=!creditMonthView.titleViewHidden
        }

        textView=findViewById(R.id.text)
        creditDayRow.title="1 мес"
        creditDayRow.sales="500"
        creditDayRow.repayment="600"
        creditDayRow.overpayment="700"
//        val text = SpannableString("Voglio sottolineare solo questa parola")

    Log.i("MainActivity", "onCreate (line 30): ")

        val udata = "UNDERLINEDTEXT"
        val content = SpannableString(udata)
//        content.setSpan(DottedUnderlineSpan(0,udata,resources.displayMetrics.density),0,udata.length, 0)
        content.setSpan(PathOverSpan(resources.displayMetrics.density),0,udata.length, Spanned.SPAN_MARK_MARK)
        textView.setText(content)
        Log.i("MainActivity", "onCreate (line 35): ${textView.width}")


        val  creditAmountView=findViewById<CreditAmountView>(R.id.creditAmountET)

        creditAmountView.min="-50а ₸"

        creditAmountView.max="99_000_000"

        creditAmountView.step="1000ggg"

//        creditAmountView.amount="  500 000"

    }





}

