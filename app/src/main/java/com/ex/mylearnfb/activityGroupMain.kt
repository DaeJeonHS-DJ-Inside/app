package com.ex.mylearnfb

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_group_main.*


class activityGroupMain : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var dbpost: DatabaseReference
    private var uid:String? = null
    private var groupName:String? = null
    val listpost = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_main)


        if (intent.hasExtra("uid"))
            uid = intent.getStringExtra("uid")
        if (intent.hasExtra("groupName"))
            groupName = intent.getStringExtra("groupName")

        auth = FirebaseAuth.getInstance()

        database = FirebaseDatabase.getInstance().reference.child("groups")

        /*val listPostAdapter = GroupPostAdapter(this, listpost, )
        lvGroupPosts.adapter = listPostAdapter

        database.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                for (data in snapshot.children)
                {
                    val modelResult = data.getValue(DataModel::class.java)
                    listpost.add(modelResult?.groupName.toString())
                }
                listPostAdapter.notifyDataSetChanged()
            }

        })*/


        fabPosting.setOnClickListener{
            val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view = inflater.inflate(R.layout.popup_posting, null)

            val etTitle: EditText = view.findViewById(R.id.etPostTitle)
            val etContent: EditText = view.findViewById(R.id.etPostContents)
            val alertDialog = AlertDialog.Builder(this)
                .setTitle("글쓰기")
                .setPositiveButton("저장") { dialog, which ->
                    Toast.makeText(applicationContext, "글쓰기 완료", Toast.LENGTH_SHORT).show()

                    dbpost = database.child("posts")

                    dbpost.child("Title").setValue(etTitle.text.toString())
                    dbpost.child("Contents").setValue(etContent.text.toString())
                    dbpost.child("writer").setValue(uid.toString())
                }
                .setNeutralButton("취소", null)
                .create()

            alertDialog.setView(view)
            alertDialog.show()
        }
    }
}