package com.gahov.data.local.storage.cars

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gahov.data.local.entities.CarDTO

/**
 * Data Access Object (DAO) interface for managing cars data in the database (local storage).
 */

@Dao
interface CarsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItems(carsList: List<CarDTO>)

    @Query("SELECT * FROM carData")
    fun getAll(): List<CarDTO>

    @Query("SELECT * FROM carData WHERE digits = :carDigits")
    fun getCarByDigits(carDigits: String): List<CarDTO>

    @Query("DELETE FROM carData")
    fun deleteAll()

    @Query("DELETE FROM carData WHERE digits = :carDigits")
    fun deleteCarByDigits(carDigits: String)

}