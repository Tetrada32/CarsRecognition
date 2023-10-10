package com.gahov.data.util

import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

/**
 * An object providing utility functions for working with date and time.
 */
object DateUtil {
    private const val DEFAULT_DATE_FORMAT = "dd.MM.yyyy - HH:mm"

    /**
     * Formats the current time with an optional timezone offset.
     *
     * @param timezoneOffsetSeconds The timezone offset in seconds, or null for the system default.
     * @return A formatted string representing the current time.
     */
    fun formatCurrentTimeWithOffset(timezoneOffsetSeconds: Int?): String {
        return try {
            val time =
                LocalDateTime.now(timezoneOffsetSeconds?.let { ZoneOffset.ofTotalSeconds(it) })
            time.formatByPattern(DEFAULT_DATE_FORMAT)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            val currentTime = LocalDateTime.now()
            currentTime.formatByPattern(DEFAULT_DATE_FORMAT)
        }
    }

    fun formatCurrentTime(): String {
        return try {
            val time = LocalDateTime.now()
            time.formatByPattern(DEFAULT_DATE_FORMAT)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            ""
        }
    }

    /**
     * Formats a LocalDateTime instance using the specified pattern.
     *
     * @param pattern The pattern used for formatting.
     * @return A formatted string representing the LocalDateTime instance.
     */
    fun LocalDateTime.formatByPattern(pattern: String): String {
        val formatter = DateTimeFormatter.ofPattern(pattern)
        return format(formatter)
    }
}
