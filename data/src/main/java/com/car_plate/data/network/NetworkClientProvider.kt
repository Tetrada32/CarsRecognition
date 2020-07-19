package com.car_plate.data.network

import com.google.gson.*
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import com.car_plate.data.ApiConstants
import com.car_plate.data.auth.AuthInterceptor
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

private const val OKHTTP_READ_TIMEOUT = 60L
private const val OKHTTP_CONNECT_TIMEOUT = 60L

object NetworkClientProvider {

    fun <T> provideApiService(retrofit: Retrofit, service: Class<T>): T = retrofit.create(service)

    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ApiConstants.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(okHttpClient)
            .build()
    }

    fun provideGson(): Gson =
        GsonBuilder()
            .registerTypeAdapter(Date::class.java, getGsonDateDeserializer())
            .registerTypeAdapterFactory(NullStringToEmptyAdapterFactory<String>())
            .registerTypeAdapter(object : TypeToken<ApiErrorMessage>(){}.type, ApiErrorDeserializer())
            .setLenient()
            .registerTypeAdapter(
                object : TypeToken<ApiErrorMessage>() {}.type,
                ApiErrorDeserializer()
            )
            .create()

    fun provideOkHttp(authInterceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = getLoggingLevel()
            })
            .readTimeout(OKHTTP_READ_TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(OKHTTP_CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }

    //It probably will be need in future (requests with tokens)
    fun provideAuthInterceptor(): Interceptor =
        AuthInterceptor()

    private fun getLoggingLevel() = HttpLoggingInterceptor.Level.BODY

    private class NullStringToEmptyAdapterFactory<T> : TypeAdapterFactory {
        override fun <T> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T>? {

            val rawType = type.getRawType() as Class<T>
            return if (rawType != String::class.java) {
                null
            } else StringAdapter() as TypeAdapter<T>
        }
    }

    private fun getGsonDateDeserializer(): JsonDeserializer<Date> {
        return JsonDeserializer<Date> { json, _, _ -> Date(json!!.asJsonPrimitive.asLong); }
    }

    private class StringAdapter : TypeAdapter<String>() {

        override fun read(reader: JsonReader): String {
            if (reader.peek() === JsonToken.NULL) {
                reader.nextNull()
                return ""
            }
            return reader.nextString()
        }

        override fun write(writer: JsonWriter, value: String?) {
            if (value == null) {
                writer.nullValue()
                return
            }
            writer.value(value)
        }
    }
}