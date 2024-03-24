package il.example.weatherapp.data.models.new_api

data class LocationItem(
    val lat:Double,
    val lon:Double,
    val localtime:String,
    val localtime_epoch:String,
    val country:String
)
