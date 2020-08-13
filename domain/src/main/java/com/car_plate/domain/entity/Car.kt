package com.car_plate.domain.entity

import com.car_plate.domain.usecase.carplate.GetCarParams
import java.io.Serializable

data class Car(
    var photoUrl: String?,
    var isStolen: Boolean,
    var year: String,
    var vendor: String? = null,
    var digits: String? = null,
    var model: String? = null,
    var region: Region,
    var operations: List<Operations?>

) : Serializable

fun Car.toParams() : GetCarParams {
    return GetCarParams(
        carPlate = digits ?: ""
    )
}