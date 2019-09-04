package com.example.customviews


import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    lateinit var textView:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val creditMonthView = findViewById<CreditMonthView>(R.id.creditRow)
        creditMonthView.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        creditMonthView.titleView.text="1 мес"
        creditMonthView.salesView.text="500"
        creditMonthView.repaymentView.text="600"
        creditMonthView.overpaymentView.text="700"
        creditMonthView.setOnClickListener {

            creditMonthView.salesViewActive=!creditMonthView.salesViewActive
            creditMonthView.titleViewActive=!creditMonthView.titleViewActive
        }

        textView=findViewById(R.id.text)

//        val text = SpannableString("Voglio sottolineare solo questa parola")

    Log.i("MainActivity", "onCreate (line 30): ")

        val udata = "UNDERLINEDTEXT"
        val content = SpannableString(udata)
//        content.setSpan(DottedUnderlineSpan(0,udata,resources.displayMetrics.density),0,udata.length, 0)
        content.setSpan(PathOverSpan(resources.displayMetrics.density),0,udata.length, Spanned.SPAN_MARK_MARK)
        textView.setText(content)
        Log.i("MainActivity", "onCreate (line 35): ${textView.width}")
    }



}

