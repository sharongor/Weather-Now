package il.example.weatherapp.data.models.LocalModels

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "History")
data class History(@PrimaryKey val city:String)

