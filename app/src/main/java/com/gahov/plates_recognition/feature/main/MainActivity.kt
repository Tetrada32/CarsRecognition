package com.gahov.plates_recognition.feature.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gahov.plates_recognition.R
import dagger.hilt.android.AndroidEntryPoint

/**
 * The main activity of the application.
 * Uses the SingleActivity pattern and Jetpack Navigation Component for navigation.
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
    }
}