package com.ex.mylearnfb

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_make_group.*
import kotlinx.android.synthetic.main.activity_my_groups.*
import java.lang.Exception
import java.net.URL

class activityMakeGroup : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var newdb: DatabaseReference

    private var uid:String? = null
    private val OPENGALLERY = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_make_group)

        if(intent.hasExtra("uid"))
            uid = intent.getStringExtra("uid")

        database = FirebaseDatabase.getInstance().reference.child("groups")

        buttonMakeGroup.setOnClickListener {
            if (!TextUtils.isEmpty(editTextGroupName.text) && !TextUtils.isEmpty(editTextGroupDescription.text)) {
                newdb = database.child(editTextGroupName.text.toString())

                val dataInput = DataModel(

                    editTextGroupName.text.toString(),
                    editTextGroupDescription.text.toString(),
                    uid.toString()
                )

                newdb.setValue(dataInput)

                val postdataInput = PostData(
                    "Hello",
                    "Hello Group",
                    "Administrator"
                )
                newdb.child("Posts").child("post" + 0).setValue(postdataInput)
            }
        }
        btSelectGroupBanner.setOnClickListener{
            selectGroupImage()
        }
    }

    private fun selectGroupImage(){
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, OPENGALLERY)
    }

    @Override
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        Log.d("BANNER", "reached1")

        Log.d("BANNER", Activity.RESULT_OK.toString())
        if(resultCode == Activity.RESULT_OK) {
            if (requestCode == OPENGALLERY) {
                var currentImageUrl : Uri? = data?.data

                Log.d("BANNER", "reached2")
                try {
                    val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, currentImageUrl)
                    ivPreviewGroupBannerImage.setImageBitmap(bitmap)

                    Log.d("BANNER", "reached3")
                }catch (e:Exception){

                    Log.d("BANNER", "reached4")
                    e.printStackTrace()
                }
            }
        }
    }

}
