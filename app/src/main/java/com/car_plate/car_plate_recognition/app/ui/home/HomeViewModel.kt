package com.car_plate.car_plate_recognition.app.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.car_plate.car_plate_recognition.app.arch.BaseCoroutinesViewModel
import com.car_plate.car_plate_recognition.app.util.Constants
import com.car_plate.domain.usecase.carplate.GetCarInfoUseCase
import com.car_plate.domain.usecase.carplate.GetCarParams
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.text.FirebaseVisionText
import org.koin.core.KoinComponent
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import org.koin.core.inject

class HomeViewModel : BaseCoroutinesViewModel(), KoinComponent {

    private val getCarInfoUseCase: GetCarInfoUseCase by inject()

    val recognitionResult = MutableLiveData<String>()

    val carData = getCarInfoUseCase.carSuccess

    fun processImage(context: Context, photoUri: Uri) {

        val image = FirebaseVisionImage.fromFilePath(context, photoUri)
        val detector = FirebaseVision.getInstance().onDeviceTextRecognizer

        detector.processImage(image)
            .addOnSuccessListener { firebaseVisionText ->
                processResultText(firebaseVisionText)
            }
            .addOnFailureListener {
                recognitionResult.postValue("Error! ")
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
                var carPlate = i.text.replace("\\s".toRegex(), "")
                Log.d("sds", carPlate)
                if (carPlate.length == 8) {
                    recognitionResult.postValue(carPlate)

                    getCarInfoByPlateNumber(carPlate)
                } else if (carPlate.length == 10 && carPlate.contains("UA")) {
                    carPlate = carPlate.replace("UA", "")

                    if (carPlate.length == 8) {
                        recognitionResult.postValue(carPlate)
                        getCarInfoByPlateNumber(carPlate)
                    } else {
                        recognitionResult.postValue("ERROR!")
                    }
                }
            }
        }
    }

    fun searchCarByText(carPlate: String) {
        if (carPlate.length == 8)
        getCarInfoByPlateNumber(carPlate)
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

    private fun getCarInfoByPlateNumber(carPlate: String) {
        getCarInfoUseCase.execute(GetCarParams(carPlate))
    }
}