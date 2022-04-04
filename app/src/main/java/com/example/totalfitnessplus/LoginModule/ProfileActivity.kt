package com.example.totalfitnessplus.LoginModule

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.totalfitnessplus.R
import com.example.totalfitnessplus.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    lateinit var auth:FirebaseAuth
    var databaseReference:DatabaseReference?=null
    var database:FirebaseDatabase?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth= FirebaseAuth.getInstance()
        database= FirebaseDatabase.getInstance()
        databaseReference=database?.reference!!.child("Profile")
        profile()


    }
    private fun profile(){
        val user=auth.currentUser
        val userRef=databaseReference?.child(user?.uid!!)
        binding.emailTvProfile.text=user?.email
        userRef?.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                binding.firstnameTvProfile.text="First Name: "+snapshot.child("firstname").value.toString()
                binding.lastnameTvProfile.text="Last Name: "+snapshot.child("lastname").value.toString()
              binding.totalStepsProfile.text="Total steps: "+snapshot.child("totalsteps").value.toString()
              binding.totalCaloriesProfile.text="Total calories: "+snapshot.child("totalcalories").value.toString()
              binding.totalDistanceProfile.text="Total distance: "+snapshot.child("totaldistance").value.toString()

            }

            override fun onCancelled(error: DatabaseError) {

            }
            })

        binding.logoutbtnProfile.setOnClickListener{
            auth.signOut()
            startActivity(Intent(this,SignInActivity::class.java))
            finish()
        }

        binding.clearStatsProfile.setOnClickListener{
            val currentUser=auth.currentUser
            val userDb=databaseReference?.child((currentUser?.uid!!))
            userDb?.child("totalsteps")?.setValue(0)
            userDb?.child("totalcalories")?.setValue(0)
            userDb?.child("totaldistance")?.setValue(0)
        }
    }
}


