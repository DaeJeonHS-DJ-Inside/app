package com.ex.mylearnfb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_my_groups.*
import kotlinx.android.synthetic.main.listview_group.*

class activityMyGroups : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private var uid:String? = null
    val listGroups = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_groups)

        if (intent.hasExtra("uid"))
            uid = intent.getStringExtra("uid")
        auth = FirebaseAuth.getInstance()

        database = FirebaseDatabase.getInstance().reference.child("groups")


        button_enterMakeGroupActivity.setOnClickListener {
            var nextIntent = Intent(this, activityMakeGroup::class.java)
            nextIntent.putExtra("uid", uid)
            startActivity(nextIntent)
        }

        btEnterSearchingGroup.setOnClickListener {
            var nextIntent = Intent(this, ActivitySearchingGroup::class.java)
            nextIntent.putExtra("uid", uid)
            startActivity(nextIntent)
        }

        val listGroupAdapter = GroupListAdapter(this, listGroups)
        listviewGroup.adapter = listGroupAdapter

        database.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d("TEST", snapshot.children.toString())
                for (data in snapshot.children)
                {
                    Log.d("TEST", data.toString())
                    val modelResult = data.getValue(DataModel::class.java)

                    Log.d("TEST", modelResult.toString())
                    listGroups.add(modelResult?.groupName.toString())
                }
                listGroupAdapter.notifyDataSetChanged()
            }

        })


        listviewGroup.setOnItemClickListener { adapterView, view, i, l ->

            val element = listGroupAdapter.getItemId(i)
            Log.d("ITEM", listGroupAdapter.getItemId(i).toString())
            Log.d("ITEM", element.toString())

            database.addListenerForSingleValueEvent(object : ValueEventListener
            {
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (ds in snapshot.children)
                    {
                        val result = ds.getValue(DataModel::class.java)
                        if(result?.groupName.toString() == listGroups[element.toInt()].toString())
                            Log.d("TEST", result?.groupDescription.toString())

                        Log.d("TEST", result?.groupName.toString())

                    }
                }
            })

            var nextIntent = Intent(this, activityGroupMain::class.java)
            nextIntent.putExtra("uid", uid)
            nextIntent.putExtra("groupName", listGroups[element.toInt()])
            startActivity(nextIntent)

        }



}
}