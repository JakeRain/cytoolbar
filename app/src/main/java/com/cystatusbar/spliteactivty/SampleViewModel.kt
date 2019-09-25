package com.cystatusbar.spliteactivty

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SampleViewModel: ViewModel() {
    val textData = MutableLiveData<String>()
}