package com.ex.mylearnfb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_my_groups.*

class activityMakeGroup : AppCompatActivity() {

    private var uid:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_make_group)

        if(intent.hasExtra("uid"))
            uid = intent.getStringExtra("uid")

    }
}