package com.gahov.data.entity.car

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiCarTotalOperations(

    @SerialName("registered_at")
    val registrationDate: String? = null,

    @SerialName("notes")
    val notes: String? = null,

    @SerialName("address")
    val address: String? = null,

    @SerialName("isLast")
    val isLast: Boolean? = null,

    @SerialName("vendor")
    val vendor: String? = null,

    @SerialName("model")
    val model: String? = null,

    @SerialName("model_year")
    val modelYear: Int? = null,

    @SerialName("operations")
    val operations: List<ApiCarOperation>? = null,

    @SerialName("department")
    val department: String? = null,

    @SerialName("is_registered_to_company")
    val isRegisteredToCompany: Boolean? = null,

    @SerialName("displacement")
    val displacement: Int? = null,

    @SerialName("own_weight")
    val ownWeight: Int? = null,

    @SerialName("total_weight")
    val totalWeight: Int? = null,

    @SerialName("kind")
    val type: ApiCarType? = null,

    @SerialName("fuel")
    val fuelType: ApiCarFuel? = null,
)