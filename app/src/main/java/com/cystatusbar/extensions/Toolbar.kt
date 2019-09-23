package com.cystatusbar.extensions

import android.graphics.PorterDuff
import androidx.annotation.ColorInt
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import com.cystatusbar.R

fun Toolbar.setBackArrowColor(@ColorInt color:Int = ResourcesCompat.getColor(resources , R.color.appbarTitleColor , context.theme)){
    navigationIcon?.setColorFilter(color , PorterDuff.Mode.SRC_ATOP)
}