package com.cystatusbar.spliteactivty

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.cystatusbar.R

class SplitActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_split)
        supportFragmentManager.beginTransaction().add(R.id.fragmentContainerTop , TopFragment()).add(R.id.fragmentContainerBottom , BottomFragment()).commit()
    }


    override fun onStart() {
        super.onStart()
        title = "Split Activity"
    }
}