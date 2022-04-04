package com.example.totalfitnessplus.StepCountingModule

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.totalfitnessplus.MainActivity
import com.example.totalfitnessplus.R

import com.example.totalfitnessplus.databinding.ActivityOverallStepCountingStatsScmBinding
import com.example.totalfitnessplus.databinding.ActivityStatsScmBinding
import com.example.totalfitnessplus.databinding.ActivityUserStepsStatsScmBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.text.DecimalFormat
import kotlin.properties.Delegates


class StatsActivitySCM : AppCompatActivity() {
    override fun onBackPressed() {
// super.onBackPressed();
// Not calling **super**, disables back button in current screen.
    }
    private lateinit var binding:ActivityStatsScmBinding

    private  var steps_fb:Long= 0
    private  var calories_fb:Long= 0
    private  var distance_fb:Long= 0

    lateinit var auth: FirebaseAuth
    var databaseReference: DatabaseReference?=null
    var database: FirebaseDatabase?=null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityStatsScmBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.hide()

        auth= FirebaseAuth.getInstance()
        database=FirebaseDatabase.getInstance()
        databaseReference=database?.reference!!.child("Profile")


        val totalSteps = intent.getIntExtra("totalSteps", 0)
        val totalCalories = intent.getStringExtra("totalCalories")
        val totalDistance = intent.getStringExtra("totalDistance")
        val startTime = intent.getStringExtra("startTime")
        val endTime = intent.getStringExtra("endTime")

        binding.totalDistanceTVScm.text = ("DISTANCE COVERED: $totalDistance km")
        binding.totalStepsTVScm.text = ("TOTAL STEPS $totalSteps")
        binding.totalCaloriesTVScm.text = ("CALORIES BURNT: $totalCalories")
        binding.starttimeDateTVScm.text = ("START TIME: $startTime")
        binding.endtimeDateTVScm.text = ("END TIME: $endTime")

        val user=auth.currentUser
        val userRef=databaseReference?.child(user?.uid!!)
        userRef?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                steps_fb= snapshot.child("totalsteps").value as Long
                calories_fb= snapshot.child("totalcalories").value as Long
                distance_fb= snapshot.child("totaldistance").value as Long
//                    binding.lastnameTvProfile.text="Last Name: "+snapshot.child("lastname").value.toString()

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })


        binding.finalStatsBtn.setOnClickListener{
            val intent=Intent(this,StartActivitySCM::class.java)
            startActivity(intent)
        }
        binding.backToHomePagebtnScm.setOnClickListener{
            val intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
        }


        binding.statsstats.setOnClickListener{



            var step=steps_fb.toInt()
            var calories=calories_fb.toInt()
          //  var distance=distance_fb.toInt()
            var distance=distance_fb.toFloat()

          val firebaseSteps=step.toInt()+totalSteps.toInt()
          val firebaseCalories=calories.toInt()+ totalCalories!!.toInt()
            var dis=Math.ceil(totalDistance!!.toDouble())
//           var dis= totalDistance?.toFloat()

          val firebaseDistance=distance.toInt()+dis.toInt()



            if(totalSteps>=0){
            val currentUser=auth.currentUser
            val userDb=databaseReference?.child((currentUser?.uid!!))
            userDb?.child("totalsteps")?.setValue(firebaseSteps)
            userDb?.child("totalcalories")?.setValue(firebaseCalories)
           userDb?.child("totaldistance")?.setValue(firebaseDistance)
           startActivity(Intent(this,MainActivity::class.java))

            Toast.makeText(this, "data stored successfully", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))}


//           startActivity(Intent(this,MainActivity::class.java))

        }


    }
}






