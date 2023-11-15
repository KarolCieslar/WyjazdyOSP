package pl.kcieslar.wyjazdyosp.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import pl.kcieslar.wyjazdyosp.data.repository.impl.FirebaseLogServiceImpl
import pl.kcieslar.wyjazdyosp.mvvm.SingleLiveEvent
import pl.kcieslar.wyjazdyosp.utils.NavigationCommand
import javax.inject.Inject

abstract class BaseViewModel : ViewModel() {

    @Inject
    lateinit var logService: FirebaseLogServiceImpl

    private val _navigation = MutableLiveData<SingleLiveEvent<NavigationCommand>>()
    val navigation: LiveData<SingleLiveEvent<NavigationCommand>> get() = _navigation

    private val _messageRes = MutableLiveData<SingleLiveEvent<Int>>()
    val messageRes: LiveData<SingleLiveEvent<Int>> get() = _messageRes

    private val _message = MutableLiveData<SingleLiveEvent<String>>()
    val message: LiveData<SingleLiveEvent<String>> get() = _message


    fun navigate(navDirections: NavDirections) {
        _navigation.value = SingleLiveEvent(NavigationCommand.ToDirection(navDirections))
    }

    fun navigateBack() {
        _navigation.value = SingleLiveEvent(NavigationCommand.Back)
    }

    fun showMessage(text: String) {
        _message.value = SingleLiveEvent(text)
    }

    fun showMessageRes(stringRes: Int) {
        _messageRes.value = SingleLiveEvent(stringRes)
    }

    fun launchCatching(snackbar: Boolean = true, invokeOnCompletion: () -> Unit? = {}, block: suspend CoroutineScope.() -> Unit) =
        viewModelScope.launch(
            CoroutineExceptionHandler { _, throwable ->
                if (snackbar) {
                    showMessage(throwable.message.orEmpty())
                }
                logService.logNonFatalCrash(throwable)
                logService.printStackTrace(throwable)
            },
            block = block
        ).invokeOnCompletion { invokeOnCompletion() }
}

