package com.car_plate.data.network

import com.car_plate.data.entity.car.ApiCar
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @POST("nomer/{carPlate}")
    suspend fun getCarInfo(@Body @Path("carPlate") carPlate: String): Response<ApiCar>
}