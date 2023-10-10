package com.gahov.plates_recognition.arch.controller

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.gahov.domain.entity.failure.Failure
import com.gahov.plates_recognition.arch.ui.view.model.TextProvider
import com.gahov.plates_recognition.arch.coroutine.CoroutineLauncher
import com.gahov.plates_recognition.arch.coroutine.impl.DefaultCoroutineLauncher
import com.gahov.plates_recognition.arch.lifecycle.SingleLiveEvent
import com.gahov.plates_recognition.arch.provider.CoroutineProvider
import com.gahov.plates_recognition.arch.router.command.Command
import com.gahov.plates_recognition.arch.router.command.NavDirection
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

/**
 * A base abstract class for ViewModels that provides common functionality such as handling commands,
 * showing messages, managing loading state, and navigation.
 */
abstract class BaseViewModel : ViewModel(), Controller, CoroutineProvider {

    /**
     * A [CoroutineLauncher] instance used for launching coroutines in a ViewModel-friendly way.
     */
    override val launcher: CoroutineLauncher by lazy {
        DefaultCoroutineLauncher(
            viewModelScope,
            ::handleFailure
        )
    }

    /**
     * LiveData holding text-based messages to be displayed in the UI.
     */
    private val _message by lazy { SingleLiveEvent<TextProvider>() }
    val message: LiveData<TextProvider>
        get() = _message

    /**
     * LiveData for broadcasting failures or errors that occur during ViewModel operations.
     */
    private val _errorEvent by lazy { SingleLiveEvent<Failure>() }
    val errorEvent: LiveData<Failure>
        get() = _errorEvent

    /**
     * LiveData for sending commands to the UI processing.
     */
    private val _command by lazy { SingleLiveEvent<Command>() }
    val command: LiveData<Command>
        get() = _command

    /**
     * LiveData indicating whether a loading operation is currently in progress.
     */
    private val _isLoading by lazy { MutableLiveData(false) }
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    /**
     * LiveData for triggering navigation events within the ViewModel.
     */
    private val _navigationCommand by lazy { SingleLiveEvent<Command>() }
    val navigationCommand: LiveData<Command>
        get() = _navigationCommand

    /**
     * Handles a given command by posting it to the [command] LiveData.
     *
     * @param command The command to be handled.
     */
    open fun handleCommand(command: Command) {
        _command.postValue(command)
    }

    /**
     * Launches a coroutine on the specified dispatcher, to perform the async opetations.
     *
     * @param dispatcher The [CoroutineDispatcher] on which to execute the coroutine.
     * @param block The coroutine code to be executed.
     */
    fun launch(
        dispatcher: CoroutineDispatcher = Dispatchers.Default,
        block: suspend CoroutineScope.() -> Unit
    ) = launcher.launch(block = block, dispatcher = dispatcher)

    /**
     * Displays a text-based message in the UI.
     *
     * @param message The text message to be displayed.
     */
    override fun showMessage(message: TextProvider) {
        _message.value = message
    }

    /**
     * Sets the loading state by the specified value.
     *
     * @param boolean The loading state value (true if loading, false otherwise).
     */
    override fun setLoading(boolean: Boolean) {
        _isLoading.value = boolean
    }

    /**
     * Triggers navigation by posting a navigation [command].
     *
     * @param command The navigation command to be executed.
     */
    override fun navigate(command: Command) {
        _navigationCommand.value = command
    }

    /**
     * Navigates using a [NavDirections] instance by converting it to a navigation [command].
     *
     * @param directions The navigation directions to be converted and executed.
     */
    fun navigateDirection(directions: NavDirections) {
        handleCommand(NavDirection.Direction(directions))
    }

    /**
     * Handles a failure event by differentiating between general failures and feature-specific failures.
     *
     * @param failure The failure event to be handled.
     */
    override fun handleFailure(failure: Failure) {
        if (failure is Failure.FeatureFailure) {
            handleFailureFeature(failure)
        } else {
            _errorEvent.postValue(failure)
        }
    }

    /**
     * Handles a feature-specific failure event.
     *
     * @param failure The feature-specific failure event to be handled.
     */
    protected open fun handleFailureFeature(failure: Failure.FeatureFailure) {
        _errorEvent.postValue(failure)
    }
}
