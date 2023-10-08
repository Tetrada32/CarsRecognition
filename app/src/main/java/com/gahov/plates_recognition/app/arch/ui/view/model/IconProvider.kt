package com.gahov.plates_recognition.app.arch.ui.view.model

import androidx.annotation.DrawableRes
import android.graphics.drawable.Drawable as AndroidDrawable

sealed class IconProvider {
    data class ResIcon(@DrawableRes val icon: Int = 0) : IconProvider()
    data class ResVectorIcon(@DrawableRes val icon: Int = 0) : IconProvider()
    data class Url(val url: String = "") : IconProvider()
    data class Drawable(val icon: AndroidDrawable) : IconProvider()
}