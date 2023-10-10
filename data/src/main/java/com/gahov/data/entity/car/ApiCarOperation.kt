package com.gahov.data.entity.car

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiCarOperation(

    @SerialName("ua")
    val operation: String
)