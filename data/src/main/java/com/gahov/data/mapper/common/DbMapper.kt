package com.gahov.data.mapper.common

/**
 * An interface for mapping between domain model data and database model data.
 *
 * @param InputDomainModel The type of the input domain model.
 * @param DbModel The type of the database model.
 */
interface DbMapper<InputDomainModel, DbModel> {

    /**
     * Converts an input domain model to a corresponding database model.
     *
     * @param domainModel The input domain model to be converted.
     * @return The resulting database model.
     */
    fun toDatabase(domainModel: InputDomainModel): DbModel

    /**
     * Converts a database model to the corresponding domain model.
     *
     * @param dbModel The database model to be converted.
     * @return The resulting domain model.
     */
    fun toDomain(dbModel: DbModel): InputDomainModel
}