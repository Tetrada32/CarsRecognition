package com.gahov.domain.entity.failure

/**
 * A sealed class representing different types of failures that can occur in the application.
 */
sealed class Failure {

    /**
     * Represents a common failure that may occur.
     *
     * @property throwable The throwable associated with the common failure.
     */
    data class Common(val throwable: Throwable? = null) : Failure()

    /**
     * Represents a failure related to a data source.
     *
     * @property throwable The throwable associated with the data source failure.
     */
    data class DataSourceException(val throwable: Throwable) : Failure()

    /**
     * Represents a failure related to a coroutine operation.
     *
     * @property throwable The throwable associated with the coroutine failure.
     */
    data class CoroutineException(val throwable: Throwable) : Failure()

    /** * Extend this class for feature specific failures.*/
    abstract class FeatureFailure : Failure()

}