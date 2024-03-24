package il.example.weatherapp.data.models.LocalModels

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "favourites")
data class Favourites (
    @PrimaryKey
    val city: String
)