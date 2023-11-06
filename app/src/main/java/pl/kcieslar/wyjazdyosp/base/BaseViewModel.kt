package pl.kcieslar.leocrm.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import pl.kcieslar.leocrm.utils.NavigationCommand
import pl.kcieslar.leocrm.utils.SingleLiveEvent

abstract class BaseViewModel : ViewModel() {

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
}

