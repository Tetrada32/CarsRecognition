package com.gahov.domain.entity.failure

data class ErrorEntity(
    val status: String = "error",
    val code: Int = 401,
    val message: String = ""
)