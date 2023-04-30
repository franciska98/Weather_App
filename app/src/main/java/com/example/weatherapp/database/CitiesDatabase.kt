package com.example.weatherapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.weatherapp.model.search.City

@Database(entities = [City::class], version = 1, exportSchema = false)
abstract class CitiesDatabase : RoomDatabase() {

    abstract fun citiesDao(): CitiesDao

    companion object {
        private var instance: CitiesDatabase? = null

        fun getDatabase(context: Context): CitiesDatabase? {
            if (instance == null) {
                instance = buildDatabase(context)
            }
            return instance
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            CitiesDatabase::class.java,
            "CitiesDb.db",
        ).build()
    }
}
