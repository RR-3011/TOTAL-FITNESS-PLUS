package com.example.totalfitnessplus.AlarmModule

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.Ringtone
import android.media.RingtoneManager
import android.os.Build
import android.os.Vibrator
import android.widget.Toast
import androidx.annotation.RequiresApi


class AlarmReceiver_AM : BroadcastReceiver() {
    @RequiresApi(api = Build.VERSION_CODES.Q)  // implement onReceive() method
    override fun onReceive(context: Context, intent: Intent) {

        // we will use vibrator first
        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vibrator.vibrate(4000)
        Toast.makeText(context, "TIME FOR EXERCISE", Toast.LENGTH_LONG).show()
        var alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        if (alarmUri == null) {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        }

        // setting default ringtone
        val ringtone = RingtoneManager.getRingtone(context, alarmUri)

        // play ringtone
        ringtone.play()
    }
}