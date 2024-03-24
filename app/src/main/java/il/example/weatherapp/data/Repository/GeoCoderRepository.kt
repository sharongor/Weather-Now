package il.example.weatherapp.data.Repository

import android.location.Address
import android.location.Geocoder
import il.example.weatherapp.data.models.coordinates.MyLatLong
import il.example.weatherapp.utils.Resource
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class GeoCoderRepository @Inject constructor(
    private val geocoder: Geocoder
) {
    fun cityToLongLat(city : String) : Resource<MyLatLong> {
        try{
            val result : MutableList<Address>? = geocoder.getFromLocationName(city,1)
            if (!result.isNullOrEmpty()) {
                val myLatLong = MyLatLong()
                myLatLong.long = result?.get(0)?.longitude
                myLatLong.lat = result?.get(0)?.latitude
                myLatLong.city = result?.get(0)?.getAddressLine(0).toString()
                return Resource.success(myLatLong)
            }
            return Resource.error(3)// No such city!
        }

        catch (e:Exception){
            return Resource.error(1)// network call has failed
        }
    }


    //longitude,latitude
    fun longLatToCity(longLat:String) : Resource<MyLatLong>{
        try{
            val lat = longLat.split(",")[0].toDouble()
            val long = longLat.split(",")[1].toDouble()
            val addresses : MutableList<Address>? = geocoder.getFromLocation(lat,long,1)
            if(addresses!=null){
                val myLatLong = MyLatLong()
                myLatLong.long = long
                myLatLong.lat = lat
                myLatLong.city = addresses.get(0).locality
                return Resource.success(myLatLong)
            }
            return Resource.error(4)// No cities found with that longitude and latitude
        }
        catch (e:Exception){
            return Resource.error(2)// Some exception caught
        }
    }
}