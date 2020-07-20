package com.car_plate.data.network

import com.car_plate.data.entity.ApiCar
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("nomer/{carPlate}")
    suspend fun getCarInfo(@Path("carPlate") carPlate: String): Response<ApiCar>
}