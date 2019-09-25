package com.cystatusbar.spliteactivty

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.cystatusbar.R
import com.staticviewmodelstore.StaticViewModelLifecycleFragment
import kotlinx.android.synthetic.main.fragment_top.*

class TopFragment :StaticViewModelLifecycleFragment(){
    private var sampleViewModel: SampleViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sampleViewModel = getStaticViewModel("testCustomKey" , SampleViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_top , container , false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sampleViewModel?.textData?.observe(this , Observer {
            applyViewModelData(it)
        })
        applyViewModelData(sampleViewModel?.textData?.value)
    }

    private fun applyViewModelData(text: String?){
        if(text != null){
            textView.text = "持有数据:${text}"
        }else{
            textView.text = "没有数据"
        }
    }
}