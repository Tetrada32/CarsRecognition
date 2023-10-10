package com.gahov.domain.entity.cars

import java.io.Serializable

data class CarEntity(
    var photoUrl: String?,
    var isStolen: Boolean,
    var year: String,
    var vendor: String? = null,
    var digits: String? = null,
    var model: String? = null,
    var region: Region? = null,
    var cityName: String? = null,
    val lastRegistrationDate: String? = null,
    var operations: List<Operations?>? = null
) : Serializable