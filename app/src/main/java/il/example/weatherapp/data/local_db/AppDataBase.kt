package il.example.weatherapp.data.local_db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import il.example.weatherapp.data.models.LocalModels.Favourites
import il.example.weatherapp.data.models.LocalModels.History


@Database(entities = [Favourites::class,History::class], version = 1, exportSchema = false)
abstract class AppDataBase() : RoomDatabase() {

    abstract fun favouriteDao() :FragmentsDao

    companion object{
        @Volatile
        private var instance: AppDataBase? = null


        fun getDataBase(context:Context) :AppDataBase{
            return instance?: synchronized(this){
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "favourites_db"
                )
                    .fallbackToDestructiveMigration().build().also {
                        instance = it
                    }
            }
        }
    }
}