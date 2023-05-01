package com.example.weatherapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.weatherapp.model.forecast.Location

@Database(entities = [Location::class], version = 1, exportSchema = false)
abstract class HistoryDatabase : RoomDatabase() {
    abstract fun historyDao(): LocationDao

    companion object {
        private var instance: HistoryDatabase? = null

        fun getDatabase(context: Context): HistoryDatabase? {
            if (instance == null) {
                instance = buildDatabase(context)
            }
            return instance
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            HistoryDatabase::class.java,
            "HistoryDb.db",
        ).build()
    }
}
