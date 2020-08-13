package com.car_plate.data.mapper

import com.car_plate.data.entity.ApiCar
import com.car_plate.data.exceptions.InvalidResponseException
import com.car_plate.domain.entity.Car
import com.car_plate.domain.entity.Operations
import com.car_plate.domain.entity.Region


class CarInfoMapper : ApiMapper<ApiCar, Car, Map<String, Any>> {

    override fun toDomain(apiModel: ApiCar): Car {
        val mOperations = emptyList<Operations>().toMutableList()

        val digits = apiModel.digits ?: throw InvalidResponseException()
        val photoUrl = apiModel.photoUrl ?: ""
        val isStolen = apiModel.isStolen ?: throw InvalidResponseException()
        val year = apiModel.year.toString()
        val vendor = apiModel.vendor ?: ""
        val model = apiModel.model ?: throw InvalidResponseException()
        val region = Region(
            nameUa = apiModel.region?.nameUa,
            oldCode = apiModel.region?.oldCode,
            name = apiModel.region?.name,
            newCode = apiModel.region?.newCode,
            slug = apiModel.region?.slug
        )
        if (apiModel.operations != null) {
            for (i in apiModel.operations) {
                val localOperation = Operations()
                localOperation.address = i?.address
                localOperation.isLast = i?.isLast
                localOperation.model = i?.model
                localOperation.modelYear = i?.modelYear
                localOperation.operation = i?.operation
                localOperation.registrationDate = i?.regAt
                localOperation.vendor = i?.vendor
                localOperation.notes = i?.notes
                mOperations.add(localOperation)
            }
        }

        return Car(photoUrl, isStolen, year, vendor, digits, model, region, mOperations)
    }

    override fun toApi(domainModel: Car): Map<String, Any> {
        return emptyMap()
    }
}