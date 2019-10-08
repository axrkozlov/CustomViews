package com.example.customviews

import android.content.Context
import android.content.res.Resources
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.widget.TextViewCompat
import kotlinx.android.synthetic.main.credit_month_view.view.*


class CreditMonthView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attributeSet, defStyleAttr) {

    var title: String = ""
        set(value) {
            field = value
            titleTV.text = field
        }
    var sales: String = ""
        set(value) {
            field = value
            salesTV.text = field
        }
    var repayment: String = ""
        set(value) {
            field = value
            repaymentTV.text = field
        }
    var overpayment: String = ""
        set(value) {
            field = value
            overpaymentTV.text = field
            headerOverpaymentTV.text= context.getString(R.string.headerOverPayment).plus(field)
        }
    var salesViewActive=false
        set(value) {
            field=value
            if (value) {
                salesUnderline.visibility= View.VISIBLE
                TextViewCompat.setTextAppearance(salesTV, R.style.Body3Link)
            }
            else {
                salesUnderline.visibility= View.INVISIBLE
                TextViewCompat.setTextAppearance(salesTV, R.style.Body3Style)
            }
        }

    var titleViewActive=false
        set(value) {
            field=value
            titleTV.isActivated=field
        }

    var titleViewHidden=false
        set(value) {
            field=value
            if (value) {
                titleTV.visibility= View.INVISIBLE
            }
            else {
                titleTV.visibility= View.VISIBLE
            }
        }

    init {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        inflate(context, R.layout.credit_month_view, this)
        val attrs = context.theme.obtainStyledAttributes(
            attributeSet,
            R.styleable.CreditMonthView,
            0, 0
        )
        if (attrs.hasValue(R.styleable.CreditMonthView_title)) {
            title = attrs.getString(R.styleable.CreditMonthView_title)?:""
        }
        if (attrs.hasValue(R.styleable.CreditMonthView_sales)) {
            sales = attrs.getString(R.styleable.CreditMonthView_sales)?:""
        }
        if (attrs.hasValue(R.styleable.CreditMonthView_repayment)) {
            repayment = attrs.getString(R.styleable.CreditMonthView_repayment)?:""
        }
        if (attrs.hasValue(R.styleable.CreditMonthView_overpayment)) {
            overpayment = attrs.getString(R.styleable.CreditMonthView_overpayment)?:""
        }
    }


}
