package com.car_plate.data.mapper

import com.car_plate.data.entity.car.ApiCar
import com.car_plate.domain.entity.Car

//TODO complete
//
//class CarInfoMapper : ApiMapper<ApiCar, Car, Map<String, Any>> {
//
//    override fun toDomain(apiModel: ApiToken): UserToken {
////        val token = apiModel.accessToken ?: throw InvalidResponseException()
////        val refreshToken = apiModel.refreshToken ?: throw InvalidResponseException()
////        val tokenType = apiModel.tokenType ?: throw InvalidResponseException()
////        val tokenExpires = apiModel.tokenExpires ?: throw InvalidResponseException()
//        return UserToken.create(token, tokenType, refreshToken, tokenExpires)
//    }
//
//    override fun toDomain(apiModel: ApiCar): Car {
//        val digits  = apiModel.digits
//        return Car.create()
//    }
//
//    override fun toApi(domainModel: Car): Map<String, Any> {
//        TODO("Not yet implemented")
//    }
//}