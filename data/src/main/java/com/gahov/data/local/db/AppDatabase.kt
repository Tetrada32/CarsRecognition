package com.gahov.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gahov.data.local.entities.CarDTO
import com.gahov.data.local.db.AppDatabase.Companion.DB_VERSION
import com.gahov.data.local.storage.cars.CarsDao

/**
 * An abstract Room database class representing the application's database.
 *
 * @see Database
 */
@Database(
    entities = [CarDTO::class],
    version = DB_VERSION
)

abstract class AppDatabase : RoomDatabase() {

    /**
     * Provides access to the [CarsDao] for database operations related to cars data.
     *
     * @return An instance of the [CarsDao] interface.
     */
    abstract fun carsDao(): CarsDao

    /**
     * A companion object containing constants and configuration for the database.
     */
    companion object {

        /**
         * The version of the database.
         * Should be increased every time the developer changes DTO or DB config.
         */
        const val DB_VERSION = 1

        /**
         * The name of the database file.
         */
        var DB_NAME = "car_plates_recognition.db"
    }
}