package com.gahov.plates_recognition.app.arch.ui.view

import com.gahov.domain.entity.failure.Failure
import com.gahov.plates_recognition.app.arch.ui.view.model.TextProvider

interface BaseView {
    fun displayError(failure: Failure)

    fun showMessage(textProvider: TextProvider)
}