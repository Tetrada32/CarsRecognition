package com.gahov.plates_recognition.feature.home

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.gahov.domain.entity.cars.CarEntity
import com.gahov.domain.entity.common.Either
import com.gahov.domain.usecase.carplate.GetRemoteCarInfoUseCase
import com.gahov.domain.usecase.carplate.params.GetCarParams
import com.gahov.plates_recognition.arch.controller.BaseViewModel
import com.gahov.plates_recognition.arch.lifecycle.SingleLiveEvent
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.text.FirebaseVisionText
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val getCarInfoUseCase: GetRemoteCarInfoUseCase
) : BaseViewModel() {

    val recognitionResult = MutableLiveData<String>()

    val carData = SingleLiveEvent<CarEntity>()

//    private val getCarInfoFailedObserver: Observer<Throwable> = Observer {
//        onError(it)
//    }

    init {
//        getCarInfoUseCase.carError.observeForever(getCarInfoFailedObserver)
    }

    fun processImage(context: Context, photoUri: Uri) {

        val image = FirebaseVisionImage.fromFilePath(context, photoUri)
        val detector = FirebaseVision.getInstance().onDeviceTextRecognizer

        detector.processImage(image)
            .addOnSuccessListener { firebaseVisionText ->
                processResultText(firebaseVisionText)
            }
            .addOnFailureListener {
//                onError(Throwable(message = "Ошибка обработки изображения"))
            }
    }

    private fun processResultText(resultText: FirebaseVisionText) {
        if (resultText.textBlocks.size == 0) {
//            onError(Throwable(message = "Ошибка обработки текста"))
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
                        recognitionResult.postValue("На сервер был отправлен некорректный номер автомобиля!")
                    }
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
            JPEG_PREFIX + "${timeStamp}_", JPEG_SUFFIX, storageDir
        ).apply {
            absolutePath
        }
    }

    fun getCarInfoByPlateNumber(carPlate: String?) {
        launch {
            if (carPlate?.length == 8) {
                when (val result = getCarInfoUseCase.execute(GetCarParams(carPlate))) {
                    is Either.Right -> processSuccess(result.success)
                    is Either.Left -> error(result.failure)
                }
            }
        }
    }

    private fun processSuccess(car: CarEntity?) {
        car?.let { carData.postValue(it) }
    }

    companion object {

        private const val PROVIDER = ".provider"

        const val AUTHORITY = "com.gahov.plates_recognition" + PROVIDER

        const val REQUEST_IMAGE = 100

        const val STORAGE = 1

        const val JPEG_SUFFIX = ".jpg"

        const val JPEG_PREFIX = "JPEG_"

        const val FLOAT = "%.2f"
    }
}