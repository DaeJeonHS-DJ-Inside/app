package com.ex.mylearnfb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*

class Register : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        button_register.setOnClickListener {

            if(password.text.toString() == password_re.text.toString())
            {
                auth.createUserWithEmailAndPassword(email_register.text.toString(), password.text.toString())
                    .addOnCompleteListener(this) {task ->
                        if(task.isSuccessful) {
                            Toast.makeText(baseContext, "Registering Success", Toast.LENGTH_SHORT).show()


                        } else {
                            Toast.makeText(baseContext, "Registering Failed", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }


}