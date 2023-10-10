package com.gahov.plates_recognition.arch.component.coil

interface CoilImagePreloader {

    fun preloadSimpleResource(url: String, diskCacheKey: String)

    fun clearRecord(diskCacheKey: String): Boolean
}