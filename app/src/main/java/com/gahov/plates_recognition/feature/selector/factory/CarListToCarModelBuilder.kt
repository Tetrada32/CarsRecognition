package com.gahov.plates_recognition.feature.selector.factory

import com.gahov.domain.entity.cars.CarEntity
import com.gahov.plates_recognition.arch.ui.view.model.IconProvider
import com.gahov.plates_recognition.arch.ui.view.model.TextProvider
import com.gahov.plates_recognition.feature.selector.CarDisplayModel

/**
 * A class responsible for building a list of CarDisplayModel instances from a list of CarEntity instances.
 */
class CarListToCarModelBuilder : CarEntityBuilder {

    /**
     * Builds a list of CarDisplayModel instances from the provided list of WeatherEntity instances.
     *
     * @param entityItems The list of CarEntity instances to be converted to CarDisplayModel instances.
     * @return The list of CarDisplayModel instances representing the data from the CarEntity instances.
     */
    override fun buildCarModel(entityItems: List<CarEntity>): List<CarDisplayModel> {
        return entityItems.map {
            CarDisplayModel(
                digits = TextProvider.Text(it.digits.toString()),
                cityName = TextProvider.Text(it.region?.nameUa.toString()),
                photoUrl = IconProvider.Url(it.photoUrl.toString()),
                isStolen = it.isStolen,
                year = TextProvider.Text(it.year),
                brand = TextProvider.Text(it.vendor.toString()),
                model = TextProvider.Text(it.model.toString()),
                operations = it.operations,
                lastRegistrationDate = TextProvider.Text(it.operations?.last()?.registrationDate.toString())
            )
        }.distinctBy { it.cityName }
    }
}