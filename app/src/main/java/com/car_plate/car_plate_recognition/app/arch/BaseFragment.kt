package com.car_plate.car_plate_recognition.app.arch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<T : ViewDataBinding> : Fragment() {

    lateinit var binding: T
        private set

    @LayoutRes
    internal abstract fun getLayoutId(): Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        return binding.root
    }

    open fun getBindingVariable(): Int? = null

    abstract fun getCurrentDestinationId(): Int

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getBindingVariable()?.let {
            binding.setVariable(it, initViewModel())
        }
        binding.lifecycleOwner = viewLifecycleOwner
        binding.executePendingBindings()
        setSubscribers()
    }

    abstract fun initViewModel(): Any

    protected abstract fun setSubscribers()
}