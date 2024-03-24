package il.example.weatherapp.utils.receivers


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import il.example.weatherapp.R
import il.example.weatherapp.utils.WorkManager.WeatherWorker
import java.util.concurrent.TimeUnit


class  AlarmManagerReceiver : BroadcastReceiver() {


    override fun onReceive(context: Context?, intent: Intent?) {

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val repeatInterval = 24L // 24 hours

        val periodicWorkRequest = PeriodicWorkRequestBuilder<WeatherWorker>(
            repeatInterval,
            TimeUnit.HOURS
        )
            .addTag(context!!.getString(R.string.periodicuniquejob1)) // Adding a tag if needed
            .setBackoffCriteria(BackoffPolicy.LINEAR,10L,TimeUnit.MINUTES)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(context!!).enqueueUniquePeriodicWork(
            context!!.getString(R.string.periodicuniquejob1),
            ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE,
            periodicWorkRequest
        )
    }
}