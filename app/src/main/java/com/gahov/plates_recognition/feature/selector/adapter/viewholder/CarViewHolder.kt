package com.gahov.plates_recognition.feature.selector.adapter.viewholder

import androidx.databinding.ViewDataBinding
import com.gahov.plates_recognition.arch.ui.recycler.BaseViewHolder
import com.gahov.plates_recognition.databinding.ItemCarBinding
import com.gahov.plates_recognition.feature.selector.CarDisplayModel
import com.gahov.plates_recognition.feature.selector.presenter.CarSelectorPresenter

class CarViewHolder(
    private val presenter: CarSelectorPresenter, binding: ViewDataBinding
) : BaseViewHolder<CarDisplayModel, ItemCarBinding>(binding) {

    override fun bindView(position: Int) {
        val car = item

        binding.presenter = presenter
        binding.car = car
    }
}