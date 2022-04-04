package com.example.totalfitnessplus.FitnessModule

import android.os.Bundle
import android.view.View
import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.totalfitnessplus.databinding.ActivityBmifmBinding
import com.example.totalfitnessplus.databinding.ActivityExerciseFmBinding
import com.example.totalfitnessplus.databinding.ActivityExerciseHistoryFmBinding
//import kotlinx.android.synthetic.main.activity_exercise_history_fm.*

class HistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityExerciseHistoryFmBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityExerciseHistoryFmBinding.inflate(layoutInflater)
        setContentView(binding.root)


//        supportActionBar?.setDisplayHomeAsUpEnabled(true) //set back button
//        supportActionBar?.title = "HISTORY" // Setting an title in the action bar.

//        binding.toolbarHistoryActivity.setNavigationOnClickListener {
//            onBackPressed()
//        }

        getAllCompletedDates()
    }

    /**
     * Function is used to get the list exercise completed dates.
     */
    private fun getAllCompletedDates() {

        // Instance of the Sqlite Open Helper class.
        val dbHandler = SqliteOpenHelper(this, null)

        val allCompletedDatesList =
            dbHandler.getAllCompletedDatesList() // List of history table data

        // TODO(Step 3 : Now here the dates which were printed in log.
        //  We will pass that list to the adapter class which we have created and bind it to the recycler view.)
        // START
        if (allCompletedDatesList.size > 0) {
            // Here if the List size is greater then 0 we will display the item in the recycler view or else we will show the text view that no data is available.
            binding.tvHistory.visibility = View.VISIBLE
            binding.rvHistory.visibility = View.VISIBLE
            binding.tvNoDataAvailable.visibility = View.GONE

            // Creates a vertical Layout Manager
            binding.rvHistory.layoutManager = LinearLayoutManager(this)

            // History adapter is initialized and the list is passed in the param.
            val historyAdapter = HistoryAdapter(this, allCompletedDatesList)

            // Access the RecyclerView Adapter and load the data into it
            binding.rvHistory.adapter = historyAdapter
        } else {
            binding.tvHistory.visibility = View.GONE
            binding.rvHistory.visibility = View.GONE
            binding.tvNoDataAvailable.visibility = View.VISIBLE
        }
        // END
    }
}