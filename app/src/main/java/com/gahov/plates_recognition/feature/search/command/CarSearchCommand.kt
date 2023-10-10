package com.gahov.plates_recognition.feature.search.command

import com.gahov.domain.entity.failure.Failure
import com.gahov.plates_recognition.arch.router.command.Command


sealed class CarSearchCommand : Command.FeatureCommand() {

    data class OnNetworkError(var failure: Failure) : CarSearchCommand()

}