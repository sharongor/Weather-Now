package il.example.weatherapp.data.models.new_api

data class AllDataNew(
    val location:LocationItem,
    val current: CurrentItem,
    val forecast:ForecastItem
)
