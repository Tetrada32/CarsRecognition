package com.car_plate.domain.entity

import com.car_plate.domain.usecase.carplate.GetCarParams
import java.io.Serializable


data class Car(
    var photoUrl: String?,
    var isStolen: Boolean?,
    var year: String,
    var vendor: String? = null,
    var digits: String? = null,
    var model: String? = null,
//    var region: ApiCarRegion,
//    var operations: List<ApiCarOperations?>,

    //TODO deal with models
    //operations
    var registrationDate: String? = null,
    var notes: String? = null,
    var address: String? = null,
    var isLast: Boolean? = null,
    var modelYear: Int? = null,
    var operation: String? = null,
    //region
    val nameUa: String? = null,
    val oldCode: String? = null,
    val name: String? = null,
    val newCode: String? = null,
    val slug: String? = null
) : Serializable

fun Car.toParams() : GetCarParams {
    return GetCarParams(
        carPlate = digits ?: ""
    )
}
