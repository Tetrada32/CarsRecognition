package com.gahov.plates_recognition.arch.ui.recycler

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * An abstract base class for creating RecyclerView ViewHolders with simplified binding and item handling.
 *
 * @param T The type of item associated with the ViewHolder.
 * @param B The type of ViewDataBinding associated with the ViewHolder.
 * @property binding The ViewDataBinding instance associated with the ViewHolder's root view.
 */

abstract class BaseViewHolder<T : Any, B : ViewDataBinding>(binding: ViewDataBinding) :
    RecyclerView.ViewHolder(binding.root) {

    lateinit var item: T
        private set

    @Suppress("UNCHECKED_CAST")
    val binding: B = binding as B

    fun bindView(position: Int, item: T) {
        this.item = item
        bindView(position)
    }

    abstract fun bindView(position: Int)

    open fun onAttachToWindow() {}

    open fun onDetachFromWindow() {}
}