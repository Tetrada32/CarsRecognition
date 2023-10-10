package com.gahov.plates_recognition.feature.selector.factory

import com.gahov.domain.entity.cars.CarEntity
import com.gahov.plates_recognition.feature.selector.CarDisplayModel

/**
 * An interface that defines the contract for building a list of CityModel instances from a
 * list of WeatherEntity instances.
 */
interface CarEntityBuilder {

    /**
     * Builds a list of CityModel instances from the provided list of WeatherEntity instances.
     *
     * @param entityItems The list of WeatherEntity instances to be converted to CityModel instances.
     * @return The list of CityModel instances representing the data from the WeatherEntity instances.
     */
    fun buildCarModel(entityItems: List<CarEntity>): List<CarDisplayModel>
}