package com.gahov.plates_recognition.feature.search

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import com.gahov.plates_recognition.R
import com.gahov.plates_recognition.arch.router.command.Command
import com.gahov.plates_recognition.arch.ui.dialog.BaseBottomSheetFragment
import com.gahov.plates_recognition.databinding.FragmentCarSearchBinding
import com.gahov.plates_recognition.feature.search.command.CarSearchCommand
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CarSearchBottomDialogFragment :
    BaseBottomSheetFragment<CarSearchViewModel, FragmentCarSearchBinding>(
        layoutId = R.layout.fragment_car_search,
        viewModelClass = CarSearchViewModel::class.java
    ) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.ThemeOverlay_Material3_BottomSheetDialog)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onLoading(false)
    }

    override fun setObservers() {
        super.setObservers()
        viewModel.isLoading.observe(viewLifecycleOwner, this::onLoading)
    }

    private fun onLoading(isLoading: Boolean) {
        binding.searchProgressBar.isVisible = isLoading
    }

    override fun handleFeatureCommand(command: Command.FeatureCommand) {
        with(command) {
            if (this is CarSearchCommand) {
                when (this) {
                    is CarSearchCommand.OnNetworkError -> {}
                }
            } else {
                super.handleFeatureCommand(command)
            }
        }
    }

    companion object {
        private const val LOADING_DELAY = 650L
        private const val SEARCHING_DELAY = 1500L
    }
}