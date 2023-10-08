package com.gahov.plates_recognition.app.arch.ui.view.model

import androidx.annotation.StringRes

sealed class TextProvider {
    data class Text(val text: String = "") : TextProvider()
    data class ResText(@StringRes val text: Int = 0) : TextProvider()
    data class FormatResText(@StringRes val res: Int = 0, val text: String = "") : TextProvider()
}