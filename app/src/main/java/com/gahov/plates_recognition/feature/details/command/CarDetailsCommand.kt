package com.gahov.plates_recognition.feature.details.command

import com.gahov.domain.entity.failure.Failure
import com.gahov.plates_recognition.arch.router.command.Command
import com.gahov.plates_recognition.feature.selector.CarDisplayModel


sealed class CarDetailsCommand : Command.FeatureCommand() {

    data class DisplayContent(var content: CarDisplayModel) : CarDetailsCommand()

    data class OnError(var failure: Failure) : CarDetailsCommand()
}