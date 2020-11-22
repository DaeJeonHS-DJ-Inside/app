package com.ex.mylearnfb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.SearchView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_my_groups.*
import kotlinx.android.synthetic.main.activity_searching_group.*

class ActivitySearchingGroup : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private var uid:String? = null
    val listGroups = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_searching_group)

        if (intent.hasExtra("uid"))
            uid = intent.getStringExtra("uid")

        database = FirebaseDatabase.getInstance().reference.child("groups")

        svGroupSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                Log.d("SRC", "Submit")
                searchGroup(p0.toString())
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                Log.d("SRC", "Change")
                return true
            }
        })



    }

    fun searchGroup(keyword : String) {

        val searched = ArrayList<String>()

        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                for (data in snapshot.children)
                {
                    val modelResult = data.getValue(DataModel::class.java)
                    if (modelResult?.groupName.toString().contains(keyword))
                        searched.add(modelResult?.groupName.toString())
                }
            }
        })

        val listGroupAdapter = GroupListAdapter(this, searched)
        lvSearchedGroups.adapter = listGroupAdapter
        listGroupAdapter.notifyDataSetChanged()


    }


}