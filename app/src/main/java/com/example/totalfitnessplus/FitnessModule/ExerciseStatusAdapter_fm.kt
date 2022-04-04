package com.example.totalfitnessplus.FitnessModule

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.totalfitnessplus.R


class ExerciseStatusAdapter(val items: ArrayList<ExerciseModel>, val context: Context) :
    RecyclerView.Adapter<ExerciseStatusAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_exercise_status,
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val model: ExerciseModel = items[position]

        holder.tvItem.text = model.getId().toString()


        if (model.getIsSelected()) {
            holder.tvItem.background =
                ContextCompat.getDrawable(
                    context,
                    R.drawable.item_circular_thin_color_accent_border
                )
            holder.tvItem.setTextColor(Color.parseColor("#212121")) // Parse the color string, and return the corresponding color-int.
        } else if (model.getIsCompleted()) {
            holder.tvItem.background =
                ContextCompat.getDrawable(context, R.drawable.item_circular_color_accent_background)
            holder.tvItem.setTextColor(Color.parseColor("#FFFFFF"))
        } else {
            holder.tvItem.background =
                ContextCompat.getDrawable(context, R.drawable.item_circular_color_gray_background)
            holder.tvItem.setTextColor(Color.parseColor("#212121"))
        }
    }

    /**
     * Gets the number of items in the list
     */
    override fun getItemCount(): Int {
        return items.size
    }

    /**
     * A ViewHolder describes an item view and metadata about its place within the RecyclerView.
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Holds the TextView that will add each item to
        val tvItem = view.findViewById<TextView>(R.id.tvItem)!!
    }
}