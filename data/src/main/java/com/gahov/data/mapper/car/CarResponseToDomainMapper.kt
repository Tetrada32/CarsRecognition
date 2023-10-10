package com.gahov.data.mapper.car

import com.gahov.data.entity.car.ApiCar
import com.gahov.data.exceptions.InvalidResponseException
import com.gahov.data.mapper.common.ApiMapper
import com.gahov.data.util.DateUtil.formatCurrentTime
import com.gahov.domain.entity.cars.CarEntity
import com.gahov.domain.entity.cars.Operations
import com.gahov.domain.entity.cars.Region


/**
 * A class providing mapping functions for converting to domain data from Car response.
 */
class CarResponseToDomainMapper : ApiMapper<ApiCar, CarEntity> {

    /**
     * Converts a CarAPI response model to the corresponding car domain model.
     *
     * @param apiModel The CarAPI response model to be converted.
     * @return The resulting car domain model [CarEntity].
     */
    override fun toDomain(apiModel: ApiCar): CarEntity {
        val mOperations = emptyList<Operations>().toMutableList()

        val digits = apiModel.digits ?: ""
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
                localOperation.operation = i?.operations?.get(0)?.operation
                localOperation.registrationDate = i?.registrationDate
                localOperation.vendor = i?.vendor
                localOperation.notes = i?.notes
                mOperations.add(localOperation)
            }
        }

        return CarEntity(
            photoUrl = photoUrl,
            isStolen = isStolen,
            year = year,
            vendor = vendor,
            digits = digits,
            model = model,
            region = region,
            lastRegistrationDate = mOperations.last().registrationDate,
            operations = mOperations,
            searchDate = formatCurrentTime()
        )
    }
}