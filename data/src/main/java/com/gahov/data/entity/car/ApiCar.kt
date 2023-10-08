package com.gahov.data.entity.car

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class ApiCar(

    @SerialName("photo_url")
    val photoUrl: String? = null,

    @SerialName("is_stolen")
    val isStolen: Boolean? = null,

    @SerialName("stolen_details")
    val stolenDetails: String? = null,

    @SerialName("operations")
    val operations: List<ApiCarTotalOperations?>? = null,

    @SerialName("model_year")
    val year: Int? = null,

    @SerialName("vendor")
    val vendor: String? = null,

    @SerialName("digits")
    val digits: String? = null,

    @SerialName("vin")
    val vinCode: String? = null,

    @SerialName("model")
    val model: String? = null,

    @SerialName("region")
    val region: ApiCarRegion? = null
)