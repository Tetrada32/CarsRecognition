package com.gahov.data.remote

import com.gahov.data.mapper.error.ErrorEventMapper
import com.gahov.domain.entity.common.Either
import com.gahov.domain.entity.common.OperationStatus
import com.gahov.domain.entity.failure.ErrorEntity
import com.gahov.domain.entity.failure.Failure
import com.gahov.domain.entity.failure.ServerError
import com.gahov.domain.entity.network.NoNetworkFailure
import com.gahov.data.entity.error.ErrorResponse
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.ResponseBody
import retrofit2.Response
import java.net.UnknownHostException

suspend inline fun <T : Any> call(
    crossinline call: suspend () -> Response<T>,
): Either<Failure, T> {
    return try {
        val response = call.invoke()
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                Either.Right(body)
            } else {
                Either.Left(
                    ServerError.ServerCodeError(
                        response.code(),
                        parseServerError(response.errorBody())
                    )
                )
            }
        } else {
            Either.Left(
                ServerError.ServerCodeError(
                    response.code(),
                    parseServerError(response.errorBody())
                )
            )
        }
    } catch (e: Throwable) {
        e.printStackTrace()
        println(e.message)
        handleException(e)
    }
}

suspend inline fun <T : Any> callWithEmptyBody(
    crossinline call: suspend () -> Response<T>,
): Either<Failure, OperationStatus> {
    return try {
        val response = call.invoke()
        if (response.isSuccessful) {
            Either.Right(OperationStatus.SUCCESS)
        } else {
            Either.Left(
                ServerError.ServerCodeError(
                    response.code(),
                    parseServerError(response.errorBody())
                )
            )
        }
    } catch (e: Throwable) {
        e.printStackTrace()
        println(e.message)
        handleException(e)
    }
}

@PublishedApi
internal fun handleException(e: Throwable): Either.Left<Failure> {
    return if (e is UnknownHostException) {
        Either.Left(NoNetworkFailure)
    } else {
        Either.Left(ServerError.ServerCommon)
    }
}

@PublishedApi
internal fun parseServerError(errorBody: ResponseBody?): ErrorEntity {
    return ErrorEventMapper.ErrorMessageResponseToEntity()
        .map(parseErrorBody(errorBody) ?: ErrorResponse())
}

@PublishedApi
internal fun parseErrorBody(errorBody: ResponseBody?): ErrorResponse? {
    return try {
        val json = Json { ignoreUnknownKeys = true }
        json.decodeFromString<ErrorResponse>(string = errorBody?.string().orEmpty())
    } catch (e: Throwable) {
        e.printStackTrace()
        println(e.message)
        null
    }
}
