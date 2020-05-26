package com.gahov.car_plate_recognition.app.util

import com.gahov.car_plate_recognition.BuildConfig

object Constants {
    private const val PROVIDER = ".provider"

    const val AUTHORITY = BuildConfig.APPLICATION_ID + PROVIDER

    const val REQUEST_IMAGE = 100

    const val STORAGE = 1

    const val JPEG_SUFFIX = ".jpg"

    const val JPEG_PREFIX = "JPEG_"

    const val FLOAT = "%.2f"
}