package com.cystatusbar.viewmodel

import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WindowInsetsViewModel: ViewModel(){

    var windowInsetsCompatMutableLiveData: MutableLiveData<WindowInsetsCompat> = MutableLiveData()


}