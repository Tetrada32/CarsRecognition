package com.gahov.domain.common.converter

@SuppressWarnings("unused")
abstract class Converter<FirstSourceType, SecondSourceType>(
    private val firstSource: Mapper<FirstSourceType, SecondSourceType>,
    private val secondSource: Mapper<SecondSourceType, FirstSourceType>,
) {

    @JvmName("mapFromFirstSource")
    fun mapFrom(from: FirstSourceType) = firstSource.map(from)

    @JvmName("mapFromFirstSource")
    fun mapFrom(from: List<FirstSourceType>) = from.map { mapFrom(it) }

    @JvmName("mapFromSecondSource")
    fun mapFrom(from: SecondSourceType) = secondSource.map(from)

    @JvmName("mapFromSecondSource")
    fun mapFrom(from: List<SecondSourceType>) = from.map { mapFrom(it) }

}