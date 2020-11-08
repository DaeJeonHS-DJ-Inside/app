package com.ex.mylearnfb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_make_group.*
import kotlinx.android.synthetic.main.activity_my_groups.*

class activityMakeGroup : AppCompatActivity() {

    private var uid:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_make_group)

        if(intent.hasExtra("uid"))
            uid = intent.getStringExtra("uid")

        buttonMakeGroup.setOnClickListener {
            val database = FirebaseDatabase.getInstance()
            val myRef = database.reference

            val dataInput = DataModel(

                editTextGroupName.text.toString(),
                editTextGroupDescription.text.toString()
            )

            uid?.let { it1 -> myRef.child(it1).push().setValue(dataInput)
            myRef.child("groups").push().setValue(dataInput)
            }
        }
    }
}