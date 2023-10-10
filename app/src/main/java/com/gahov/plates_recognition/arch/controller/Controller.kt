package com.gahov.plates_recognition.arch.controller

import com.gahov.domain.entity.failure.Failure
import com.gahov.plates_recognition.arch.router.command.Command
import com.gahov.plates_recognition.arch.ui.view.model.TextProvider

interface Controller {
    fun showMessage(message: TextProvider)

    fun setLoading(boolean: Boolean)

    fun navigate(command: Command)

    fun handleFailure(failure: Failure)

}