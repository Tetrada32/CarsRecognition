package com.gahov.data.local.storage.authorization

import android.content.SharedPreferences
import com.gahov.data.local.source.BasePreferences

/** An implementation of the [AuthorizationLocalStorage] interface for managing access token storage
 *  using SharedPreferences.
 *
 * @param preferences The [SharedPreferences] instance used for managing preferences.
 */

class ImplAuthorizationLocalStorage(
    preferences: SharedPreferences,
) : AuthorizationLocalStorage, BasePreferences(preferences) {

    override var accessToken: String
        get() = get(KEY_ACCESS_TOKEN, "")
        set(value) = put(KEY_ACCESS_TOKEN, value)

    companion object {
        private const val KEY_ACCESS_TOKEN = "key_access_token"
    }
}