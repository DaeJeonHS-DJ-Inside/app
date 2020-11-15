package com.ex.mylearnfb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_make_group.*
import kotlinx.android.synthetic.main.activity_my_groups.*

class activityMakeGroup : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var newdb: DatabaseReference

    private var uid:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_make_group)

        if(intent.hasExtra("uid"))
            uid = intent.getStringExtra("uid")

        database = FirebaseDatabase.getInstance().reference.child("groups")

        buttonMakeGroup.setOnClickListener {
            //val database = FirebaseDatabase.getInstance()
            //val myRef = database.reference
            newdb = database.child(editTextGroupName.text.toString())

            newdb.child("GroupDescription").setValue(editTextGroupDescription.text.toString())
            newdb.child("GroupAdmin").setValue(uid.toString())

/*            val dataInput = DataModel(

                editTextGroupName.text.toString(),
                editTextGroupDescription.text.toString(),
                uid.toString()
            )
*/
            /*uid?.let { it1 -> myRef.child(it1).push().setValue(dataInput)
            myRef.child("groups").push().setValue(dataInput)
            }*/
        }
    }
}