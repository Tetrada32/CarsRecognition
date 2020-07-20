package com.car_plate.data.mapper

import com.car_plate.data.entity.ApiCar
import com.car_plate.data.exceptions.InvalidResponseException
import com.car_plate.domain.entity.Car


class CarInfoMapper : ApiMapper<ApiCar, Car, Map<String, Any>> {

    override fun toDomain(apiModel: ApiCar): Car {
        val digits = apiModel.digits ?: throw InvalidResponseException()
        val photoUrl = apiModel.photoUrl ?: throw InvalidResponseException()
        val isStolen = apiModel.isStolen ?: throw InvalidResponseException()
        val year = apiModel.year.toString() ?: throw InvalidResponseException()
        val vendor = apiModel.vendor ?: throw InvalidResponseException()
        val model = apiModel.model ?: throw InvalidResponseException()
        val operations = "To do operations"
        val region = "UKRAINE"

        return Car(photoUrl, isStolen, year, vendor, digits, model, region, operations)
    }

    override fun toApi(domainModel: Car): Map<String, Any> {
       return emptyMap()
    }
}