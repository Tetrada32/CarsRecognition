package com.car_plate.data.network

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

private const val ERRORS = "errors"
private const val MESSAGE = "message"

class ApiErrorDeserializer : JsonDeserializer<ApiErrorMessage> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): ApiErrorMessage {
        val rootObj = json?.asJsonObject ?: throw IllegalArgumentException()

        if (rootObj.has(ERRORS) && rootObj.get(ERRORS).isJsonObject) {
            val errorObj = rootObj.get(ERRORS).asJsonObject

            val entrySet: Set<Map.Entry<String, JsonElement>> = errorObj.entrySet()
            for ((key, value) in entrySet) {
                if (value.isJsonArray && value.asJsonArray.size() > 0) {
                    val firstErrorMessage = value.asJsonArray.first()
                    return ApiErrorMessage(message = firstErrorMessage.asString)
                }
            }
        } else if (rootObj.has(MESSAGE)) {
            val errorObj = rootObj.asJsonObject
            return ApiErrorMessage(message = errorObj.get(MESSAGE).asString)
        }
        return ApiErrorMessage(message = "")
    }
}