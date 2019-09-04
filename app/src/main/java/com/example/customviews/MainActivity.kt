package com.example.customviews


import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import android.text.style.UnderlineSpan
import android.text.SpannableString
import android.support.v4.app.SupportActivity
import android.support.v4.app.SupportActivity.ExtraData
import android.support.v4.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.text.Spanned
import android.util.Log


class MainActivity : AppCompatActivity() {

    lateinit var textView:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

