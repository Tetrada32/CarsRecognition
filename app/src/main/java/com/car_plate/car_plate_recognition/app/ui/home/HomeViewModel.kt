package com.car_plate.car_plate_recognition.app.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.MutableLiveData
import com.car_plate.car_plate_recognition.app.arch.BaseCoroutinesViewModel
import com.car_plate.car_plate_recognition.app.arch.SingleLiveEvent
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

    private val _errorEvent by lazy { SingleLiveEvent<Throwable>() }
    val errorEvent: LiveData<Throwable>
        get() = _errorEvent

    private val _isLoading by lazy { MutableLiveData(false) }
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    val recognitionResult = MutableLiveData<String>()

    val carData = getCarInfoUseCase.carSuccess

    private val getCarInfoFailedObserver: Observer<Throwable> = Observer {
        onError(it)
    }

    init {
        getCarInfoUseCase.carError.observeForever(getCarInfoFailedObserver)
    }

    fun processImage(context: Context, photoUri: Uri) {

        val image = FirebaseVisionImage.fromFilePath(context, photoUri)
        val detector = FirebaseVision.getInstance().onDeviceTextRecognizer

        detector.processImage(image)
            .addOnSuccessListener { firebaseVisionText ->
                processResultText(firebaseVisionText)
            }
            .addOnFailureListener {
                onError(Throwable(message = "Ошибка обработки изображения"))
            }
    }

    private fun processResultText(resultText: FirebaseVisionText) {
        if (resultText.textBlocks.size == 0) {
            onError(Throwable(message = "Ошибка обработки текста"))
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
            Constants.JPEG_PREFIX + "${timeStamp}_", Constants.JPEG_SUFFIX, storageDir
        ).apply {
            absolutePath
        }
    }

    fun getCarInfoByPlateNumber(carPlate: String) {
        if (carPlate.length == 8)
        getCarInfoUseCase.execute(GetCarParams(carPlate))
    }

    private fun onError(throwable: Throwable) {
        _errorEvent.value = throwable
    }

    override fun onCleared() {
        super.onCleared()

        getCarInfoUseCase.carError.removeObserver(getCarInfoFailedObserver)
    }
}