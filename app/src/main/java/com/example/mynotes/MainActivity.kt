package com.example.mynotes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseUser:FirebaseUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val emailMain=findViewById<EditText>(R.id.loginEmailMain)
        val passMain=findViewById<EditText>(R.id.loginPassMain)
        val  loginBtn=findViewById<MaterialButton>(R.id.loginBtnMain)
        val forgotPassMain=findViewById<TextView>(R.id.forgotPassMain)
        val gotoSignUp=findViewById<MaterialButton>(R.id.gotosignupMain)
        firebaseAuth=FirebaseAuth.getInstance()
        if(firebaseAuth.currentUser!=null){
            finish()
            startActivity(Intent(this,NotesActivity::class.java))
        }
        gotoSignUp.setOnClickListener{
            val intent=Intent(this,SignupActivity::class.java)
            startActivity(intent)
        }
        forgotPassMain.setOnClickListener{
            val intent=Intent(this,ForgotPassword::class.java)
            startActivity(intent)
        }
        loginBtn.setOnClickListener{
            val emailMain=emailMain.text.toString().trim()
            val passMain=passMain.text.toString().trim()
            if(emailMain.isEmpty()||passMain.isEmpty())
                Toast.makeText(this,"All field required",Toast.LENGTH_SHORT).show()
            else{
                // TODO: get user details for authentication from firebase
                firebaseAuth.signInWithEmailAndPassword(emailMain,passMain).addOnCompleteListener{
                    if(it.isSuccessful){
                        checkEmailVerification()
                    }
                    else Toast.makeText(this,"Account doesn't exist",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun checkEmailVerification() {
         firebaseUser=firebaseAuth?.currentUser!!
        if(firebaseUser?.isEmailVerified!!){
            Toast.makeText(this,"Logged In",Toast.LENGTH_SHORT).show()
            finish()//its significance?
            startActivity(Intent(this,NotesActivity::class.java))
        }
        else{
            Toast.makeText(this,"Verify your Email first",Toast.LENGTH_SHORT).show()
            firebaseAuth.signOut()
        }
    }
}