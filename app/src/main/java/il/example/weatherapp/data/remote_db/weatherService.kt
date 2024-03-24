package il.example.weatherapp.data.remote_db

import il.example.weatherapp.data.models.new_api.AllDataNew
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

//8c577b5ea692409eb73134700242202 -> ney api key

//72d79f425b38486ab3c53843243001 -> old api key
interface weatherService {
    @GET("forecast.json")
    suspend fun getAddress(
        @Query("q") lat_long: String,
        @Query("key") appId:String = "8c577b5ea692409eb73134700242202",
        @Query("days") days:Int = 7
    ): Response<AllDataNew>

}