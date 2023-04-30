package com.example.weatherapp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherapp.model.search.City

@Dao
interface CitiesDao {

    @Query("SELECT * FROM city")
    suspend fun getAllCitiesFromDb(): List<City>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCity(city: City)

    @Delete
    suspend fun deleteCity(city: City)
}
