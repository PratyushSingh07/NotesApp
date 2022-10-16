package com.example.mynotes

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth

class ForgotPassword:AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        val emailBox=findViewById<EditText>(R.id.emailbox)
        val recover=findViewById<MaterialButton>(R.id.recoverBtn)
        val backToLogin=findViewById<TextView>(R.id.gotologin)
        firebaseAuth=FirebaseAuth.getInstance()
        backToLogin.setOnClickListener{
            val intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
        recover.setOnClickListener{
            var email=emailBox.text.toString().trim()
            if(email.isEmpty()){
                Toast.makeText(this,"Email cannot be empty",Toast.LENGTH_SHORT).show()
            }
            else{
                // TODO: use firebase to send recovery email
                firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener{
                    if(it.isSuccessful){
                        Toast.makeText(this,"Mail sent on registered email",Toast.LENGTH_SHORT).show()
                        finish()
                        startActivity(Intent(this,MainActivity::class.java))
                    }
                }
            }
        }

    }
}