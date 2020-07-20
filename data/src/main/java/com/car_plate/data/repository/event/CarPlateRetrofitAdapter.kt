package com.car_plate.data.repository.event

import com.car_plate.data.entity.ApiCar
import com.car_plate.data.network.ApiService
import com.car_plate.data.network.ResponseTransformer
import com.car_plate.data.repository.car.CarRemoteSource


class CarPlateRetrofitAdapter(
    private val apiService: ApiService,
    private val responseTransformer: ResponseTransformer
) : CarPlateRemoteSource, CarRemoteSource {

    override suspend fun getCarInfo(carPlate: String): ApiCar =
        responseTransformer.transform(apiService.getCarInfo(carPlate))
}