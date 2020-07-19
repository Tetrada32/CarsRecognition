package com.car_plate.data.network

import retrofit2.Response

interface ResponseTransformer {

    fun <ApiBody : Any> transform(response: Response<ApiBody>): ApiBody
}

class ResponseTransformerImpl(
    private val errorExtractor: ResponseErrorExtractor
) : ResponseTransformer {

    override fun <ApiBody : Any> transform(response: Response<ApiBody>): ApiBody {
        val body = response.body()

        if (!response.isSuccessful || body == null) {
            throw errorExtractor.extractError(response)
        }

        return body
    }
}
