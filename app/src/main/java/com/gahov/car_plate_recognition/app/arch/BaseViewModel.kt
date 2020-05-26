package com.gahov.car_plate_recognition.app.arch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    private val mIsLoading = MutableLiveData<Boolean>()

    val isLoading: LiveData<Boolean>
        get() = mIsLoading

    fun setLoading(isLoading: Boolean) {
        mIsLoading.value = isLoading
    }

    fun postLoading(isLoading: Boolean) {
        mIsLoading.postValue(isLoading)
    }
}
