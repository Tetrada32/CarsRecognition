package com.gahov.plates_recognition.common

import com.google.android.material.appbar.AppBarLayout
import kotlin.math.abs

/**
 * An abstract class that implements the AppBarLayout.OnOffsetChangedListener interface to provide
 * a listener
 * for tracking the offset changes of an AppBarLayout.
 */
abstract class AppBarOffsetChangeListener : AppBarLayout.OnOffsetChangedListener {

    private var mMaxScrollSize = 0

    private var mIsElementNotHidden = true

    /**
     * Called when the offset of the AppBarLayout changes.
     *
     * @param appBarLayout The AppBarLayout instance.
     * @param verticalOffset The vertical offset of the AppBarLayout.
     */
    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        if (mMaxScrollSize == 0) {
            if (appBarLayout != null) {
                mMaxScrollSize = appBarLayout.totalScrollRange
                val currentScrollPercentage: Int = (abs(verticalOffset) * 100 / mMaxScrollSize)
                checkPosition(currentScrollPercentage)
            }
        } else {
            val currentScrollPercentage: Int = (abs(verticalOffset) * 100 / mMaxScrollSize)
            if (currentScrollPercentage >= PERCENTAGE_TO_SHOW_ELEMENT) {
                showViews()
            } else {
                hideViews()
            }
        }
    }

    private fun showViews() {
        if (!mIsElementNotHidden) {
            mIsElementNotHidden = true
            onShow()
        }
    }

    private fun hideViews() {
        if (mIsElementNotHidden) {
            mIsElementNotHidden = false
            onHide()
        }
    }

    private fun checkPosition(currentScrollPosition: Int) {
        if (currentScrollPosition >= PERCENTAGE_TO_SHOW_ELEMENT) {
            mIsElementNotHidden = true
            onShow()
        }
    }

    abstract fun onHide()
    abstract fun onShow()

    companion object {
        private const val PERCENTAGE_TO_SHOW_ELEMENT = 40
    }
}
