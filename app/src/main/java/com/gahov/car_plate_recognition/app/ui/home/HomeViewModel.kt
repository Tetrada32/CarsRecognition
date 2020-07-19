package com.gahov.car_plate_recognition.app.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.gahov.car_plate_recognition.app.arch.BaseCoroutinesViewModel
import com.gahov.car_plate_recognition.app.util.Constants
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.text.FirebaseVisionText
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class HomeViewModel : BaseCoroutinesViewModel() {

    val recognitionResult = MutableLiveData<String>()

    fun processImage(context: Context, photoUri: Uri) {

        val image = FirebaseVisionImage.fromFilePath(context, photoUri)
        val detector = FirebaseVision.getInstance().onDeviceTextRecognizer

        detector.processImage(image)
            .addOnSuccessListener { firebaseVisionText ->
                processResultText(firebaseVisionText)
            }
            .addOnFailureListener {
                recognitionResult.postValue("Error!")
            }
    }


    private fun processResultText(resultText: FirebaseVisionText) {
        if (resultText.textBlocks.size == 0) {
            recognitionResult.postValue("Error!")
            return
        }
        for (block in resultText.textBlocks) {
            val blockLine = block.lines
            for (i in blockLine) {
                val carPlate = i.text.replace("\\s".toRegex(), "")
                Log.d("sds", carPlate)
                if (carPlate.length == 8) {
                    recognitionResult.postValue(carPlate)
                } else if (carPlate.length == 10 && carPlate.contains("UA")) {
                    carPlate.replace("UA", "")
                    recognitionResult.postValue(carPlate)
                    }
                }
            }
        }

    @SuppressLint("SimpleDateFormat")
    @Throws(IOException::class)
    fun createImageFile(context: Context): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            Constants.JPEG_PREFIX + "${timeStamp}_", Constants.JPEG_SUFFIX, storageDir
        ).apply {
            absolutePath
        }
    }
}