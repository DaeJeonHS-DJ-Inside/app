package com.ex.mylearnfb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_my_groups.*

class activityMyGroups : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private var uid:String? = null
    val listGroups = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_groups)

        if (intent.hasExtra("uid"))
            uid = intent.getStringExtra("uid")
        auth = FirebaseAuth.getInstance()


        button_enterMakeGroupActivity.setOnClickListener {
            var nextIntent = Intent(this, activityMakeGroup::class.java)
            nextIntent.putExtra("uid", uid)
            startActivity(nextIntent)
        }

        val listGroupAdaptor = GroupListAdaptor(this, listGroups)
        listviewGroup.adapter = listGroupAdaptor

    }


}