package com.example.totalfitnessplus.FitnessModule

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.totalfitnessplus.R
import com.example.totalfitnessplus.databinding.ActivityExerciseFmBinding
//import kotlinx.android.synthetic.main.activity_exercise.*
//import kotlinx.android.synthetic.main.dialog_custom_back_confirmation.*
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener  {
    private lateinit var binding: ActivityExerciseFmBinding
    private var restTimer: CountDownTimer? =
        null
    private var restProgress =
        0

    private var exerciseTimer: CountDownTimer? = null
    private var exerciseProgress = 0

    private var exerciseList: ArrayList<ExerciseModel>? = null
    private var currentExercisePosition = -1

    private var tts: TextToSpeech? = null

    private var player: MediaPlayer? = null


    private var exerciseAdapter: ExerciseStatusAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityExerciseFmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarExerciseActivity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // Navigate the activity on click on back button of action bar.
        binding.toolbarExerciseActivity.setNavigationOnClickListener {
            customDialogForBackButton()

        }

        tts = TextToSpeech(this, this)

        exerciseList = Constants.defaultExerciseList()

        setupRestView()
        setupExerciseStatusRecyclerView()
    }


    public override fun onDestroy() {
        if (restTimer != null) {
            restTimer!!.cancel()
            restProgress = 0
        }

        if (exerciseTimer != null) {
            exerciseTimer!!.cancel()
            exerciseProgress = 0
        }

        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }

        if(player != null){
            player!!.stop()
        }
        super.onDestroy()
    }


    override fun onInit(status: Int) {

        if (status == TextToSpeech.SUCCESS) {
            // set US English as language for tts
            val result = tts!!.setLanguage(Locale.US)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "The Language specified is not supported!")
            }

        } else {
            Log.e("TTS", "Initialization Failed!")
        }
    }


    private fun setupRestView() {


        try {
            val soundURI =
                Uri.parse("android.resource://com.sevenminuteworkout/" + R.raw.press_start)
            player = MediaPlayer.create(applicationContext, soundURI)
            player!!.isLooping = false // Sets the player to be looping or non-looping.
            player!!.start() // Starts Playback.
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // Here according to the view make it visible as this is Rest View so rest view is visible and exercise view is not.
        binding.llRestView.visibility = View.VISIBLE
        binding.llExerciseView.visibility = View.GONE


        if (restTimer != null) {
            restTimer!!.cancel()
            restProgress = 0
        }


        binding.tvUpcomingExerciseName.text = exerciseList!![currentExercisePosition + 1].getName()


        setRestProgressBar()
    }


    private fun setRestProgressBar() {

        binding.progressBar.progress = restProgress // Sets the current progress to the specified value.



        restTimer = object : CountDownTimer(2000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                restProgress++ // It is increased to ascending order
                binding.progressBar.progress = 2 - restProgress // Indicates progress bar progress
                binding.tvTimer.text =
                    (2 - restProgress).toString()  // Current progress is set to text view in terms of seconds.
            }

            override fun onFinish() {
                currentExercisePosition++

                exerciseList!![currentExercisePosition].setIsSelected(true) // Current Item is selected
                exerciseAdapter!!.notifyDataSetChanged() // Notified the current item to adapter class to reflect it into UI.

                setupExerciseView()
            }
        }.start()
    }


    private fun setupExerciseView() {


        binding.llRestView.visibility = View.GONE
        binding.llExerciseView.visibility = View.VISIBLE


        if (exerciseTimer != null) {
            exerciseTimer!!.cancel()
            exerciseProgress = 0
        }


        binding.ivImage.setImageResource(exerciseList!![currentExercisePosition].getImage())
        binding.tvExerciseName.text = exerciseList!![currentExercisePosition].getName()

        speakOut(exerciseList!![currentExercisePosition].getName())

        setExerciseProgressBar()
    }


    private fun setExerciseProgressBar() {

        binding.progressBarExercise.progress = exerciseProgress

        exerciseTimer = object : CountDownTimer(3000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress++
                binding.progressBarExercise.progress = 3 - exerciseProgress
                binding.tvExerciseTimer.text = (3- exerciseProgress).toString()
            }

            override fun onFinish() {
                exerciseList!![currentExercisePosition].setIsSelected(false)
                exerciseList!![currentExercisePosition].setIsCompleted(true)
                exerciseAdapter!!.notifyDataSetChanged()

                if (currentExercisePosition < 11) {
                    setupRestView()
                } else {
                    val intent= Intent(baseContext, FinishActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(
                        this@ExerciseActivity,
                        "Congratulations! You have completed the 7 minutes workout.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }.start()
    }


    private fun speakOut(text: String) {
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }


    private fun setupExerciseStatusRecyclerView() {


        binding.rvExerciseStatus.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)


        exerciseAdapter = ExerciseStatusAdapter(exerciseList!!, this)


        binding.rvExerciseStatus.adapter = exerciseAdapter
    }

    private fun customDialogForBackButton() {
        val customDialog = Dialog(this)

        customDialog.setContentView(R.layout.dialog_custom_back_confirmation)
        customDialog.findViewById<Button>(R.id.tvYes).setOnClickListener {
            finish()
            customDialog.dismiss()
        }
        customDialog.findViewById<Button>(R.id.tvNo).setOnClickListener {
            customDialog.dismiss()
        }

        customDialog.show()
    }
}