package com.car_plate.data.repository.event

import com.car_plate.data.entity.car.ApiCar
import com.car_plate.data.network.ApiService
import com.car_plate.data.network.ResponseTransformer


class CarPlateRetrofitAdapter(
    private val apiService: ApiService,
    private val responseTransformer: ResponseTransformer
) : CarPlateRemoteSource {

    override suspend fun getCarInfo(carPlate: String): ApiCar =
        responseTransformer.transform(apiService.getCarInfo(carPlate))
}