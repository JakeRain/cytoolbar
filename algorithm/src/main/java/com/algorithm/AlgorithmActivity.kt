package com.algorithm

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_algorithm.*

class AlgorithmActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_algorithm)
    }

    val handler = object : Handler(){
        override fun dispatchMessage(msg: Message) {
            super.dispatchMessage(msg)
            val content = "${show.text}  ${msg.what}"
            show.text = content
        }
    }

     val ints = (40..60).shuffled()



    fun sleep(view: View) {

        ints.forEach{
            handler.sendEmptyMessageDelayed(it , it.toLong())
        }

    }





}