package com.gahov.plates_recognition.app.arch.component.coil

interface CoilImagePreloader {

    fun preloadSimpleResource(url: String, diskCacheKey: String)

    fun clearRecord(diskCacheKey: String): Boolean
}