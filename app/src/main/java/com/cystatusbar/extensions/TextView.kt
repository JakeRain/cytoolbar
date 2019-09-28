package com.cystatusbar.extensions

import android.os.Build
import android.text.Html
import android.widget.TextView

fun TextView.setHtml(html: String?, flags: Int = 2) {
    if (html == null) {
        text = ""
        return
    }
    text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(html, flags)
    } else {
        Html.fromHtml(html)
    }
}



infix fun TextView.show(content:String?){
    if(content.isNullOrEmpty()){
        text = ""
    }else{
        setHtml(content)
    }
}