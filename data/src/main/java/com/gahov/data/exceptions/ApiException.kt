package com.gahov.data.exceptions

open class ApiException : Exception {

    constructor(message: String? = "") : super(message)

    constructor(message: String? = "", cause: Throwable?) : super(message, cause)
}