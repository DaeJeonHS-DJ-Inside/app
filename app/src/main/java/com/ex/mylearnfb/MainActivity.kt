package com.ex.mylearnfb

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {

        val intentLogin = Intent(this, Loginscreen::class.java)

//        testButton_login.setOnClickListener { startActivity(intentLogin) }
        startActivity(intentLogin)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()

        auth.createUserWithEmailAndPassword("abcd@naver.com", "123123").addOnCompleteListener(this) {  task ->
            if (task.isSuccessful) {
                Toast.makeText(baseContext, "Authentication Success.", Toast.LENGTH_SHORT).show()
            } else{
                Toast.makeText(baseContext, "Authentication Failed.", Toast.LENGTH_SHORT).show()
            }

        }
    }
}