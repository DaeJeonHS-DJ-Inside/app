package com.ex.mylearnfb

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.activity_make_group.*
import kotlinx.android.synthetic.main.activity_my_groups.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.lang.Exception
import java.net.URL

class activityMakeGroup : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var newdb: DatabaseReference
    private lateinit var storage: FirebaseStorage

    private var uid:String? = null
    private val OPENGALLERY = 1

    private lateinit var bannerImageUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_make_group)

        if(intent.hasExtra("uid"))
            uid = intent.getStringExtra("uid")

        database = FirebaseDatabase.getInstance().reference.child("groups")
        storage = Firebase.storage

        buttonMakeGroup.setOnClickListener {
            if (!TextUtils.isEmpty(editTextGroupName.text) && !TextUtils.isEmpty(editTextGroupDescription.text)) { // TODO: 2020-12-19 check preview image is null
                newdb = database.child(editTextGroupName.text.toString())

                val storageRef = storage.reference
                val groupImageRef = storageRef.child(editTextGroupName.text.toString())
 //               storageRef.child("images/" + editTextGroupName.text.toString())



                var uploadTask: UploadTask = groupImageRef.putFile(bannerImageUri)

                Log.d("IMAGE", uploadTask.toString())

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

            var nextIntent = Intent(this, activityMyGroups::class.java)
            nextIntent.putExtra("uid", uid)
            startActivity(nextIntent)
        }
        btSelectGroupBanner.setOnClickListener{
            selectGroupImage()
        }
    }

    private fun getImageUriFromBitmap(context: Context, bitmap: Bitmap): Uri{
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(context.contentResolver, bitmap, "Title", null)
        return Uri.parse(path.toString())
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

                    bannerImageUri = getImageUriFromBitmap(this, bitmap)

                }catch (e:Exception){

                    Log.d("BANNER", "reached4")
                    e.printStackTrace()
                }
            }
        }



    }



}
