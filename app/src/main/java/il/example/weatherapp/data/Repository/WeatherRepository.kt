package il.example.weatherapp.data.Repository

import il.example.weatherapp.data.local_db.FragmentsDao
import il.example.weatherapp.data.models.LocalModels.Favourites
import il.example.weatherapp.data.models.LocalModels.History
import il.example.weatherapp.data.remote_db.WeatherRemoteDataSource
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class WeatherRepository @Inject constructor(
    private val weatherRemoteDataSource : WeatherRemoteDataSource,
    private val localDao: FragmentsDao
) {

    //will be of type Response<AllData>
    suspend fun fetchWeather(string: String) = weatherRemoteDataSource.getWeather(string)


    fun getAllFavourites() = localDao.getAllFavourites()


    suspend fun deleteFavourite(favourite: Favourites) = localDao.deleteFavourite(favourite)


    suspend fun insertCity(city: Favourites) = localDao.insertCity(city)


    //History Entity

    suspend fun deleteHistory() = localDao.deleteHistory()

    suspend fun deleteSpecificHistory(city:History) = localDao.deleteSpecficHistory(city)

    fun getAllHistory()  = localDao.getAllHistory()

    suspend fun insertToHistory(city:History) = localDao.insertToHistory(city)
}