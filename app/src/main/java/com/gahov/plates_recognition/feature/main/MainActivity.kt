package com.gahov.plates_recognition.feature.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gahov.plates_recognition.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
    }
}