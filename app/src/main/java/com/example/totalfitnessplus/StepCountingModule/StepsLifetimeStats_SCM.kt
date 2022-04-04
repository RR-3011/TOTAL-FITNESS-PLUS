package com.example.totalfitnessplus.StepCountingModule

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.totalfitnessplus.databinding.ActivityStepsLifetimeStatsScmBinding

class StepsLifetimeStats_SCM : AppCompatActivity() {
    private lateinit var binding:ActivityStepsLifetimeStatsScmBinding
    @SuppressLint("SetTextI18n")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityStepsLifetimeStatsScmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var totalSteps = intent.getIntExtra("totalSteps", 0)
        val totalCalories = intent.getStringExtra("totalCalories")
        val totalDistance = intent.getStringExtra("totalDistance")
//        val startTime = intent.getStringExtra("startTime")
//        val endTime = intent.getStringExtra("endTime")

        binding.button.setOnClickListener{
         val sharedPrefFile = "kotlinsharedpreference"

            val sharedPreferences: SharedPreferences = this.getSharedPreferences(sharedPrefFile,Context.MODE_PRIVATE)
            val finalSteps = sharedPreferences.getInt("steps",0)
        val finalCal = sharedPreferences.getInt("cal",0)
        val finalDis = sharedPreferences.getInt("dis",0)



        binding.totalDistanceLTTv.text  = ("DISTANCE COVERED: $finalDis km" )
      binding.totalStepsLTTv.text = ("TOTAL STEPS ${finalSteps}")
       binding.totalcaloriesLTTv.text = ("CALORIES BURNT: $finalCal")}
//        binding.starttimeDateTVScm.text = ("START TIME: $startTime")
//        binding.endtimeDateTVScm.text = ("END TIME: $endTime")
    }
}