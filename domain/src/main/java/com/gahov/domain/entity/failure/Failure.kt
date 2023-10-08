package com.gahov.domain.entity.failure

sealed class Failure {

    data class Common(val throwable: Throwable? = null) : Failure()

    data class DataSourceException(val throwable: Throwable) : Failure()

    data class CoroutineException(val throwable: Throwable) : Failure()

    /** * Extend this class for feature specific failures.*/
    abstract class FeatureFailure : Failure()

}