package il.example.weatherapp.data.local_db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import il.example.weatherapp.data.models.LocalModels.Favourites
import il.example.weatherapp.data.models.LocalModels.History


@Dao
interface FragmentsDao {

    @Query("SELECT * from favourites")
    fun getAllFavourites() :LiveData<List<Favourites>>

    @Query("SELECT * from favourites WHERE city=:city")
    fun getFavouriteByCity(city:String) :Favourites

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCity(city: Favourites)

    @Delete
    suspend fun deleteFavourite(fav:Favourites)

    @Query("DELETE from history")
     suspend fun deleteHistory()

    @Delete
    suspend fun deleteSpecficHistory(city: History)

    @Query("SELECT * from history")
    fun getAllHistory(): LiveData<List<History>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToHistory(city:History)

}