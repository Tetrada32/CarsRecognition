package com.gahov.data.entity.car

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiCarType(

    @SerialName("id")
    val id: Int? = null,

    @SerialName("ua")
    val carTypeUa: String? = null,

    @SerialName("slug")
    val slug: String? = null,
)