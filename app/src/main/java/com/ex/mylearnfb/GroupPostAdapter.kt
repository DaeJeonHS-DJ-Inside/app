package com.ex.mylearnfb

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.listview_group.view.*
import kotlinx.android.synthetic.main.listview_post.view.*

class GroupPostAdapter(val context: Context, private val list:ArrayList<String>, var groupName: String) : BaseAdapter(){

    private lateinit var database:DatabaseReference

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {

        val view: View = LayoutInflater.from(context).inflate(R.layout.listview_post, null)

        database = FirebaseDatabase.getInstance().reference.child("groups").child(groupName).child("Posts")

        database.addListenerForSingleValueEvent(object : ValueEventListener
        {
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: PostData) {
                for (ds in snapshot.children){
                    val result = ds.getValue(DataModel::class.java)
                    if(result?.PostName.toString() == list[p0]){
                        view.tvListGroupDescription.text = result?.groupDescription.toString()
                    }
                }
            }
        })

        view.tvPostTitle.text = list[p0]

        return view
    }

    override fun getItem(p0: Int): Any {
        return 0
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getCount(): Int {
        return list.size
    }
}