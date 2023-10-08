package com.gahov.plates_recognition.app.arch.router.command

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions

sealed class NavDirection : Command.Route() {
    data class Direction(val directions: NavDirections, val options: NavOptions? = null) :
        NavDirection()

    data class Replace(val directions: NavDirections) : NavDirection()
    data class ResID(@IdRes val resID: Int, val args: Bundle? = null) : NavDirection()

    data class BackTo(val destinationId: Int, val inclusive: Boolean = false) : NavDirection()
}