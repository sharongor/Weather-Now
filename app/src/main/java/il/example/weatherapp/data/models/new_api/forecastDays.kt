package il.example.weatherapp.data.models.new_api

data class forecastDays(
    val maxtemp_c:Double,
    val mintemp_c:Double,
    val totalprecip_mm:Double,
    val condition:ConditionItem,
    val avgtemp_c:Double
)
