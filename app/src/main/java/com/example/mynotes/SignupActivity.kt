package com.example.mynotes

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth

class SignupActivity:AppCompatActivity() {
    private lateinit var firebaseAuth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val gotologin=findViewById<TextView>(R.id.gotologin)
        val signupBtn=findViewById<MaterialButton>(R.id.signupBtn) //register new user using fb
        val signupEmail=findViewById<EditText>(R.id.loginEmail)
        val signupPass=findViewById<EditText>(R.id.loginPass)
        firebaseAuth=FirebaseAuth.getInstance()
        gotologin.setOnClickListener{
            val intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
        signupBtn.setOnClickListener{
            val email=signupEmail.text.toString().trim()
            val pass=signupPass.text.toString().trim()
            if(email.isEmpty()||pass.isEmpty())
                Toast.makeText(this,"All fields required",Toast.LENGTH_SHORT).show()
            else if(pass.length<7)
                Toast.makeText(this,"Password needs to be of more than 7",Toast.LENGTH_SHORT).show()
            else{
                // TODO: using firebase register a user
                firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener{
                    if(it.isSuccessful){
                        Toast.makeText(this,"Successfully Registered",Toast.LENGTH_SHORT).show()
                        sendEmailVerification()//sends email to gmail once user has registered
                    }
                    else Toast.makeText(this,"Registration unsuccessful",Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    private fun sendEmailVerification() {
        val firebaseUser=firebaseAuth.currentUser
        if(firebaseUser!=null){//user exists
            firebaseUser.sendEmailVerification().addOnCompleteListener{
                Toast.makeText(this,"Verification link sent to registered Email id",Toast.LENGTH_SHORT).show()
                firebaseAuth.signOut()
                finish()
                startActivity(Intent(this,MainActivity::class.java))
            }
        }
        else{
            Toast.makeText(this,"Verification Failed",Toast.LENGTH_SHORT).show()
        }
    }

}