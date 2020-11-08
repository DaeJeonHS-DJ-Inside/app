package com.ex.mylearnfb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_my_groups.*
import kotlinx.android.synthetic.main.listview_group.*

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
        Log.d("Test", listGroupName.text.toString())

        val listGroupAdapter = GroupListAdapter(this, listGroups)
        listviewGroup.adapter = listGroupAdapter

        Log.d("Test", listGroupName.text.toString())
    }


}