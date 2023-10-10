package com.gahov.plates_recognition.feature.selector

import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gahov.plates_recognition.R
import com.gahov.plates_recognition.arch.router.command.Command
import com.gahov.plates_recognition.arch.ui.fragment.BaseFragment
import com.gahov.plates_recognition.common.AppBarOffsetChangeListener
import com.gahov.plates_recognition.databinding.FragmentCarSelectorBinding
import com.gahov.plates_recognition.feature.selector.adapter.CarListAdapter
import com.gahov.plates_recognition.feature.selector.adapter.viewholder.CarViewHolder
import com.gahov.plates_recognition.feature.selector.command.CarSelectorCommand
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CarSelectorFragment :
    BaseFragment<FragmentCarSelectorBinding, CarSelectorViewModel>(
        contentLayoutID = R.layout.fragment_car_selector,
        viewModelClass = CarSelectorViewModel::class.java
    ) {

    private val cityListAdapter: CarListAdapter by lazy {
        CarListAdapter(presenter = viewModel)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.presenter = viewModel

        setupScrollingAnimation()
        setupAdapter()
        setupSwipeToDeleteItemGesture()
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadAllCashedCarData()
    }

    override fun handleFeatureCommand(command: Command.FeatureCommand) {
        with(command) {
            if (this is CarSelectorCommand) {
                when (this) {
                    is CarSelectorCommand.DisplayContent -> displayContent(content)
                }
            } else {
                super.handleFeatureCommand(command)
            }
        }
    }

    private fun setupAdapter() {
        binding.carsSelectorList.layoutManager = LinearLayoutManager(requireContext())
        binding.carsSelectorList.adapter = cityListAdapter
    }

    private fun displayContent(content: List<CarDisplayModel>) {
        cityListAdapter.items = content
    }

    private fun setupScrollingAnimation() {
        with(binding) {
            carsSelectorAppBarLayout.addOnOffsetChangedListener(object :
                AppBarOffsetChangeListener() {
                override fun onHide() {
                    hideViews()
                }

                override fun onShow() {
                    showViews()
                }
            })
        }
    }

    private fun hideViews() {
        binding.carsSelectorToolbar.animate()
            .translationY((-binding.carsSelectorToolbar.height).toFloat()).interpolator =
            AccelerateInterpolator(2F)
    }

    private fun showViews() {
        if (!binding.carsSelectorToolbar.isVisible) {
            val valueInPixels = resources.getDimension(R.dimen.grid_56)
            binding.carsSelectorToolbar.y = -valueInPixels
            binding.carsSelectorToolbar.visibility = View.VISIBLE
        }
        binding.carsSelectorToolbar.animate().translationY(0F).interpolator =
            DecelerateInterpolator(2F)
    }

    private fun setupSwipeToDeleteItemGesture() {
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                v: RecyclerView,
                h: RecyclerView.ViewHolder,
                t: RecyclerView.ViewHolder
            ) = false

            override fun onSwiped(h: RecyclerView.ViewHolder, dir: Int) {
                viewModel.deleteItem((h as CarViewHolder).item.getCarDigits())
                cityListAdapter.removeAt(h.adapterPosition)
            }
        }).attachToRecyclerView(binding.carsSelectorList)
    }
}