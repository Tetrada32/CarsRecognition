package com.gahov.plates_recognition.feature.selector.adapter

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.gahov.plates_recognition.R
import com.gahov.plates_recognition.arch.ui.recycler.BaseRecyclerListAdapter
import com.gahov.plates_recognition.arch.ui.recycler.BaseViewHolder
import com.gahov.plates_recognition.feature.selector.CarDisplayModel
import com.gahov.plates_recognition.feature.selector.adapter.viewholder.CarViewHolder
import com.gahov.plates_recognition.feature.selector.presenter.CarSelectorPresenter

class CarListAdapter(
    private val presenter: CarSelectorPresenter
) : BaseRecyclerListAdapter<CarDisplayModel>(CarListDiffUtil()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<CarDisplayModel, out ViewDataBinding> {
        return CarViewHolder(
            presenter = presenter,
            binding = inflate(parent, R.layout.item_car)
        )
    }
}