package com.gahov.data.mapper.error

import com.gahov.domain.common.converter.Mapper
import com.gahov.domain.entity.failure.ErrorEntity
import com.gahov.data.entity.error.ErrorResponse

/**
 * An object providing mapping functions for converting error-related data.
 */

object ErrorEventMapper {

    /**
     * An inner class for mapping from [ErrorResponse] to [ErrorEntity].
     */
    class ErrorMessageResponseToEntity : Mapper<ErrorResponse, ErrorEntity>() {

        /**
         * Converts an [ErrorResponse] to an [ErrorEntity].
         *
         * @param from The [ErrorResponse] object to be converted.
         * @return The resulting [ErrorEntity] object.
         */
        override fun map(from: ErrorResponse): ErrorEntity {
            return ErrorEntity(
                message = from.errorMessage.orEmpty(),
                code = from.responseCode ?: 401
            )
        }
    }
}