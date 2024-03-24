package il.example.weatherapp.data.models.new_api

data class forecastday(
    val date:String,
    val date_epoch:String,
    val day:forecastDays,
    val astro:AstroItem,
    val hour:List<HourForecastItem>
)
