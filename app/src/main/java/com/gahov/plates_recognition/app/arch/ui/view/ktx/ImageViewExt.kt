package com.gahov.plates_recognition.app.arch.ui.view.ktx

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.gahov.plates_recognition.app.common.extensions.loadImage
import com.gahov.plates_recognition.app.arch.ui.view.model.IconProvider

@BindingAdapter("setImage")
fun ImageView.setImage(iconProvider: IconProvider?) {
    when (iconProvider) {
        is IconProvider.Drawable -> loadImage(iconProvider.icon)
        is IconProvider.Url -> loadImage(iconProvider.url)
        is IconProvider.ResIcon -> loadImage(iconProvider.icon)
        is IconProvider.ResVectorIcon -> loadImage(iconProvider.icon)
        else -> setImageDrawable(null)
    }
}