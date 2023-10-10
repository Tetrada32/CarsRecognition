package com.gahov.plates_recognition.feature.selector


import com.gahov.domain.entity.cars.Operations
import com.gahov.plates_recognition.R
import com.gahov.plates_recognition.arch.ktx.getString
import com.gahov.plates_recognition.arch.ui.view.model.IconProvider
import com.gahov.plates_recognition.arch.ui.view.model.TextProvider
import java.io.Serializable


data class CarDisplayModel(
    var photoUrl: IconProvider? = IconProvider.ResIcon(R.drawable.ic_car),
    var isStolen: Boolean = false,
    var year: TextProvider? = null,
    var brand: TextProvider? = null,
    var digits: TextProvider? = null,
    var cityName: TextProvider? = null,
    var model: TextProvider? = null,
    var infoDate: TextProvider? = null,
    var operations: List<Operations?>? = null,
    val lastRegistrationDate: TextProvider? = null,
    val carIcon: IconProvider = IconProvider.ResIcon(R.drawable.ic_car),
    val historyIcon: IconProvider = IconProvider.ResIcon(R.drawable.ic_info)
) : Serializable {

    fun getCarDigits(): String {
        return digits?.getString().toString()
    }

    fun areItemsSame(model: CarDisplayModel): Boolean {
        return model.digits == digits
    }
}