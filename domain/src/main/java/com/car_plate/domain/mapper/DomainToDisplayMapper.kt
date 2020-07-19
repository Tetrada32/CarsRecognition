package com.car_plate.domain.mapper

interface DomainToDisplayMapper<in DomainModel, out DisplayModel> {

    fun mapToDisplay(useCaseResult: DomainModel): DisplayModel
}

abstract class Mapper<in DomainModel, out DisplayModel> :
    DomainToDisplayMapper<DomainModel, DisplayModel> {

    open fun mapToDisplay(useCaseResult: List<DomainModel>) = useCaseResult.map { mapToDisplay(it) }

}