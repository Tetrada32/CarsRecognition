package com.gahov.car_plate_recognition.app.ui.home

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.gahov.car_plate_recognition.BR
import com.gahov.car_plate_recognition.BuildConfig
import com.gahov.car_plate_recognition.R
import com.gahov.car_plate_recognition.app.arch.BaseFragment
import com.gahov.car_plate_recognition.app.ui.main.MainActivity
import com.gahov.car_plate_recognition.app.util.ImageLoader
import com.gahov.car_plate_recognition.databinding.HomeScreenFragmentBinding
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import org.koin.android.ext.android.inject
import org.openalpr.OpenALPR
import org.openalpr.model.Results
import org.openalpr.model.ResultsError
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : BaseFragment<HomeScreenFragmentBinding>() {

    private val viewModel: HomeViewModel by inject()
    private val imageLoader: ImageLoader by inject()

    override fun getLayoutId() = R.layout.home_screen_fragment

    override fun initViewModel() = this

    override fun getCurrentDestinationId(): Int = R.id.homeFragment

    override fun setSubscribers() {}

    private val REQUEST_IMAGE = 100
    private val STORAGE = 1
    private var ANDROID_DATA_DIR: String? = null
    private var destination: File? = null

    private val TAG: String = "TAG"

    lateinit var currentPhotoPath: String
    lateinit var saveContext: Context
    lateinit var saveActivity: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (context != null) saveContext = context as Context
        if (activity != null) saveActivity = activity as MainActivity

        ANDROID_DATA_DIR = activity?.applicationInfo?.dataDir
    }

    override fun onResume() {
        super.onResume()
        binding.presenter = this
    }

    override fun onPause() {
        super.onPause()
        binding.presenter = null
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE && resultCode == RESULT_OK) {
            val openAlprConfFile =
                ANDROID_DATA_DIR + File.separatorChar + "runtime_data" + File.separatorChar + "openalpr.conf"
            val options = BitmapFactory.Options()
            options.inSampleSize = 10

//            Picasso.with(this@MainActivity).load(destination).fit().centerCrop().into(imageView)
            imageLoader.loadImage(binding.imageView, destination)
            binding.textView.text = "Processing"
            AsyncTask.execute {
                val result =
                    OpenALPR.Factory.create(saveContext, ANDROID_DATA_DIR)
                        .recognizeWithCountryRegionNConfig(
                            "eu",
                            "",
                            destination?.absolutePath,
                            openAlprConfFile,
                            10
                        )
                try {
                    val results: Results? = Gson().fromJson(result, Results::class.java)
                    saveActivity.runOnUiThread {
                        if (results?.results == null || results.results.size == 0) {
                            Toast.makeText(
                                saveContext,
                                "It was not possible to detect the licence plate.",
                                Toast.LENGTH_LONG
                            ).show()
                            binding.textView.text =
                                "It was not possible to detect the licence plate."
                        } else {
                            binding.textView.text = ("Plate: " + results.results[0]
                                .plate // Trim confidence to two decimal places
                                    + " Confidence: " + String.format(
                                "%.2f",
                                results.results[0].confidence
                            ) + "%" // Convert processing time to seconds and trim to two decimal places
                                    + " Processing time: " + String.format(
                                "%.2f",
                                results.processingTimeMs / 1000.0 % 60
                            ) + " seconds")
                        }
                    }
                } catch (exception: JsonSyntaxException) {
                    val resultsError: ResultsError =
                        Gson().fromJson(result, ResultsError::class.java)
                    saveActivity.runOnUiThread { binding.textView.text = resultsError.msg }
                }
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = saveActivity.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(saveActivity.packageManager)?.also {
                // Create the File where the photo should go
                destination = try {
                    createImageFile()
                } catch (ex: IOException) {
                    // Error occurred while creating the File
                    null
                }
                // Continue only if the File was successfully created
                destination?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        saveContext,
                        BuildConfig.APPLICATION_ID + ".provider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE)
                }
            }
        }
    }
}









//    fun checkPermission() {
//        val permissions: MutableList<String> =
//            ArrayList()
//        if (ContextCompat.checkSelfPermission(
//                saveContext,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE
//            ) !== PackageManager.PERMISSION_GRANTED
//        ) {
//            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
//        }
//        if (permissions.isNotEmpty()) {
//            Toast.makeText(saveContext, "Storage access needed to manage the picture.", Toast.LENGTH_LONG)
//                .show()
//            val params = permissions.toTypedArray()
//            ActivityCompat.requestPermissions(saveActivity, params, STORAGE)
//        } else { // We already have permissions, so handle as normal
//            dispatchTakePictureIntent()
//        }
//    }
//
//        override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<String>,
//        grantResults: IntArray
//    ) {
//        when (requestCode) {
//            STORAGE -> {
//                val perms: MutableMap<String, Int> =
//                    HashMap()
//                // Initial
//                perms[Manifest.permission.WRITE_EXTERNAL_STORAGE] =
//                    PackageManager.PERMISSION_GRANTED
//                // Fill with results
//                var i = 0
//                while (i < permissions.size) {
//                    perms[permissions[i]] = grantResults[i]
//                    i++
//                }
//                // Check for WRITE_EXTERNAL_STORAGE
//                val storage =
//                    perms[Manifest.permission.WRITE_EXTERNAL_STORAGE] == PackageManager.PERMISSION_GRANTED
//                if (storage) {
//                    // permission was granted, yay!
//                    dispatchTakePictureIntent()
//                } else {
//                    // Permission Denied
//                    Toast.makeText(
//                        saveContext,
//                        "Storage permission is needed to analyse the picture.",
//                        Toast.LENGTH_LONG
//                    ).show()
//                }
//            }
//            else -> {
//            }
//        }
//    }
//
//    private fun dateToString(date: Date?, format: String?): String {
//        val df =
//            SimpleDateFormat(format, Locale.getDefault())
//        return df.format(date)
//    }
//
