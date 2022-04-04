package com.example.totalfitnessplus.FitnessModule


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.totalfitnessplus.databinding.ActivityBmifmBinding
import com.example.totalfitnessplus.databinding.ActivityFinalFmBinding
//import kotlinx.android.synthetic.main.activity_Final.*
import java.text.SimpleDateFormat
import java.util.*

class FinishActivity : AppCompatActivity() {
    private lateinit var binding:ActivityFinalFmBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityFinalFmBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.btnFinish.setOnClickListener {
            finish()
            val intent= Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        addDateToDatabase()
    }


    private fun addDateToDatabase() {

        val c = Calendar.getInstance() // Calender Current Instance
        val dateTime = c.time // Current Date and Time of the system.
        Log.e("Date : ", "" + dateTime) // Printed in the log.


        val sdf = SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.getDefault()) // Date Formatter
        val date = sdf.format(dateTime) // dateTime is formatted in the given format.
        Log.e("Formatted Date : ", "" + date) // Formatted date is printed in the log.

        // Instance of the Sqlite Open Helper class.
        val dbHandler = SqliteOpenHelper(this, null)
        dbHandler.addDate(date) // Add date function is called.
        Log.e("Date : ", "Added...") // Printed in log which is printed if the complete execution is done.
    }
}