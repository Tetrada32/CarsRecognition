package com.gahov.domain.common.usecase

interface UseCase<out Result> where Result : Any {

    open class Params
}