package com.gahov.data.entity.car

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiCarFuel(

    @SerialName("id")
    val id: Int? = null,

    @SerialName("ua")
    val patrolType: String? = null,

    @SerialName("slug")
    val slug: String? = null,
)