package com.gahov.car_plate_recognition.app.ui.home

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.core.content.FileProvider
import androidx.lifecycle.Observer
import com.gahov.car_plate_recognition.R
import com.gahov.car_plate_recognition.app.arch.BaseFragment
import com.gahov.car_plate_recognition.app.ui.main.MainActivity
import com.gahov.car_plate_recognition.app.util.Constants.AUTHORITY
import com.gahov.car_plate_recognition.app.util.Constants.FLOAT
import com.gahov.car_plate_recognition.app.util.Constants.JPEG_PREFIX
import com.gahov.car_plate_recognition.app.util.Constants.JPEG_SUFFIX
import com.gahov.car_plate_recognition.app.util.Constants.REQUEST_IMAGE
import com.gahov.car_plate_recognition.app.util.ImageLoader
import com.gahov.car_plate_recognition.app.util.extensions.toast
import com.gahov.car_plate_recognition.databinding.HomeScreenFragmentBinding
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import org.koin.android.ext.android.inject
import org.openalpr.model.Results
import org.openalpr.model.ResultsError
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : BaseFragment<HomeScreenFragmentBinding>() {
    override fun getLayoutId() = R.layout.home_screen_fragment

    override fun initViewModel() = this

    override fun getCurrentDestinationId(): Int = R.id.homeFragment

    private var androidDataDir: String? = null
    private var destination: File? = null

    private val viewModel: HomeViewModel by inject()
    private val imageLoader: ImageLoader by inject()

    lateinit var currentPhotoPath: String
    lateinit var saveContext: Context
    lateinit var saveActivity: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (context != null) saveContext = context as Context
        if (activity != null) saveActivity = activity as MainActivity

        androidDataDir = saveActivity.applicationInfo?.dataDir
    }

    override fun onResume() {
        super.onResume()
        binding.presenter = this
    }

    override fun onPause() {
        super.onPause()
        binding.presenter = null
    }

    override fun setSubscribers() {
        viewModel.recognitionResult.observe(this, Observer { setRecognitionResult(it) })
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        binding.textView.text = getString(R.string.processing)

        imageLoader.loadImage(binding.imageView, destination)
        viewModel.recognizePlateNumber(saveContext, androidDataDir, destination)
    }

    private fun setRecognitionResult(result: String) {
        try {
            val results: Results? = Gson().fromJson(result, Results::class.java)
            results.apply {
                if (this?.results.isNullOrEmpty()) {
                    showError(getString(R.string.recognizing_error_text))
                } else {
                    binding.textView.text =
                        (getString(R.string.plate_text)
                            + results?.results?.get(0)?.plate
                            + getString(R.string.confidence_text)
                            + String.format(FLOAT, results?.results?.get(0)?.confidence)
                            + getString(R.string.persent))
                     }
                 }

        } catch (exception: JsonSyntaxException) {
            val resultsError: ResultsError = Gson().fromJson(result, ResultsError::class.java)
            showError(resultsError.msg)
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = saveContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            JPEG_PREFIX + "${timeStamp}_", JPEG_SUFFIX, storageDir
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(saveContext.packageManager)?.also {
                // Create the File where the photo should go
                destination = try {
                    createImageFile()
                } catch (ex: IOException) {
                    showError(ex.toString())
                    null
                }
                // Continue only if the File was successfully created
                destination?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        saveContext, AUTHORITY, it)
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE)
                }
            }
        }
    }

    private fun showError(message: String) {
        message.apply {
            saveContext.toast(this)
            binding.textView.text = this
        }
    }
}
