package com.gahov.plates_recognition.arch.component.coil.impl

import android.content.Context
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.imageLoader
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.gahov.plates_recognition.arch.component.coil.CoilImagePreloader

@OptIn(ExperimentalCoilApi::class)
class CoilImagePreloaderImpl(private val context: Context) : CoilImagePreloader {

    override fun preloadSimpleResource(url: String, diskCacheKey: String) {
        val imageLoader: ImageLoader = context.imageLoader
        val request = ImageRequest.Builder(context)
            .data(url)
            .addHeader("Cache-Control", "max-age=86400")
            .diskCacheKey(diskCacheKey)
            .memoryCachePolicy(CachePolicy.DISABLED)
            .diskCachePolicy(CachePolicy.WRITE_ONLY)
            .build()
        imageLoader.enqueue(request)
    }

    override fun clearRecord(diskCacheKey: String): Boolean {
        return try {
            val loader = context.imageLoader
            loader.diskCache?.remove(diskCacheKey) ?: false
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}