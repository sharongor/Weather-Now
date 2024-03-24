package il.example.weatherapp.data.remote_db

import javax.inject.Inject

class WeatherRemoteDataSource @Inject constructor(
    private val weatherService: weatherService
) : BaseDataSource() {

    //get result gets the suspend function getAddress and will wrap our Retrofit response with Resource we have made(success, failure, loading)
    suspend fun getWeather(latlong:String) = getResult { weatherService.getAddress(latlong) }


}