package com.gahov.plates_recognition.feature.home

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Handler
import android.provider.MediaStore
import androidx.core.content.FileProvider
import com.gahov.domain.entity.cars.Car
import com.gahov.plates_recognition.R
import com.gahov.plates_recognition.app.arch.ui.fragment.BaseFragment
import com.gahov.plates_recognition.app.common.extensions.loadImage
import com.gahov.plates_recognition.databinding.HomeScreenFragmentBinding
import com.gahov.plates_recognition.feature.home.HomeViewModel.Companion.AUTHORITY
import com.gahov.plates_recognition.feature.home.HomeViewModel.Companion.REQUEST_IMAGE
import com.gahov.plates_recognition.feature.main.MainActivity
import com.permissionx.guolindev.PermissionX
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.IOException

@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeScreenFragmentBinding, HomeViewModel>(
    contentLayoutID = R.layout.home_screen_fragment,
    viewModelClass = HomeViewModel::class.java
) {

    private var destination: File? = null

    override fun onResume() {
        super.onResume()
        binding.presenter = this
    }

    override fun setObservers() {
        super.setObservers()
        viewModel.recognitionResult.observe(
            viewLifecycleOwner
        ) { setRecognitionResult(it) }
        viewModel.carData.observe(viewLifecycleOwner) { showCarResult(it) }
    }

    @SuppressLint("SetTextI18n")
    private fun showCarResult(car: Car) {
        binding.tvCarModel.text = getString(R.string.model) + car.vendor + " " + car.model
        binding.tvCarYear.text = getString(R.string.year) + car.year
        binding.tvCarRegion.text = getString(R.string.region) + car.region
        binding.tvCarIsStolen.text = ifIsStolen(car.isStolen)
        car.photoUrl?.let { binding.imageView.loadImage(it) }
    }

    //TODO
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)

//        imageLoader.loadImage(binding.imageView, destination)
        binding.numberInput.setText("")

        Handler().postDelayed({
            val photoURI: Uri = destination?.let {
                FileProvider.getUriForFile(
                    requireContext(), AUTHORITY, it
                )
            }!!
            photoURI.let { viewModel.processImage(requireContext(), it) }
        }, 1500)
    }

    //TODO
    fun dispatchTakePictureIntent() {
        PermissionX.init(activity as MainActivity)
            .permissions(listOf(android.Manifest.permission.CAMERA))
            .request { allGranted, _, _ ->
                if (allGranted) {
                    requestCameraIntent()
                } else {
                    showError("Camera permission denied")
                }
            }
    }

    //TODO
    private fun requestCameraIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(requireContext().packageManager)?.also {
                // Create the File where the photo should go
                destination = try {
                    viewModel.createImageFile(requireContext())
                } catch (ex: IOException) {
                    showError(ex.toString())
                    null
                }
                // Continue only if the File was successfully created
                destination?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        requireContext(), AUTHORITY, it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE)
                }
            }
        }
    }

    //TODO
    private fun setRecognitionResult(result: String) {
        if (result.length == 8) {
            binding.numberInput.append(result + "\n")
        }
    }

    //TODO
    private fun showError(message: String) {
        message.apply {}
    }

    fun searchByText() {
        viewModel.getCarInfoByPlateNumber(binding.numberInput.text.toString())
    }

    //TODO
    private fun ifIsStolen(isStolen: Boolean): String {
        return if (isStolen) {
            "Угон: Числится в угоне"
        } else {
            "Угон: Не числится в угоне"
        }
    }
}
