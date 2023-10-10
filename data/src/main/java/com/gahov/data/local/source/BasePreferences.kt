package com.gahov.data.local.source

import android.content.SharedPreferences
import java.io.IOException

/**
 * An abstract base class for managing preferences using SharedPreferences.
 *
 * @param sharedPreferences The [SharedPreferences] instance used for managing preferences.
 */
abstract class BasePreferences(private val sharedPreferences: SharedPreferences) {

    /**
     * Saves a value into SharedPreferences using the provided key.
     *
     * @param key The key associated with the value to be saved.
     * @param value The value to be saved.
     * @throws IOException If an unsupported type is encountered for the value.
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(IOException::class)
    protected fun <T> put(key: String, value: T) {
        val editor = sharedPreferences.edit()
        when (value) {
            is Float -> editor.putFloat(key, value)
            is Int -> editor.putInt(key, value)
            is String -> editor.putString(key, value)
            is Boolean -> editor.putBoolean(key, value)
            is Long -> editor.putLong(key, value)
            is Set<*> -> editor.putStringSet(key, value as Set<String>)
            else -> throw IOException()
        }
        editor.apply()
    }

    /**
     * Retrieves a value from SharedPreferences using the provided key with a default value.
     *
     * @param key The key associated with the value to be retrieved.
     * @param default The default value to be returned if the key is not found.
     * @return The retrieved value or the default value.
     * @throws IOException If an unsupported type is encountered for the default value.
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(IOException::class)
    protected fun <T> get(key: String, default: T): T {
        val preferences = sharedPreferences
        return when (default) {
            is Float -> preferences.getFloat(key, default) as T
            is Int -> preferences.getInt(key, default) as T
            is String -> preferences.getString(key, default) as T
            is Boolean -> preferences.getBoolean(key, default) as T
            is Long -> preferences.getLong(key, default) as T
            is Set<*> -> preferences.getStringSet(key, default as Set<String>) as T
            else -> throw IOException()
        }
    }
}