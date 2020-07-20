package com.car_plate.car_plate_recognition.app.ui.home

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import androidx.core.content.FileProvider
import androidx.lifecycle.Observer
import com.car_plate.car_plate_recognition.R
import com.car_plate.car_plate_recognition.app.arch.BaseFragment
import com.car_plate.car_plate_recognition.app.ui.main.MainActivity
import com.car_plate.car_plate_recognition.app.util.Constants.AUTHORITY
import com.car_plate.car_plate_recognition.app.util.Constants.REQUEST_IMAGE
import com.car_plate.car_plate_recognition.app.util.ImageLoader
import com.car_plate.car_plate_recognition.app.util.extensions.toast
import com.car_plate.car_plate_recognition.databinding.HomeScreenFragmentBinding
import com.car_plate.domain.entity.Car
import org.koin.android.ext.android.inject
import java.io.File
import java.io.IOException

class HomeFragment : BaseFragment<HomeScreenFragmentBinding>() {
    override fun getLayoutId() = R.layout.home_screen_fragment

    override fun initViewModel() = this

    override fun getCurrentDestinationId(): Int = R.id.homeFragment

    private var destination: File? = null

    private val viewModel: HomeViewModel by inject()
    private val imageLoader: ImageLoader by inject()

    private lateinit var saveContext: Context
    private lateinit var saveActivity: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (context != null) saveContext = context as Context
        if (activity != null) saveActivity = activity as MainActivity
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
        viewModel.recognitionResult.observe(viewLifecycleOwner, Observer { setRecognitionResult(it) })
        viewModel.carData.observe(viewLifecycleOwner, Observer { showCarResult(it) })
    }

    private fun showCarResult(car: Car) {
        binding.tvCarModel.append(car.vendor + " " + car.model)
        binding.tvCarYear.append(car.year)
        binding.tvCarRegion.append(car.isStolen.toString())
        car.photoUrl?.let { imageLoader.loadImage(binding.carDBImage, it) }
    }

    override fun onActivityResult(
            requestCode: Int,
            resultCode: Int,
            data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)

        imageLoader.loadImage(binding.imageView, destination)
        binding.textView.text = ""

        Handler().postDelayed({
            val photoURI: Uri = destination?.let {
                FileProvider.getUriForFile(
                        saveContext, AUTHORITY, it)
            }!!
            photoURI.let { viewModel.processImage(saveContext, it) }
        }, 1500)
    }

    fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(saveContext.packageManager)?.also {
                // Create the File where the photo should go
                destination = try {
                    viewModel.createImageFile(saveContext)
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

    private fun setRecognitionResult(result: String) {
        binding.textView.append(result + "\n")
    }

    private fun showError(message: String) {
        message.apply {
            saveContext.toast(this)
            binding.textView.text = this
        }
    }
}
