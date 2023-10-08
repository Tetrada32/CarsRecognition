package com.gahov.plates_recognition.app.arch.controller

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.gahov.domain.entity.failure.Failure
import com.gahov.plates_recognition.app.arch.ui.view.model.TextProvider
import com.gahov.plates_recognition.app.arch.coroutine.CoroutineLauncher
import com.gahov.plates_recognition.app.arch.coroutine.impl.DefaultCoroutineLauncher
import com.gahov.plates_recognition.app.arch.lifecycle.SingleLiveEvent
import com.gahov.plates_recognition.app.arch.provider.CoroutineProvider
import com.gahov.plates_recognition.app.arch.router.command.Command
import com.gahov.plates_recognition.app.arch.router.command.NavDirection
import kotlinx.coroutines.CoroutineScope

abstract class BaseViewModel : ViewModel(), Controller, CoroutineProvider {

    override val launcher: CoroutineLauncher by lazy {
        DefaultCoroutineLauncher(
            viewModelScope,
            ::handleFailure
        )
    }

    private val _message by lazy { SingleLiveEvent<TextProvider>() }
    val message: LiveData<TextProvider>
        get() = _message

    private val _errorEvent by lazy { SingleLiveEvent<Failure>() }
    val errorEvent: LiveData<Failure>
        get() = _errorEvent

    private val _command by lazy { SingleLiveEvent<Command>() }
    val command: LiveData<Command>
        get() = _command

    private val _isLoading by lazy { MutableLiveData(false) }
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _navigationCommand by lazy { SingleLiveEvent<Command>() }
    val navigationCommand: LiveData<Command>
        get() = _navigationCommand

    open fun handleCommand(command: Command) {
        _command.postValue(command)
    }

    fun launch(block: suspend CoroutineScope.() -> Unit) = launcher.launch(block = block)

    override fun showMessage(message: TextProvider) {
        _message.value = message
    }

    override fun setLoading(boolean: Boolean) {
        _isLoading.value = boolean
    }

    override fun navigate(command: Command) {
        _navigationCommand.value = command
    }

    fun navigateDirection(directions: NavDirections) {
        handleCommand(NavDirection.Direction(directions))
    }

    override fun handleFailure(failure: Failure) {
        if (failure is Failure.FeatureFailure) {
            handleFailureFeature(failure)
        } else {
            _errorEvent.postValue(failure)
        }
    }

    protected open fun handleFailureFeature(failure: Failure.FeatureFailure) {
        _errorEvent.postValue(failure)
    }
}
