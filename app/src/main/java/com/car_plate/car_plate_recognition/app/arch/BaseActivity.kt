package com.car_plate.car_plate_recognition.app.arch

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity() {

    lateinit var binding: T
        private set

    @LayoutRes
    protected abstract fun getLayoutId(): Int

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, getLayoutId())

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        getBindingVariable()?.let {
            binding.setVariable(it, initViewModel())
        }

        binding.executePendingBindings()
        binding.lifecycleOwner = this
    }

    @LayoutRes
    protected abstract fun getBindingVariable(): Int?

    protected abstract fun initViewModel() : Any?

    protected abstract fun setSubscribers()
}
