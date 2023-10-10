package com.gahov.domain.usecase.carplate.params

import com.gahov.domain.common.usecase.UseCase

class GetCarParams(
    val carPlate: String
) : UseCase.Params()