package com.gahov.data.entity.car

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiCarRegion(

    @SerialName("name_ua")
    val nameUa: String? = null,

    @SerialName("old_code")
    val oldCode: String? = null,

    @SerialName("name")
    val name: String? = null,

    @SerialName("new_code")
    val newCode: String? = null,

    @SerialName("slug")
    val slug: String? = null
)