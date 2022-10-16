package com.example.mynotes

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class ForgotPassword:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        val emailBox=findViewById<EditText>(R.id.emailbox)
        val recover=findViewById<MaterialButton>(R.id.recoverBtn)
        val backToLogin=findViewById<TextView>(R.id.gotologin)

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
            }
        }

    }
}