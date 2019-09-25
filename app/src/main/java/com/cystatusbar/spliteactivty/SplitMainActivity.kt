package com.cystatusbar.spliteactivty

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import com.cystatusbar.R
import com.staticviewmodelstore.StaticViewMOdelLifecycleActivity
import com.staticviewmodelstore.StaticViewModelStore
import kotlinx.android.synthetic.main.fragment_bottom.*

class SplitMainActivity : StaticViewMOdelLifecycleActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_split_main)


        button.setOnClickListener{
            startActivity(Intent(this , SplitActivity::class.java))
        }
    }
}