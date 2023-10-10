package com.gahov.plates_recognition.feature.selector.adapter

import androidx.recyclerview.widget.DiffUtil
import com.gahov.plates_recognition.feature.selector.CarDisplayModel

class CarListDiffUtil : DiffUtil.ItemCallback<CarDisplayModel>() {

    override fun areItemsTheSame(oldItem: CarDisplayModel, newItem: CarDisplayModel): Boolean {
        return oldItem.areItemsSame(newItem)
    }

    override fun areContentsTheSame(oldItem: CarDisplayModel, newItem: CarDisplayModel): Boolean {
        return oldItem == newItem
    }
}