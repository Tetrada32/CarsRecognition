package com.gahov.plates_recognition.feature.selector.command

import com.gahov.plates_recognition.arch.router.command.Command
import com.gahov.plates_recognition.feature.selector.CarDisplayModel


sealed class CarSelectorCommand : Command.FeatureCommand() {

    data class DisplayContent(var content: List<CarDisplayModel>) : CarSelectorCommand()

}