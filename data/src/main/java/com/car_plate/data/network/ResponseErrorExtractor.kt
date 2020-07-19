package com.car_plate.data.network

import com.car_plate.data.exceptions.ApiException
import retrofit2.Response

interface ResponseErrorExtractor {

    fun extractError(response: Response<*>) : ApiException
}