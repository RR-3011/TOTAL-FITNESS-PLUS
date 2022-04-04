package com.example.totalfitnessplus.FitnessModule


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.totalfitnessplus.AlarmModule.AlarmActivity
import com.example.totalfitnessplus.databinding.ActivityStartFmBinding


class MainActivity : AppCompatActivity() {
    override fun onBackPressed() {

    }

    private lateinit var binding: ActivityStartFmBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding= ActivityStartFmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.llStart.setOnClickListener {
            val intent = Intent(this, ExerciseActivity::class.java)
            startActivity(intent)
        }
        binding.backToMenuFm.setOnClickListener{
            startActivity(Intent(this,com.example.totalfitnessplus.MainActivity::class.java))
        }

        binding.llBMI.setOnClickListener {

            val intent = Intent(this, BMIActivity::class.java)
            startActivity(intent)
        }

        binding.llHistory.setOnClickListener {

            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }
        binding.alarmBtnFm.setOnClickListener{
            startActivity(Intent(this,AlarmActivity::class.java))
        }
    }
}