package com.gahov.plates_recognition.feature.search

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.view.View
import androidx.core.content.FileProvider
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import com.gahov.domain.entity.failure.Failure
import com.gahov.plates_recognition.R
import com.gahov.plates_recognition.arch.router.command.Command
import com.gahov.plates_recognition.arch.ui.dialog.BaseBottomSheetFragment
import com.gahov.plates_recognition.databinding.FragmentCarSearchBinding
import com.gahov.plates_recognition.feature.main.MainActivity
import com.gahov.plates_recognition.feature.search.CarSearchViewModel.Companion.AUTHORITY
import com.gahov.plates_recognition.feature.search.CarSearchViewModel.Companion.LOAD_DELAY
import com.gahov.plates_recognition.feature.search.CarSearchViewModel.Companion.REQUEST_IMAGE
import com.gahov.plates_recognition.feature.search.command.CarSearchCommand
import com.permissionx.guolindev.PermissionX
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.IOException

@Suppress("DEPRECATION")
@AndroidEntryPoint
class CarSearchBottomDialogFragment :
    BaseBottomSheetFragment<CarSearchViewModel, FragmentCarSearchBinding>(
        layoutId = R.layout.fragment_car_search,
        viewModelClass = CarSearchViewModel::class.java
    ) {

    private var takePictureDestinationFile: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.ThemeOverlay_Material3_BottomSheetDialog)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListeners()
    }

    private fun setClickListeners() {
        binding.searchCameraButton.setOnClickListener {
            dispatchTakePictureIntent()
        }
        binding.searchButton.setOnClickListener {
            onLoading(true)
            viewModel.onNewCarDigitsInput(binding.numberInput.text.toString())
        }
    }

    private fun onLoading(isLoading: Boolean) {
        binding.searchProgressBar.isVisible = isLoading
    }

    override fun handleFeatureCommand(command: Command.FeatureCommand) {
        with(command) {
            if (this is CarSearchCommand) {
                when (this) {
                    is CarSearchCommand.OnNetworkError -> displayError(failure)
                    is CarSearchCommand.OnRecognitionResult -> displayRecognizedPlates(digits)
                }
            } else {
                super.handleFeatureCommand(command)
            }
        }
    }

    private fun displayRecognizedPlates(digits: String) {
        onLoading(false)
        binding.numberInput.setText(digits)
    }

    private fun dispatchTakePictureIntent() {
        PermissionX.init(activity as MainActivity)
            .permissions(listOf(android.Manifest.permission.CAMERA))
            .request { allGranted, _, _ ->
                if (allGranted) {
                    requestCameraIntent()
                } else {
                    viewModel.handleFailure(Failure.Common())
                }
            }
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun requestCameraIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(requireContext().packageManager)?.also {
                // Create the File where the photo should go
                takePictureDestinationFile = try {
                    onLoading(true)
                    viewModel.createImageFile(requireContext())
                } catch (ex: IOException) {
                    viewModel.handleFailure(Failure.Common())
                    null
                }
                // Continue only if the File was successfully created
                takePictureDestinationFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        requireContext(), AUTHORITY, it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE)
                }
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        binding.numberInput.setText("")

        Handler().postDelayed({
            val photoURI: Uri = takePictureDestinationFile?.let {
                FileProvider.getUriForFile(
                    requireContext(), AUTHORITY, it
                )
            }!!
            photoURI.let { viewModel.processImage(requireContext(), it) }
        }, LOAD_DELAY)
    }
}