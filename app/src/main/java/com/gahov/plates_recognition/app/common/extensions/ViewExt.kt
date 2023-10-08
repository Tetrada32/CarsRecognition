package com.gahov.plates_recognition.app.common.extensions

import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation

fun View.showAnimated(animationDuration: Long = DEFAULT_ANIMATION_DURATION) {
    if (visibility == View.GONE || visibility == View.INVISIBLE) {
        this.startAnimation(AlphaAnimation(0F, 1F).apply {
            duration = animationDuration
            fillAfter = true
            setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {}
                override fun onAnimationEnd(animation: Animation?) {
                    visibility = View.VISIBLE
                }

                override fun onAnimationRepeat(animation: Animation?) {}
            })
        })
    }
}

fun View.hideAnimated(animationDuration: Long = DEFAULT_ANIMATION_DURATION) {
    if (visibility == View.VISIBLE) {
        this.startAnimation(AlphaAnimation(1F, 0F).apply {
            duration = animationDuration
            fillAfter = true
            setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {}
                override fun onAnimationEnd(animation: Animation?) {
                    visibility = View.GONE
                }

                override fun onAnimationRepeat(animation: Animation?) {}
            })
        })
    }
}

const val DEFAULT_ANIMATION_DURATION = 500L