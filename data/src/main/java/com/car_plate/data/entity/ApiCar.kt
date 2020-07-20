package com.car_plate.data.entity

import com.car_plate.data.entity.car.ApiCarOperations
import com.car_plate.data.entity.car.ApiCarRegion
import com.google.gson.annotations.SerializedName

class ApiCar(

    @field:SerializedName("photoUrl")
    val photoUrl: String? = null,

    @field:SerializedName("stolen")
    val isStolen: Boolean? = null,

    @field:SerializedName("operations")
    val operations: List<ApiCarOperations?>? = null,

    @field:SerializedName("year")
    val year: Int? = null,

    @field:SerializedName("vendor")
    val vendor: String? = null,

    @field:SerializedName("digits")
    val digits: String? = null,

    @field:SerializedName("model")
    val model: String? = null,

    @field:SerializedName("region")
    val region: ApiCarRegion? = null
)