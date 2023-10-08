package com.gahov.domain.entity.cars

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