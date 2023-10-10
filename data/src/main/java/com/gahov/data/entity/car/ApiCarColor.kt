package com.gahov.data.entity.car

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiCarColor(

    @SerialName("slug")
    val slug: String? = null,

    @SerialName("ua")
    val colorUa: String? = null
)