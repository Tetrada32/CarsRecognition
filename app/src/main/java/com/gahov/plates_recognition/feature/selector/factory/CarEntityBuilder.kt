package com.gahov.plates_recognition.feature.selector.factory

import com.gahov.domain.entity.cars.CarEntity
import com.gahov.plates_recognition.feature.selector.CarDisplayModel

/**
 * An interface that defines the contract for building a list of [CarDisplayModel] instances from a
 * list of [CarEntity] instances.
 */
interface CarEntityBuilder {

    /**
     * Builds a list of [CarDisplayModel] instances from the provided list of [CarEntity] instances.
     *
     * @param entityItems The list of [CarEntity] instances to be converted to [CarDisplayModel] instances.
     * @return The list of CityModel instances representing the data from the [CarEntity] instances.
     */
    fun buildCarModel(entityItems: List<CarEntity>): List<CarDisplayModel>
}