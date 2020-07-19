package com.car_plate.data.mapper

interface ApiMapper<in InputApiModel, DomainModel, out OutputApiModel> {

    fun toDomain(apiModel: InputApiModel): DomainModel

    fun toApi(domainModel: DomainModel): OutputApiModel
}

abstract class Mapper<in InputApiModel, DomainModel, out OutputApiModel> :
    ApiMapper<InputApiModel, DomainModel, OutputApiModel> {

    fun toDomain(apiModels: List<InputApiModel>): List<DomainModel> = apiModels.map { toDomain(it) }

}