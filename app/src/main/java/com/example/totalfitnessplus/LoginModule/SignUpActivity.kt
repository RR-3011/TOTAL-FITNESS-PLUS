package com.example.totalfitnessplus.LoginModule

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.Toast
import com.example.totalfitnessplus.R
import com.example.totalfitnessplus.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    var databaseReference: DatabaseReference?=null
    var database: FirebaseDatabase?=null
    private lateinit var binding:ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth= FirebaseAuth.getInstance()
        database=FirebaseDatabase.getInstance()
        databaseReference=database?.reference!!.child("Profile")
        registerUser()


    }
    private fun registerUser(){

        binding.signUpbtn.setOnClickListener{
            if(TextUtils.isEmpty(binding.firstnameTVSignUp.text.toString())){
                binding.firstnameTVSignUp.setError("Please enter your first name")
                return@setOnClickListener
            } else if(TextUtils.isEmpty(binding.lastnameTvSignUp.text.toString())){
                binding.firstnameTVSignUp.setError("Please enter your last name")
                return@setOnClickListener
            }else if(TextUtils.isEmpty(binding.emailTVSignUp.text.toString())){
                binding.firstnameTVSignUp.setError("Please enter your email-id")
                return@setOnClickListener
            } else if(TextUtils.isEmpty(binding.passwordTVSignUp.text.toString())){
                binding.firstnameTVSignUp.setError("Please enter your password")
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(binding.emailTVSignUp.text.toString(),binding.passwordTVSignUp.text.toString()).addOnCompleteListener{
                if(it.isSuccessful){
                    val currentUser=auth.currentUser
                    val a=0
                    val userDb=databaseReference?.child((currentUser?.uid!!))
                    userDb?.child("firstname")?.setValue(binding.firstnameTVSignUp.text.toString())
                    userDb?.child("lastname")?.setValue(binding.lastnameTvSignUp.text.toString())
                    userDb?.child("totalsteps")?.setValue(a.toInt())
                    userDb?.child("totalcalories")?.setValue(a.toInt())
                    userDb?.child("totaldistance")?.setValue(a.toInt())
                    Toast.makeText(this, "Sign-Up successful", Toast.LENGTH_SHORT).show()
                    finish()
                }
                else{
                    Toast.makeText(this, "Sign-Up failed, please try again", Toast.LENGTH_SHORT).show()
                }
            }

        }
        binding.alreadyHaveAccountTV.setOnClickListener{
            startActivity(Intent(this,SignInActivity::class.java))
        }

    }
}