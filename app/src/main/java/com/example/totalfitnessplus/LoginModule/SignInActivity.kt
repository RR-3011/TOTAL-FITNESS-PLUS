package com.example.totalfitnessplus.LoginModule

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.totalfitnessplus.MainActivity
import com.example.totalfitnessplus.R
import com.example.totalfitnessplus.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth

class SignInActivity : AppCompatActivity() {
    private lateinit var binding:ActivitySignInBinding
    lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth= FirebaseAuth.getInstance()

        val currentUser=auth.currentUser
        if(currentUser!=null){
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
        loginUser()
    }

    private fun loginUser(){
        binding.signInbtn.setOnClickListener {
            if (TextUtils.isEmpty(binding.emailTVSignIn.text.toString())) {
                binding.emailTVSignIn.setError("Please enter your username")
                return@setOnClickListener
            }else if (TextUtils.isEmpty(binding.passwordTVSignIn.text.toString())) {
                binding.emailTVSignIn.setError("Please enter your password")
                return@setOnClickListener
            }

            auth.signInWithEmailAndPassword(binding.emailTVSignIn.text.toString(),binding.passwordTVSignIn.text.toString())
                .addOnCompleteListener {
                    if (it.isSuccessful){
                        startActivity(Intent(this,MainActivity::class.java))
                        finish()
                    }else{
                        Toast.makeText(this, "Sign-In failed, please try again", Toast.LENGTH_SHORT).show()
                    }
                }
        }
        binding.createAccountTV.setOnClickListener{
            startActivity(Intent(this,SignUpActivity::class.java))

        }

    }
}