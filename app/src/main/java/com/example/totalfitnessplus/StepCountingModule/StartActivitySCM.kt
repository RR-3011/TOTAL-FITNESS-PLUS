package com.example.totalfitnessplus.StepCountingModule

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
//import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.totalfitnessplus.MainActivity
import com.example.totalfitnessplus.StepCountingModule.Listener.StepListener
import com.example.totalfitnessplus.StepCountingModule.utils.StepDetector
import com.example.totalfitnessplus.R
import com.example.totalfitnessplus.databinding.ActivityStartScmBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.DecimalFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle




class StartActivitySCM : AppCompatActivity(), SensorEventListener, StepListener {

    private var startTimeDate:String?=null
    private lateinit var binding:ActivityStartScmBinding
    lateinit var auth: FirebaseAuth
    var databaseReference: DatabaseReference?=null
    var database: FirebaseDatabase?=null
    private var simpleStepDetector: StepDetector? = null
    private var sensorManager: SensorManager? = null
    private val TEXT_NUM_STEPS = "Number of Steps: "
    private var numSteps: Int = 0
    override fun onBackPressed() {

    }



    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event!!.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            simpleStepDetector!!.updateAccelerometer(event.timestamp, event.values[0], event.values[1], event.values[2])
        }
    }
    override fun step(timeNs: Long) {
        numSteps++
        findViewById<TextView>(R.id.tvSteps).text = TEXT_NUM_STEPS.plus(numSteps)
    }



    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityStartScmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        auth= FirebaseAuth.getInstance()
        database=FirebaseDatabase.getInstance()
        databaseReference=database?.reference!!.child("Profile")

//        setSupportActionBar(binding.toolbarStartActivityScm)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true) //set back button
//        supportActionBar?.title = "CALCULATE BMI" // Setting an title in the action bar.

        // Get an instance of the SensorManager
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        simpleStepDetector = StepDetector()
        simpleStepDetector!!.registerListener(this)

        binding.btnStart.setOnClickListener(View.OnClickListener {
            binding.appLogoIvScm.visibility=View.INVISIBLE
            binding.showStatsButtonScm.visibility=View.INVISIBLE
            numSteps = 0
            binding.linearStatsScm.visibility=View.INVISIBLE
            sensorManager!!.registerListener(this, sensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_FASTEST)
            val currentDateTime = LocalDateTime.now()
            val start= currentDateTime?.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT))
            startTimeDate= start
        })


        binding.mainMenuScm.setOnClickListener{
            val intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
        binding.btnStop.setOnClickListener(View.OnClickListener {
            sensorManager!!.unregisterListener(this)
            binding.linearStatsScm.visibility=View.VISIBLE
            binding.showStatsButtonScm.visibility=View.VISIBLE
            //binding.totalStepsTVScm.text=("TOTAL STEPS: $numSteps")
            var distance:Double=numSteps/1000.toDouble()
            var calories:Double= numSteps/8.30
            val caloriesBurnt: Long =  Math.round(calories * 100) / 100
//            val distanceCovered: Long =  Math.round((distance * 100).toDouble()) / 100
            //val distanceCovered= DecimalFormat("##.##").format(distance)
            val distanceCovered=String.format("%.2f", distance)
            val currentDateTime = LocalDateTime.now()
            var endTimeDate= currentDateTime?.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT))

            binding.showStatsButtonScm.setOnClickListener{



                val intent=Intent(this,StatsActivitySCM::class.java)
                intent.putExtra("totalSteps",numSteps)
                intent.putExtra("totalCalories",caloriesBurnt.toString())
                intent.putExtra("totalDistance",distanceCovered)
                intent.putExtra("startTime",startTimeDate)
                intent.putExtra("endTime",endTimeDate)

                startActivity(intent)}









        })

    }


}
