package com.example.weatherapp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherapp.model.forecast.Location

@Dao
interface LocationDao {
    @Query("SELECT * FROM location")
    suspend fun getAllLocationsFromDb(): List<Location>

    @Query("SELECT * FROM location WHERE name LIKE :city_name")
    suspend fun getLocationByName(city_name: String): List<Location>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(location: Location)

    @Delete
    suspend fun deleteLocation(location: Location)
}
