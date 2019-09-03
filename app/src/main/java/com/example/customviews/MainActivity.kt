package com.example.customviews


import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

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
    }
}
