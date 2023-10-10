package com.gahov.plates_recognition.arch.router

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.navigation.*
import com.gahov.plates_recognition.R
import com.gahov.domain.entity.component.logger.Logger
import com.gahov.plates_recognition.arch.router.command.Command
import com.gahov.plates_recognition.arch.router.command.NavDirection

open class NavComponentRouter(
    private val navController: NavController,
    private val logger: Logger
) : Router {

    override fun navigate(command: Command.Route) {
        if (command is NavDirection) {
            navigate(command)
        } else {
            throw Throwable(message = Router.UNKNOWN_COMMAND)
        }
    }

    override fun popBackStack() = navController.popBackStack()

    override fun popToRoot() {
        while (true) {
            if (!navController.popBackStack()) {
                break
            }
        }
        navigateResId(navController.graph.startDestinationId)
    }

    private fun navigate(command: NavDirection) {
        when (command) {
            is NavDirection.Direction -> navigateDirection(command.directions, command.options)
            is NavDirection.Replace -> navigateWithReplace(command.directions)
            is NavDirection.ResID -> navigateResId(command.resID, command.args)
            is NavDirection.BackTo -> popBackStack(command.destinationId, command.inclusive)
        }
    }

    open fun navigateDirection(directions: NavDirections, options: NavOptions? = null) {
        try {
            navController.navigate(directions, options)
        } catch (e: Exception) {
            catchException(e)
        }
    }

    open fun navigateWithReplace(directions: NavDirections) {
        navigateDirection(directions, replaceOption())
    }

    open fun navigateResId(@IdRes resId: Int, args: Bundle? = null) {
        try {
            navController.navigate(resId, args, navOptions {
                getAnimBuilder()
            })
        } catch (e: Exception) {
            catchException(e)
        }
    }

    open fun popBackStack(@IdRes destinationId: Int, inclusive: Boolean) =
        navController.popBackStack(destinationId, inclusive)

    private fun replaceOption(): NavOptions? {
        val currentID = navController.currentDestination?.id ?: return null
        return navOptions {
            popUpTo(id = currentID, popUpToBuilder = { inclusive = true })
        }
    }

    private fun catchException(e: Exception) {
        logger.log(
            message = "${logger.getConfiguration().className}: ${Router.UNKNOWN_ROUTE}",
            configuration = logger.getConfiguration().copy(this),
            throwable = e
        )
    }

    private fun getAnimBuilder(): AnimBuilder {
        val builder = AnimBuilder()
        with(builder) {
            popEnter = R.anim.nav_default_pop_enter_anim
            popExit = R.anim.nav_default_pop_exit_anim
            enter = R.anim.nav_default_enter_anim
            exit = R.anim.nav_default_exit_anim
        }
        return builder
    }
}