package com.ex.mylearnfb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_loginscreen.*

class Loginscreen : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loginscreen)

        auth = FirebaseAuth.getInstance()

        login_button.setOnClickListener {
            auth.signInWithEmailAndPassword(login_email.text.toString(), login_password.text.toString())
                .addOnCompleteListener(this) { task->
                    if(task.isSuccessful) {
                        Log.d("Login", "login success")
                        val user = auth.currentUser

                        val intentMyGroups = Intent(this, activityMyGroups::class.java)
                        intentMyGroups.putExtra("uid", auth.currentUser?.uid)
                        startActivity(intentMyGroups)

                    } else {
                        Log.w("login", "login failure", task.exception)
                        Toast.makeText(baseContext, "Login Failed", Toast.LENGTH_SHORT).show()
                    }
                }
        }

        login_button_register.setOnClickListener {
            val intentRegister = Intent(this, Register::class.java)

            startActivity(intentRegister)
        }
    }
}