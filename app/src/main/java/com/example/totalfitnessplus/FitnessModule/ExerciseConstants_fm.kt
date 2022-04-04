package com.example.totalfitnessplus.FitnessModule

import com.example.totalfitnessplus.R
import java.util.*

/**
 * Constant Class where you can add the constant values of the project.
 */
class Constants {
    companion object {

        /**
         * Here we are adding all exercises to a single list with all the default values.
        // The drawable images used here is added in the drawable folder.
         */
        fun defaultExerciseList(): ArrayList<ExerciseModel> {

            val exerciseList = ArrayList<ExerciseModel>()

            val jumpingJacks =
                ExerciseModel(1, "Jumping Jacks", R.drawable.jumping_jacks, false, false)
            exerciseList.add(jumpingJacks)

            val wallSit = ExerciseModel(2, "Wall Sit", R.drawable.wall_sit, false, false)
            exerciseList.add(wallSit)

            val pushUp = ExerciseModel(3, "Push Up", R.drawable.pushup, false, false)
            exerciseList.add(pushUp)

            val abdominalCrunch =
                ExerciseModel(4, "Abdominal Crunch", R.drawable.abdominal_crunch, false, false)
            exerciseList.add(abdominalCrunch)

            val stepUpOnChair =
                ExerciseModel(
                    5,
                    "Step-Up onto Chair",
                    R.drawable.step_up_on_chair,
                    false,
                    false
                )
            exerciseList.add(stepUpOnChair)

            val squat = ExerciseModel(6, "Squat", R.drawable.squat, false, false)
            exerciseList.add(squat)

            val tricepDipOnChair =
                ExerciseModel(
                    7,
                    "Tricep Dip On Chair",
                    R.drawable.tricep_dip_on_chair,
                    false,
                    false
                )
            exerciseList.add(tricepDipOnChair)

            val plank = ExerciseModel(8, "Plank", R.drawable.plank, false, false)
            exerciseList.add(plank)

            val highKneesRunningInPlace =
                ExerciseModel(
                    9, "High Knees Running In Place",
                    R.drawable.high_knees_runnig_in_pace,
                    false,
                    false
                )
            exerciseList.add(highKneesRunningInPlace)

            val lunges = ExerciseModel(10, "Lunges", R.drawable.lunges, false, false)
            exerciseList.add(lunges)

            val pushupAndRotation =
                ExerciseModel(
                    11,
                    "Push up and Rotation",
                    R.drawable.pushupandrotation,
                    false,
                    false
                )
            exerciseList.add(pushupAndRotation)

            val sidePlank = ExerciseModel(12, "Side Plank", R.drawable.side_plank, false, false)
            exerciseList.add(sidePlank)

            return exerciseList
        }
    }
}