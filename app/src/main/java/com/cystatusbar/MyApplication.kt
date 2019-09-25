package com.cystatusbar

import android.app.Application
import com.ycbjie.webviewlib.X5WebUtils

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        X5WebUtils.init(this);
    }
}