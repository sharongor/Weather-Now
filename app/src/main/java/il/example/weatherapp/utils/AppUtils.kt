package il.example.weatherapp.utils

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import il.example.weatherapp.R
import il.example.weatherapp.ui.MainActivity
import il.example.weatherapp.utils.receivers.AlarmManagerReceiver

class AppUtils(private val activity: AppCompatActivity) {

    companion object {
        private const val NOTIFICATION_ID = 123
        private const val NOTIFICATION_CHANNEL_ID = "10"
        private const val NOTIFICATION_CHANNEL_NAME = "NotificationChannel1"


        fun notify(
            context: Context,
            title: String,
            msg: String,
            icon: Int? = null
        ) {

            // Create an intent to launch your app's main activity
            val intent = Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }

            // Create a PendingIntent to start your app's main activity
            val pendingIntent = PendingIntent.getActivity(
                context,
                2,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )


            val build = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(msg)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent) // Set the Pending Intent
                .setAutoCancel(true)

            if (icon != null) {
                build.setSmallIcon(icon)
            } else {
                build.setSmallIcon(R.drawable.baseline_home_24)
            }

            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            )
            {
                NotificationManagerCompat.from(context).notify(NOTIFICATION_ID,build.build())
            }
        }

        fun createNotificationChannel(context: Context) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    NOTIFICATION_CHANNEL_ID,
                    NOTIFICATION_CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH
                )
                NotificationManagerCompat.from(context).createNotificationChannel(channel)
            }
        }

        //The notification will disappear as soon as we are lunching the app (if it was shown before )
        fun cancelNotification(context: Context) {
            NotificationManagerCompat.from(context).cancel(NOTIFICATION_ID)
        }
    }
}