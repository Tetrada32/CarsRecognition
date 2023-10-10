package com.gahov.data.mapper.car

import com.gahov.data.local.entities.CarDTO
import com.gahov.data.mapper.common.DbMapper
import com.gahov.domain.entity.cars.CarEntity


/**
 * A class providing mapping functions for converting weather domain model data to and from
 * local database model data.
 */
class CarDomainToLocalMapper : DbMapper<CarEntity, CarDTO> {

    /**
     * Converts a car domain model to the corresponding local database model.
     *
     * @param domainModel The car domain model to be converted.
     * @return The resulting local database model [CarDTO].
     */
    override fun toDatabase(domainModel: CarEntity): CarDTO {
       return CarDTO(
           id = 0L,
           photoUrl = domainModel.photoUrl,
           isStolen = domainModel.isStolen,
           year = domainModel.year,
           brand = domainModel.vendor,
           digits = domainModel.digits,
           model = domainModel.model,
           city = domainModel.cityName,
           lastRegistrationDate = domainModel.lastRegistrationDate,
           searchTime = domainModel.searchDate
       )
    }

    override fun toDomain(dbModel: CarDTO): CarEntity {
        return CarEntity(
            photoUrl = dbModel.photoUrl,
            isStolen = dbModel.isStolen,
            year = dbModel.year,
            vendor = dbModel.brand,
            digits = dbModel.digits,
            model = dbModel.model,
            cityName = dbModel.city,
            lastRegistrationDate = dbModel.lastRegistrationDate,
            operations = emptyList(),
            region = null,
            searchDate = dbModel.searchTime
        )
    }
}