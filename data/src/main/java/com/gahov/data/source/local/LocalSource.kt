package com.gahov.data.source.local

import com.gahov.data.local.entities.CarDTO

/**
 * An interface representing a local data source for loading car-related information.
 */
interface LocalSource {

    /**
     * Saves car data to the local data source.
     *
     * @param data The car data to be saved.
     */
    suspend fun saveCar(data: CarDTO)

    /**
     * Retrieves all cars data list from the local data source.
     *
     * @return A list of [CarDTO] containing weather information for the city.
     */
    suspend fun getAllCarsData(): List<CarDTO>

    /**
     * Deletes all data for a specific car from the local data source.
     *
     * @param carDigits The plates of the car for which to delete all the data.
     */
    suspend fun deleteCarDataByDigits(carDigits: String)
}