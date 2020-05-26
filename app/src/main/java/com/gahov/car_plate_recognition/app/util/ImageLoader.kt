package com.gahov.car_plate_recognition.app.util

import android.widget.ImageView
import com.bumptech.glide.Glide
import java.io.File

class ImageLoader {
    fun loadImage(imageView: ImageView, imageUrl: String) {
        Glide.with(imageView).load(imageUrl).into(imageView)
    }

    fun loadImage(imageView: ImageView, imageUri: File?) {
        Glide.with(imageView).load(imageUri).centerCrop().into(imageView)
    }

    fun clearImage(imageView: ImageView) {
        Glide.with(imageView).clear(imageView)
    }
}