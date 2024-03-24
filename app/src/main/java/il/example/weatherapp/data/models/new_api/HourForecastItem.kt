package il.example.weatherapp.data.models.new_api

data class HourForecastItem(
    val time:String,
    val temp_c:Double,
    val condition:ConditionItem,
    val precip_mm:Double,
)
