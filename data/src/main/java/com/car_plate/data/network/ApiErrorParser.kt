package com.car_plate.data.network

import com.google.gson.Gson
import com.car_plate.data.exceptions.ApiException
import com.car_plate.data.exceptions.UnauthorizedException
import retrofit2.Response

class ApiErrorParser(private val gson: Gson) : ResponseErrorExtractor {

    override fun extractError(response: Response<*>): ApiException {
        return try {
            val errorBody = response.errorBody()
            val rawResponse = errorBody?.string()
            val serverMessage = rawResponse?.let { gson.fromJson(it, ApiErrorMessage::class.java) }
            if (response.code() == 401 || response.code() == 403){
                UnauthorizedException(message = serverMessage?.message)
            } else {
                ApiException(serverMessage?.message)
            }
            //ApiException(serverMessage?.message)
        } catch (error: Exception) {
            ApiException(cause = error)
        }
    }
}