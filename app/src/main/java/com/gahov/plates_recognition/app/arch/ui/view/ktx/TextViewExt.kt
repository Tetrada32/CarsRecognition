package com.gahov.plates_recognition.app.arch.ui.view.ktx

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.gahov.plates_recognition.app.arch.ktx.getString
import com.gahov.plates_recognition.app.arch.ui.view.model.TextProvider

@BindingAdapter("setText")
fun TextView.setText(textProvider: TextProvider?) {
    textProvider?.let { text = textProvider.getString(context) }
}