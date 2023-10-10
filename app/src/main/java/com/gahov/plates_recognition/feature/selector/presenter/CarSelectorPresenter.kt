package com.gahov.plates_recognition.feature.selector.presenter

interface CarSelectorPresenter {

    fun onAddCarClick()

    fun onCarItemClick(carDigits: String)

    fun onCarHistoryButtonClick(carDigits: String)
}