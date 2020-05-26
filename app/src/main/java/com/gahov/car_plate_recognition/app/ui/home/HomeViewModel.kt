package com.gahov.car_plate_recognition.app.ui.home

import android.content.Context
import android.graphics.BitmapFactory
import androidx.lifecycle.MutableLiveData
import com.gahov.car_plate_recognition.app.arch.BaseCoroutinesViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.openalpr.OpenALPR
import java.io.File

class HomeViewModel : BaseCoroutinesViewModel() {

    private val options = BitmapFactory.Options()

    val recognitionResult = MutableLiveData<String>()

    fun recognizePlateNumber(
        context: Context,
        ANDROID_DATA_DIR: String?,
        destination: File?
    ) {
        scope.launch {
            withContext(Dispatchers.IO) {
                val openAlprConfFile = ANDROID_DATA_DIR + File.separatorChar + "runtime_data" +
                        File.separatorChar + "openalpr.conf"
                options.inSampleSize = 10

                val result = OpenALPR.Factory.create(context, ANDROID_DATA_DIR)
                    .recognizeWithCountryRegionNConfig(
                        "eu",
                        "",
                        destination?.absolutePath,
                        openAlprConfFile,
                        10
                    )
                recognitionResult.postValue(result)
            }
        }
    }
}