package il.example.weatherapp.utils.WorkManager

import android.Manifest
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.tasks.Tasks
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import il.example.weatherapp.R
import il.example.weatherapp.data.Repository.WeatherRepository
import il.example.weatherapp.data.models.new_api.forecastday
import il.example.weatherapp.utils.AppUtils
import il.example.weatherapp.utils.Success
import il.example.weatherapp.utils.receivers.AlarmManagerReceiver
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Calendar

//We've used this guide https://medium.com/@jeremy.leyvraz/the-art-of-integrating-hilt-dependency-injection-with-workers-for-harmonious-android-development-28bdc21be047


@HiltWorker
class WeatherWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val repository: WeatherRepository,
    private val fusedLocationClient: FusedLocationProviderClient
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {

        return try {
            // Check for permission explicitly before accessing location
            if (ActivityCompat.checkSelfPermission(
                    applicationContext,
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                var nextDayWeather : Any = Any()

                //Permission is granted, proceed to fetch location
                val locationResult = fusedLocationClient.lastLocation
                val taskResult = Tasks.await(locationResult)
                val lat = taskResult.latitude
                val long = taskResult.longitude
                val result = repository.fetchWeather("$lat,$long")
                if (result.status is Success) {
                    val currentDate = Calendar.getInstance()

                    // Add 1 day to the current date
                    currentDate.add(Calendar.DAY_OF_YEAR, 1)
                    val nextDate = currentDate.time

                    // Format and print the next date
                    val dateFormat = SimpleDateFormat(applicationContext.getString(R.string.date_format_untranslatable))
                    var nextDateStr = dateFormat.format(nextDate)
                    val forecastDayResult = result.status.data!!.forecast.forecastday
                    for (dayWeek in forecastDayResult){
                        if(dayWeek.date.contains(nextDateStr)){
                            nextDayWeather = dayWeek
                            break
                        }
                    }

                    if(nextDayWeather is forecastday){// matching weather code to it's condition's text
                        val nextDayWeatherString = when (nextDayWeather.day.condition.code) {
                            1000 -> { // 1000 can be both Clear or Sunny , an inside term is needed
                                if(nextDayWeather.day.condition.text == applicationContext.getString(R.string.clear_untranslatable)){
                                    applicationContext.getString(R.string.clear)
                                }
                                else{
                                    applicationContext.getString(R.string.sunny)
                                }
                            }
                            1003 -> applicationContext.getString(R.string.partly_cloudy)
                            1006 -> applicationContext.getString(R.string.cloudy)
                            1009 -> applicationContext.getString(R.string.overcast)
                            1030 -> applicationContext.getString(R.string.mist)
                            1063 -> applicationContext.getString(R.string.patchy_rain_possible)
                            1066 -> applicationContext.getString(R.string.patchy_snow_possible)
                            1069 -> applicationContext.getString(R.string.patchy_sleet)
                            1072 -> applicationContext.getString(R.string.patchy_freezing_drizzle)
                            1087 -> applicationContext.getString(R.string.thundery_outbreaks_possible)
                            1114 -> applicationContext.getString(R.string.blowing_snow)
                            1117 -> applicationContext.getString(R.string.blizzard)
                            1135 -> applicationContext.getString(R.string.fog)
                            1147 -> applicationContext.getString(R.string.freezing_fog)
                            1150 -> applicationContext.getString(R.string.patchy_light_drizzle)
                            1153 -> applicationContext.getString(R.string.light_drizzle)
                            1168 -> applicationContext.getString(R.string.freezing_drizzle)
                            1171 -> applicationContext.getString(R.string.heavy_freezing_drizzle)
                            1180 -> applicationContext.getString(R.string.patchy_light_rain)
                            1183 -> applicationContext.getString(R.string.light_rain)
                            1186 -> applicationContext.getString(R.string.moderate_rain_at_times)
                            1189 -> applicationContext.getString(R.string.moderate_rain)
                            1192 -> applicationContext.getString(R.string.heavy_rain_at_times)
                            1195 -> applicationContext.getString(R.string.heavy_rain)
                            1198 -> applicationContext.getString(R.string.light_freezing_rain)
                            1201 -> applicationContext.getString(R.string.moderate_or_heavy_freezing_rain)
                            1204 -> applicationContext.getString(R.string.light_sleet)
                            1207 -> applicationContext.getString(R.string.moderate_or_heavy_sleet)
                            1210 -> applicationContext.getString(R.string.patchy_light_snow)
                            1213 -> applicationContext.getString(R.string.light_snow)
                            1216 -> applicationContext.getString(R.string.patchy_moderate_snow)
                            1219 -> applicationContext.getString(R.string.moderate_snow)
                            1222 -> applicationContext.getString(R.string.patchy_heavy_snow)
                            1225 -> applicationContext.getString(R.string.heavy_snow)
                            1237 -> applicationContext.getString(R.string.ice_pellets)
                            1240 -> applicationContext.getString(R.string.light_rain_shower)
                            1243 -> applicationContext.getString(R.string.moderate_or_heavy_rain_shower)
                            1246 -> applicationContext.getString(R.string.torrential_rain_shower)
                            1249 -> applicationContext.getString(R.string.light_sleet_showers)
                            1252 -> applicationContext.getString(R.string.moderate_or_heavy_sleet_showers)
                            1255 -> applicationContext.getString(R.string.light_snow_showers)
                            1258 -> applicationContext.getString(R.string.moderate_or_heavy_snow_showers)
                            1261 -> applicationContext.getString(R.string.light_showers_of_ice_pellets)
                            1264 -> applicationContext.getString(R.string.moderate_or_heavy_showers_of_ice_pelltes)
                            1273 -> applicationContext.getString(R.string.patchy_light_rain_with_thunder)
                            1276 -> applicationContext.getString(R.string.moderate_or_heavy_rain_with_thunder)
                            1279 -> applicationContext.getString(R.string.patchy_light_snow_with_thunder)
                            1282 -> applicationContext.getString(R.string.moderate_or_heavy_snow_with_thunder)
                            else -> nextDayWeather.day.condition.text
                        }

                        AppUtils.notify(applicationContext,applicationContext.getString(R.string.tomorrow_s_weather),
                            applicationContext.getString(R.string.weather_status)+ nextDayWeatherString +"\n"+
                                    applicationContext.getString(R.string.average_temperature)  +"${nextDayWeather.day.avgtemp_c.toInt()}",
                            R.mipmap.ic_launcher_weather_round)
                    }

                    Result.success()
                } else {
                    Result.retry() // for cases of lost internet connection etc
                }
            } else {
                // Permission is not granted
                AppUtils.notify(applicationContext,applicationContext.getString(R.string.error),applicationContext.getString(R.string.allow_all_the_time))
                Result.failure()
            }
        }
        catch (e: Exception) {
            Result.failure()
        }
    }
}


