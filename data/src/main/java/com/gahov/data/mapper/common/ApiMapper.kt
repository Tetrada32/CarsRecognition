package com.gahov.data.mapper.common

/**
 * An interface for mapping API model data to domain model data.
 *
 * @param InputApiModel The type of the input API model.
 * @param DomainModel The type of the domain model.
 */
interface ApiMapper<in InputApiModel, DomainModel> {

    /**
     * Converts an input API model to a domain model.
     *
     * @param apiModel The input API model to be converted.
     * @return The resulting domain model.
     *
     */
    fun toDomain(apiModel: InputApiModel): DomainModel
}