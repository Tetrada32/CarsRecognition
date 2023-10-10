package com.gahov.plates_recognition.feature.selector.factory

import android.content.Context
import com.gahov.domain.entity.cars.CarEntity
import com.gahov.plates_recognition.R
import com.gahov.plates_recognition.arch.ui.view.model.IconProvider
import com.gahov.plates_recognition.arch.ui.view.model.TextProvider
import com.gahov.plates_recognition.feature.selector.CarDisplayModel
import javax.inject.Inject

/**
 * A class responsible for building a list of CarDisplayModel instances from a list of CarEntity instances.
 */
class CarListToCarModelBuilder @Inject constructor(private val context: Context) :
    CarEntityBuilder {

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
                lastRegistrationDate = TextProvider.Text(it.getLastRegistrationDate()),
                carName = TextProvider.Text(it.getCarName()),
                stolenData = TextProvider.Text(it.getStolenData()),
                infoDate = TextProvider.Text(it.createCarSearchDateText()),
                cityAndYear = TextProvider.Text(it.createCityAndYearField())
            )
        }.distinctBy { it.digits }
    }

    private fun CarEntity.getCarName(): String {
        return "$vendor $model"
    }

    private fun CarEntity.createCityAndYearField(): String {
        return "${region?.nameUa}, $year"
    }

    private fun CarEntity.getStolenData(): String {
        return if (isStolen) context.getString(R.string.carIsStolen) else context.getString(R.string.carIsNotStolen)
    }

    private fun CarEntity.createCarSearchDateText(): String {
        return context.getString(
            R.string.field_car_search_date,
            digits,
            searchDate
        )
    }

    private fun CarEntity.getLastRegistrationDate(): String {
        return if (operations.isNullOrEmpty()) {
            context.getString(
                R.string.field_car_last_registration_date_not_found
            )
        } else {
            context.getString(
                R.string.field_car_last_registration_date,
                operations?.get(0)?.registrationDate
            )
        }
    }
}