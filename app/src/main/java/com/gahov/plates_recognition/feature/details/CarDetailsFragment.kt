package com.gahov.plates_recognition.feature.details

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.gahov.plates_recognition.R
import com.gahov.plates_recognition.arch.router.command.Command
import com.gahov.plates_recognition.arch.ui.fragment.BaseFragment
import com.gahov.plates_recognition.databinding.FragmentCarDetailsBinding
import com.gahov.plates_recognition.feature.details.command.CarDetailsCommand
import com.gahov.plates_recognition.feature.selector.CarDisplayModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CarDetailsFragment :
    BaseFragment<FragmentCarDetailsBinding, CarDetailsViewModel>(
        contentLayoutID = R.layout.fragment_car_details,
        viewModelClass = CarDetailsViewModel::class.java
    ) {

    private val args: CarDetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.presenter = viewModel
        viewModel.loadContent(args)
    }

    override fun handleFeatureCommand(command: Command.FeatureCommand) {
        with(command) {
            if (this is CarDetailsCommand) {
                when (this) {
                    is CarDetailsCommand.DisplayContent -> displayContent(content)
                    is CarDetailsCommand.OnError -> displayError(failure)
                }
            } else {
                super.handleFeatureCommand(command)
            }
        }
    }

    private fun displayContent(content: CarDisplayModel) {
        binding.car = content
    }
}