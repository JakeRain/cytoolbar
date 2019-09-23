package com.cystatusbar.extensions

import androidx.core.view.WindowInsetsCompat

val WindowInsetsCompat.currentSafeLeft: Int
    get() {
        var size = 0
        displayCutout?.safeInsetLeft?.let {
            size = it
        }
        if (size == 0) size = systemWindowInsetLeft
        return size
    }



val WindowInsetsCompat.currentSafeTop:Int
get() {
    var size = 0
    displayCutout?.safeInsetTop?.let {
        size = it
    }
    if(size == 0)size = systemWindowInsetTop
    return size
}


val WindowInsetsCompat.currentSafeRight: Int
get() {
    var size = 0
    displayCutout?.safeInsetRight?.let {
        size = it
    }
    if(size == 0)size = systemWindowInsetRight
    return size
}



val WindowInsetsCompat.currentSafeBottom: Int
get() {
    var size = 0
    displayCutout?.safeInsetBottom?.let {
        size = it
    }
    if(size == 0) size = systemWindowInsetBottom
    return size
}

