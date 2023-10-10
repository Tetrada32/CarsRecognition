package com.gahov.plates_recognition

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * The main application class for the PlanRadarWeather application.
 * It is required for correct "Hilt" processing.
 */

@HiltAndroidApp
class App : Application()