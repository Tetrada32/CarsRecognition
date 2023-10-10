package com.gahov.domain.entity.failure

sealed class ServerError : Failure.FeatureFailure() {

    object ServerCommon : ServerError()

    data class ServerCodeError(val code: Int, val error: ErrorEntity) : ServerError()
}