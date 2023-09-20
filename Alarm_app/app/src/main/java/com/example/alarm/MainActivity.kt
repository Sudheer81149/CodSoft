package com.example.alarm
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var timePicker: TimePicker
    private lateinit var setAlarmButton: Button
    private lateinit var stopAlarmButton: Button
    private lateinit var alarmManager: AlarmManager
    private lateinit var alarmIntent: PendingIntent
    var ringtone: Ringtone? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        timePicker = findViewById(R.id.time_set)
        setAlarmButton = findViewById(R.id.set_alarm)
        stopAlarmButton = findViewById(R.id.stop_alarm)

        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlarmReceiver::class.java)
        alarmIntent = PendingIntent.getBroadcast(this, 1, intent, 0 or PendingIntent.FLAG_IMMUTABLE)

        setAlarmButton.setOnClickListener {
            val hour = timePicker.hour
            val minute = timePicker.minute

            val calendar: Calendar = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, hour)
                set(Calendar.MINUTE, minute)
                set(Calendar.SECOND, 0)
            }

            val alarmTime: Long = calendar.timeInMillis

            alarmManager.set(AlarmManager.RTC_WAKEUP, alarmTime, alarmIntent)

            Toast.makeText(
                this,
                "Alarm set for $hour:$minute",
                Toast.LENGTH_SHORT
            ).show()
        }

        stopAlarmButton.setOnClickListener {
            alarmManager.cancel(alarmIntent)
            AlarmPlayer.ringtone?.stop()
            Toast.makeText(this, "Alarm canceled", Toast.LENGTH_SHORT).show()
        }
    }
}