package il.example.weatherapp.data.models.new_api

data class CurrentItem(
    val temp_c:Double,
    val temp_f:String,
    val condition:ConditionItem,
    val wind_kph:Double,
    val humidity:Int,
    val feelslike_c:Double,
    val vis_km:Double

)
