package com.gahov.data.remote.protocol

import com.gahov.data.entity.car.ApiCar
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MainProtocol {

    @GET("nomer/{carPlate}")
    suspend fun getCarInfo(@Path("carPlate") carPlate: String): Response<ApiCar>
}