package com.example.totalfitnessplus

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.totalfitnessplus.LoginModule.ProfileActivity

import com.example.totalfitnessplus.databinding.ActivityMainBinding
import com.example.totalfitnessplus.databinding.ActivityStartFmBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.exerciseBtn.setOnClickListener{
            var intent=Intent(this,com.example.totalfitnessplus.FitnessModule.MainActivity::class.java)
            startActivity(intent)

        }
        binding.stepcountingBtn.setOnClickListener{
            var intent=Intent(this,com.example.totalfitnessplus.StepCountingModule.StartActivitySCM::class.java)
            startActivity(intent)

        }
 binding.ProfileBtn.setOnClickListener{
     startActivity(Intent(this,ProfileActivity::class.java))
 }




    }
}