package com.example.customviews.model

class DailySet (
    val title:String,
    val loanPaymentDailySet:LoanPaymentDailySet)

class LoanPaymentDailySet(
    description:String,
    salesAmount:String,
    paidAmount	:String,
    overpaidAmount:String,
    fineAmount:String,
    type:Int
)