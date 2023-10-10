package com.gahov.data.source.local

import com.gahov.data.local.entities.CarDTO
import com.gahov.data.local.storage.cars.CarsDao

class LocalSourceImpl(
    private val carsDao: CarsDao
) : LocalSource {

    override suspend fun saveCar(data: CarDTO) {
        carsDao.insertItems(listOf(data))
    }

    override suspend fun getAllCarsData(): List<CarDTO> {
        return carsDao.getAll()
    }

    override suspend fun deleteCarDataByDigits(carDigits: String) {
        carsDao.deleteCarByDigits(carDigits)
    }
}