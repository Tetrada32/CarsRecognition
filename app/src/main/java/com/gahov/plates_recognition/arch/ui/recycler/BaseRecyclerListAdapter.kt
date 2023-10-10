package com.gahov.plates_recognition.arch.ui.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

/**
 * An abstract base class for creating RecyclerView ListAdapters with simplified item management
 * and view binding.
 *
 * @param T The type of items to be displayed in the adapter.
 * @property items The list of items to be displayed in the adapter.
 */
abstract class BaseRecyclerListAdapter<T : Any> :
    ListAdapter<T, BaseViewHolder<T, out ViewDataBinding>> {

    var items: List<T> = arrayListOf()
        set(value) {
            field = value
            submitList(value)
        }

    constructor(diffCallback: DiffUtil.ItemCallback<T>) : super(diffCallback)

    constructor(config: AsyncDifferConfig<T>) : super(config)

    protected fun inflate(parent: ViewGroup, @LayoutRes contentLayoutID: Int): ViewDataBinding {
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), contentLayoutID, parent, false
        )
    }

    override fun onViewAttachedToWindow(holder: BaseViewHolder<T, out ViewDataBinding>) {
        holder.onAttachToWindow()
        super.onViewAttachedToWindow(holder)
    }

    override fun onViewDetachedFromWindow(holder: BaseViewHolder<T, out ViewDataBinding>) {
        holder.onDetachFromWindow()
        super.onViewDetachedFromWindow(holder)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<T, out ViewDataBinding>, position: Int) {
        holder.bindView(position, getItem(position))
    }

    /**
     * Removes the item at the specified [index] from the list of items.
     *
     * @param index The index of the item to be removed.
     */
    fun removeAt(index: Int) {
        if (index in items.indices) {
            val newItems = items.toMutableList()
            newItems.removeAt(index)
            items = newItems
            notifyItemRangeChanged(index, items.size - index)
        }
    }
}