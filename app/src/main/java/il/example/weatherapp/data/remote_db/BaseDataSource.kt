package il.example.weatherapp.data.remote_db


import il.example.weatherapp.utils.Resource
import retrofit2.Response

abstract class BaseDataSource {

    protected suspend fun <T>
            getResult(call : suspend () -> Response<T>) : Resource<T> {

        try {
            val result  = call()
            if(result.isSuccessful) {
                val body = result.body()
                if(body != null) return  Resource.success(body)
            }
            return Resource.error(1)// Network call has failed
        }catch (e : Exception) {
            return Resource.error(2)// Some exception caught
        }
    }
}