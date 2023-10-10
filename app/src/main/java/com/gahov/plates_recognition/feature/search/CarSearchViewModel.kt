package com.gahov.plates_recognition.feature.search


import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Environment
import com.gahov.domain.entity.cars.CarEntity
import com.gahov.domain.entity.common.Either
import com.gahov.domain.entity.failure.Failure
import com.gahov.domain.usecase.carplate.GetRemoteCarInfoUseCase
import com.gahov.domain.usecase.carplate.params.GetCarParams
import com.gahov.plates_recognition.arch.controller.BaseViewModel
import com.gahov.plates_recognition.feature.search.command.CarSearchCommand
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.text.FirebaseVisionText
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

@Suppress("DEPRECATION")
class CarSearchViewModel @Inject constructor(
    private val loadCarInfoUseCase: GetRemoteCarInfoUseCase
) : BaseViewModel() {

    private fun onResultFailure(failureResult: Failure) {
        handleCommand(CarSearchCommand.OnNetworkError(failureResult))
        handleFailure(failureResult)
    }

    fun processImage(context: Context, photoUri: Uri) {
        val image = FirebaseVisionImage.fromFilePath(context, photoUri)
        val detector = FirebaseVision.getInstance().onDeviceTextRecognizer

        detector.processImage(image)
            .addOnSuccessListener { firebaseVisionText ->
                processResultText(firebaseVisionText)
            }
            .addOnFailureListener {
                handleFailure(Failure.Common(Throwable(message = PICTURE_HANDLING_ERROR)))
            }
    }

    private fun processResultText(resultText: FirebaseVisionText) {
        if (resultText.textBlocks.size == EMPTY_TEXT) {
            handleFailure(Failure.Common(Throwable(message = TEXT_HANDLING_ERROR)))
            return
        }
        for (block in resultText.textBlocks) {
            val blockLine = block.lines
            for (i in blockLine) {
                var carPlate = i.text.replace("\\s".toRegex(), "")
                if (carPlate.length == MAX_POSSIBLE_CAR_DIGITS_LENGTH) {
                    carPlate.submitRecognitionAndSearch()
                } else {
                    carPlate = removeExtraSymbolsInDigits(carPlate)
                    if (carPlate.length == MAX_POSSIBLE_CAR_DIGITS_LENGTH) {
                        carPlate.submitRecognitionAndSearch()
                    } else {
                        handleFailure(Failure.Common(Throwable(message = WRONG_PLATED_INPUT_ERROR)))
                    }
                }
            }
        }
    }

    private fun String.submitRecognitionAndSearch() {
        CarSearchCommand.OnRecognitionResult(digits = this)
        searchCarInputDigits(GetCarParams(this))
    }

    private fun removeExtraSymbolsInDigits(fullCarDigits: String): String {
        var carPlate = fullCarDigits

        if (fullCarDigits.length == FULL_CAR_DIGITS_LENGTH && fullCarDigits.contains(UA_SYMBOL)) {
            carPlate = fullCarDigits.replace(UA_SYMBOL, "")
        }
        return carPlate
    }

    fun onNewCarDigitsInput(carDigits: String) {
        if ((carDigits.chars()).count().toInt() == MAX_POSSIBLE_CAR_DIGITS_LENGTH) {
            searchCarInputDigits(GetCarParams(carDigits))
        }
    }

    private fun searchCarInputDigits(param: GetCarParams) {
        launch {
            when (val result = loadCarInfoUseCase.execute(param = param)) {
                is Either.Right -> onResultSuccess(result = result.success)
                is Either.Left -> onResultFailure(result.failure)
            }
        }
    }

    private fun onResultSuccess(result: CarEntity) {
        navigateDirection(
            CarSearchBottomDialogFragmentDirections.actionCarSearchToCarDetails(
                carData = result,
                carDigits = result.digits
            )
        )
    }

    @SuppressLint("SimpleDateFormat")
    @Throws(IOException::class)
    fun createImageFile(context: Context): File {
        val timeStamp: String = SimpleDateFormat(IMAGE_DATE_FORMAT).format(Date())
        val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            JPEG_PREFIX + "${timeStamp}_", JPEG_SUFFIX, storageDir
        ).apply {
            absolutePath
        }
    }

    companion object {
        const val REQUEST_IMAGE = 100
        const val STORAGE = 1
        const val FLOAT = "%.2f"
        const val LOAD_DELAY = 1500L
        const val AUTHORITY = "com.gahov.plates_recognition.provider"
        private const val JPEG_SUFFIX = ".jpg"
        private const val JPEG_PREFIX = "JPEG_"
        private const val MAX_POSSIBLE_CAR_DIGITS_LENGTH = 8
        private const val FULL_CAR_DIGITS_LENGTH = 10
        private const val EMPTY_TEXT = 0
        private const val PICTURE_HANDLING_ERROR = "Помилка обробки зображення!"
        private const val TEXT_HANDLING_ERROR = "Помилка обробки номеру!"
        private const val WRONG_PLATED_INPUT_ERROR = "Невірний номер!"
        private const val IMAGE_DATE_FORMAT = "yyyyyMMdd_HHmmss"
        private const val UA_SYMBOL = "UA"
    }
}