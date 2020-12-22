package com.ex.mylearnfb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*

class Register : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()

        button_register.setOnClickListener {
            if (!TextUtils.isEmpty(Password?.text) && !TextUtils.isEmpty(password_re.text) && !TextUtils.isEmpty(email_register.text)) {
                if (Password?.text.toString() == password_re.text.toString()) {
                    auth.createUserWithEmailAndPassword(email_register.text.toString(), Password?.text.toString())
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(baseContext, "Registering Success", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(baseContext, "Registering Failed", Toast.LENGTH_SHORT).show()
                            }
                        }

                var nextIntent = Intent(this, Loginscreen::class.java)
                startActivity(nextIntent)
                }
            }else{
                Toast.makeText(baseContext, "빈칸이 있어요", Toast.LENGTH_SHORT).show()
            }
        }
    }
}