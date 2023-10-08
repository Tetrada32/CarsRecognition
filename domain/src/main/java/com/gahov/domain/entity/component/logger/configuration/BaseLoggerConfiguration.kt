package com.gahov.domain.entity.component.logger.configuration

abstract class BaseLoggerConfiguration : LoggerConfiguration {

    protected open fun getClassName(any: Any): String {
        if (any is String) {
            return any
        }
        var className = any.javaClass.name
        var firstPosition = className.lastIndexOf(".") + 1
        if (firstPosition < 0) {
            firstPosition = 0
        }
        className = className.substring(firstPosition)
        firstPosition = className.lastIndexOf("$")
        if (firstPosition > 0) {
            className = className.substring(0, firstPosition)
        }
        return className
    }
}